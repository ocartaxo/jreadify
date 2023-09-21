package jreadify.epub;

import jreadify.domain.Ebook;

public interface EpubAssembler {
    void assemble(Ebook ebook);

    static EpubAssembler build(){
        return new EpubAssemblerImpl();
    }
}
