package jreadify.application;

import jreadify.domain.Chapter;
import jreadify.domain.Ebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
public class JReadify {

    private Md2HtmlRender md2HtmlRender;
    private PdfAssembler pdfAssembler;
    private EpubAssembler epubAssembler;

    @Autowired
    public JReadify(Md2HtmlRender md2HtmlRender, PdfAssembler pdfAssembler, EpubAssembler epubAssembler) {
        this.md2HtmlRender = md2HtmlRender;
        this.pdfAssembler = pdfAssembler;
        this.epubAssembler = epubAssembler;
    }

    public void execute(JReadifyParams params){

        String format = params.getFormat();
        Path mdFilesDir = params.getMdFilesDir();
        Path outputFilesDir = params.getOutputFilesDir();

        List<Chapter> chapters = md2HtmlRender.render(mdFilesDir);
        Ebook ebook = new Ebook(format, outputFilesDir, chapters);

        if ("pdf".equals(format)) {
            pdfAssembler.assemble(ebook);
        } else if ("epub".equals(format)) {
            epubAssembler.assemble(ebook);
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + format);
        }

    }

}
