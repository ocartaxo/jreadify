package jreadify.md;

import jreadify.domain.Chapter;
import jreadify.plugin.Plugin;
import jreadify.plugin.Plugins;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Stream;

@Component
public class MD2HtmlRender {

    public Plugins plugins;

    public MD2HtmlRender(Plugins plugins) {
        this.plugins = plugins;
    }

    public List<Chapter> render(Path mdFilesDir) {

        return getMDFiles(mdFilesDir).stream()
                .map(mdFile -> {
                    Chapter chapter = new Chapter();
                    Node document = getMDParsed(mdFile, chapter);

                    render2HTML(mdFile, document, chapter);
                    return chapter;
                }).toList();
    }

    private Node getMDParsed(Path mdFile, Chapter chapter) {
        try {
            Parser parser = Parser.builder().build();
            Node document = parser.parseReader(Files.newBufferedReader(mdFile));
            document.accept(new TitleExtractor(chapter));
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
            plugins.rendered(chapter);
        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao renderizar para HTML o arquivo " + mdFile + " Mensagem: " + ex.getMessage(), ex);
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
