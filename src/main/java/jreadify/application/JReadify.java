package jreadify.application;

import jreadify.domain.Chapter;
import jreadify.domain.Ebook;
import jreadify.epub.EPUBAssembler;
import jreadify.pdf.PDFAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
public class JReadify {

    private Md2HtmlRender md2HtmlRender;

    private EbookAssembler assembler;


    @Autowired
    public JReadify(Md2HtmlRender md2HtmlRender) {
        this.md2HtmlRender = md2HtmlRender;
    }

    public void execute(JReadifyParams params){

        String format = params.getFormat();
        Path mdFilesDir = params.getMdFilesDir();
        Path outputFilesDir = params.getOutputFilesDir();

        List<Chapter> chapters = md2HtmlRender.render(mdFilesDir);
        Ebook ebook = new Ebook(format, outputFilesDir, chapters);

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
