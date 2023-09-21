package jreadify.application;

import jreadify.domain.Ebook;
import jreadify.epub.EpubAssemblerWthEpublib;

public interface EpubAssembler {
    void assemble(Ebook ebook);

    static EpubAssembler build(){
        return new EpubAssemblerWthEpublib();
    }
}
