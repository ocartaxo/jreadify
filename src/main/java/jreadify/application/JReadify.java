package jreadify.application;

import jreadify.md.Md2HtmlRender;
import jreadify.epub.EpubBuilder;
import jreadify.epub.EpubBuilderImpl;
import jreadify.pdf.PDFBuilder;
import jreadify.pdf.PDFBuilderImpl;
import jreadify.domain.Chapter;
import jreadify.domain.Ebook;
import jreadify.md.Md2HtmlRenderImpl;

import java.nio.file.Path;
import java.util.List;

public class JReadify {

    public void execute(String format, Path mdFilesDir, Path outputFilesDir){

        Md2HtmlRender md2HtmlRender = new Md2HtmlRenderImpl();
        List<Chapter> chapters = md2HtmlRender.render(mdFilesDir);
        Ebook ebook = new Ebook(format, outputFilesDir, chapters);

        if ("pdf".equals(format)) {
            PDFBuilder pdfBuilder = new PDFBuilderImpl();
            pdfBuilder.build(ebook);
        } else if ("epub".equals(format)) {
            EpubBuilder epubBuilder = new EpubBuilderImpl();
            epubBuilder.build(ebook);
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + format);
        }

    }

}
