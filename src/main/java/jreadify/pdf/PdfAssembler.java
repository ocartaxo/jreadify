package jreadify.pdf;

import jreadify.domain.Ebook;

public interface PdfAssembler {
    void assemble(Ebook ebook);

    static PdfAssembler assemble() {
        return new PDFAssemblerImpl();
    }
}
