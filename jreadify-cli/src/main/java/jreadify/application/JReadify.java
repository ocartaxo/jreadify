package jreadify.application;

import jreadify.domain.Chapter;
import jreadify.domain.Ebook;
import jreadify.domain.EbookFormat;
import jreadify.md.MD2HtmlRender;
import jreadify.plugin.Plugin;
import jreadify.plugin.Plugins;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
public class JReadify {

    private final MD2HtmlRender md2HtmlRender;
    private final List<EbookAssembler> ebookAssemblers;

    private final Plugins plugins;

    public JReadify(MD2HtmlRender md2HtmlRender, List<EbookAssembler> ebookAssemblers, Plugins plugins) {
        this.md2HtmlRender = md2HtmlRender;
        this.ebookAssemblers = ebookAssemblers;
        this.plugins = plugins;
    }

    public void execute(JReadifyParams params){

        EbookFormat format = params.getFormat();
        Path mdFilesDir = params.getMdFilesDir();
        Path outputFilesDir = params.getOutputFilesDir();

        List<Chapter> chapters = md2HtmlRender.render(mdFilesDir);
        Ebook ebook = new Ebook(format, outputFilesDir, chapters);

        var ebookAssembler = ebookAssemblers.stream()
                .filter(assembler -> assembler.accept(format))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Formato de ebook inválido: " + format));

        ebookAssembler.assemble(ebook);

        plugins.generated(ebook);
    }

}
