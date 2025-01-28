package jreadify.domain;

import jreadify.application.EbookAssembler;
import jreadify.assembler.EPUBAssembler;
import jreadify.assembler.HTMLAssembler;
import jreadify.assembler.PDFAssembler;
import lombok.Getter;

@Getter
public enum EbookFormat {
   PDF, EPUB, HTML;
}
