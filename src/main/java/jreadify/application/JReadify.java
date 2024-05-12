package jreadify.application;

import jreadify.domain.Chapter;
import jreadify.domain.Ebook;
import jreadify.epub.EpubAssemblerWthEpubLib;
import jreadify.pdf.PdfAssemblerWthIText;
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
            assembler = new PdfAssemblerWthIText();
        } else if ("epub".equals(format)) {
            assembler = new EpubAssemblerWthEpubLib();
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + format);
        }

        assembler.assemble(ebook);
    }

}
