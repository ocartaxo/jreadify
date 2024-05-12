package jreadify.application;

import jreadify.domain.Chapter;
import jreadify.domain.Ebook;
import jreadify.epub.EPUBAssembler;
import jreadify.md.MD2HtmlRender;
import jreadify.pdf.PDFAssembler;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
public class JReadify {

    public void execute(JReadifyParams params){

        String format = params.getFormat();
        Path mdFilesDir = params.getMdFilesDir();
        Path outputFilesDir = params.getOutputFilesDir();

        MD2HtmlRender md2HtmlRender = new MD2HtmlRender();
        List<Chapter> chapters = md2HtmlRender.render(mdFilesDir);
        Ebook ebook = new Ebook(format, outputFilesDir, chapters);

        EbookAssembler assembler;
        if ("pdf".equals(format)) {
            assembler = new PDFAssembler();
        } else if ("epub".equals(format)) {
            assembler = new EPUBAssembler();
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + format);
        }

        assembler.assemble(ebook);
    }

}
