package jreadify.application;

import jreadify.domain.Ebook;
import jreadify.domain.EbookFormat;

public interface EbookAssembler {

    void assemble(Ebook ebook);

    boolean accept(EbookFormat format);
}
