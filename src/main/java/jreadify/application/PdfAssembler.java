package jreadify.application;

import jreadify.domain.Ebook;
import jreadify.pdf.PdfAssemblerWthIText;

public interface PdfAssembler {
    void assemble(Ebook ebook);

    static PdfAssembler assemble() {
        return new PdfAssemblerWthIText();
    }
}
