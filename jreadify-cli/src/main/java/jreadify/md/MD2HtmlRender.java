package jreadify.md;

import jreadify.domain.Chapter;
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

    private final Plugins plugins;

    public MD2HtmlRender(Plugins plugins) {
        this.plugins = plugins;
    }

    public List<Chapter> render(Path mdFilesDir) {
        return getMDFiles(mdFilesDir).stream()
                .map(this::renderChapter)
                .toList();
    }

    private Chapter renderChapter(Path mdFile) {
        Chapter chapter = new Chapter();
        Node document = parseMarkdown(mdFile, chapter);
        chapter.setHtmlContent(renderToHtml(document));
        plugins.rendered(chapter);
        return chapter;
    }

    private Node parseMarkdown(Path mdFile, Chapter chapter) {
        try {
            Parser parser = Parser.builder().build();
            Node document = parser.parseReader(Files.newBufferedReader(mdFile));
            document.accept(new TitleExtractor(chapter));
            return document;
        } catch (IOException ex) {
            throw new IllegalStateException("Erro ao fazer parse do arquivo " + mdFile, ex);
        }
    }

    private String renderToHtml(Node document) {
        return HtmlRenderer.builder().build().render(document);
    }

    private List<Path> getMDFiles(Path mdFilesDir) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");
        try (Stream<Path> mdFiles = Files.list(mdFilesDir)) {
            return mdFiles.filter(matcher::matches).sorted().toList();
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Erro tentando encontrar arquivos .md em " + mdFilesDir.toAbsolutePath(), ex);
        }
    }
}