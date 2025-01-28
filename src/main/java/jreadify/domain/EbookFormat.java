package jreadify.domain;

import jreadify.application.EbookAssembler;
import jreadify.assembler.EPUBAssembler;
import jreadify.assembler.HTMLAssembler;
import jreadify.assembler.PDFAssembler;
import lombok.Getter;

@Getter
public enum EbookFormat {
   PDF(new PDFAssembler()),
   EPUB(new EPUBAssembler()),
   HTML(new HTMLAssembler());

   private final EbookAssembler assembler;

   EbookFormat(EbookAssembler assembler) {
      this.assembler = assembler;
   }

}
