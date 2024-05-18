package jreadify.application;

import jreadify.domain.Ebook;
import jreadify.domain.EbookFormat;

public interface EbookAssembler {

    void assemble(Ebook ebook);

    static EbookAssembler build(EbookFormat format){
        return format.getAssembler();
    }
}
