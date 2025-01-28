package jreadify.application;

import jreadify.domain.Chapter;
import jreadify.domain.Ebook;
import jreadify.domain.EbookFormat;
import jreadify.md.MD2HtmlRender;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
public class JReadify {

    private final MD2HtmlRender md2HtmlRender;
    private final List<EbookAssembler> ebookAssemblers;

    public JReadify(MD2HtmlRender md2HtmlRender, List<EbookAssembler> ebookAssemblers) {
        this.md2HtmlRender = md2HtmlRender;
        this.ebookAssemblers = ebookAssemblers;
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
    }

}
