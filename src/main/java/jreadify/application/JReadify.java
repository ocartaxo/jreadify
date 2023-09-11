package jreadify.application;

import jreadify.output.epub.EpubBuilder;
import jreadify.output.pdf.PDFBuilder;
import jreadify.domain.Chapter;
import jreadify.domain.Ebook;
import jreadify.md.RenderMD2HTML;

import java.nio.file.Path;
import java.util.List;

public class JReadify {

    public void execute(String format, Path mdFilesDir, Path outputFilesDir){

        RenderMD2HTML renderMD2HTML = new RenderMD2HTML();
        List<Chapter> chapters = renderMD2HTML.render(mdFilesDir);
        Ebook ebook = new Ebook(format, outputFilesDir, chapters);

        if ("pdf".equals(format)) {
            PDFBuilder pdfBuilder = new PDFBuilder();
            pdfBuilder.build(ebook);
        } else if ("epub".equals(format)) {
            EpubBuilder epubBuilder = new EpubBuilder();
            epubBuilder.build(ebook);
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + format);
        }

    }

}
