package jreadify.pdf;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.properties.AreaBreakType;
import jreadify.application.PdfAssembler;
import jreadify.domain.Ebook;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class PdfAssemblerWthIText implements PdfAssembler {

    @Override
    public void assemble(Ebook ebook) {

        Path outputFilesDir = ebook.outputFileDir();

        try(var writer = new PdfWriter(Files.newOutputStream(outputFilesDir));

            var pdf = new PdfDocument(writer);
            var pdfDocument = new Document(pdf)) {

            ebook.chapters().forEach(chapter -> {
                String html = chapter.getHtmlContent();
                List<IElement> convertToElements = HtmlConverter.convertToElements(html);
                convertToElements.forEach(element -> pdfDocument.add((IBlockElement) element));
                if (!ebook.isLastChapter(chapter)) {
                    pdfDocument.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                }

            });

        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao criar arquivo PDF: " + ebook.outputFileDir().toAbsolutePath(), ex);
        }
    }
}
