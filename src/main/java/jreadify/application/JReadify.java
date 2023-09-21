package jreadify.application;

import jreadify.domain.Chapter;
import jreadify.domain.Ebook;
import jreadify.epub.EpubAssembler;
import jreadify.md.Md2HtmlRender;
import jreadify.pdf.PdfAssembler;

import java.nio.file.Path;
import java.util.List;

public class JReadify {

    public void execute(String format, Path mdFilesDir, Path outputFilesDir){

        Md2HtmlRender md2HtmlRender = Md2HtmlRender.build();
        List<Chapter> chapters = md2HtmlRender.render(mdFilesDir);
        Ebook ebook = new Ebook(format, outputFilesDir, chapters);

        if ("pdf".equals(format)) {
            PdfAssembler pdfBuilder = PdfAssembler.assemble();
            pdfBuilder.assemble(ebook);
        } else if ("epub".equals(format)) {
            EpubAssembler epubAssembler = EpubAssembler.build();
            epubAssembler.assemble(ebook);
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + format);
        }

    }

}
