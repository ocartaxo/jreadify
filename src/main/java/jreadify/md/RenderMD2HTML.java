package jreadify.md;

import jreadify.domain.Chapter;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Stream;

public class RenderMD2HTML {

    public List<Chapter> render(Path mdFilesDir) {

        return getMDFiles(mdFilesDir).stream()
                .map(mdFile -> {
                    Chapter chapter = new Chapter();
                    Node document = getMDParsed(mdFile, chapter);

                    render2HTML(mdFile, document, chapter);
                    return new Chapter();
                }).toList();
    }

    private Node getMDParsed(Path mdFile, Chapter chapter) {
        Parser parser = Parser.builder().build();
        Node document;

        try {
            document = parser.parseReader(Files.newBufferedReader(mdFile));
            document.accept(new AbstractVisitor() {
                @Override
                public void visit(Heading heading) {
                    // TODO usar o design pattern CoR
                    if (heading.getLevel() == 1) {
                        chapter.setTitle(((Text) heading.getFirstChild()).getLiteral());
                    } else if (heading.getLevel() == 2) {
                        // TODO seção
                    } else if (heading.getLevel() == 3) {
                        // TODO título
                    }

                }
            });

            return document;

        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao fazer parse do arquivo " + mdFile, ex);
        }
    }

    public void render2HTML(Path mdFile, Node document, Chapter chapter) {
        try {
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String html = renderer.render(document);

            chapter.setHtmlContent(html);

        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao renderizar para HTML o arquivo " + mdFile, ex);
        }


    }


    public List<Path> getMDFiles(Path mdFilesDir) {

        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");
        try (Stream<Path> mdFiles = Files.list(mdFilesDir)) {
            return mdFiles
                    .filter(matcher::matches)
                    .sorted()
                    .toList();


        } catch (IOException ex) {
            throw new IllegalStateException("Erro tentando encontrar arquivos .md em " + mdFilesDir.toAbsolutePath(), ex);
        }

    }
}
