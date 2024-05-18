package jreadify.domain;

import jreadify.application.EbookAssembler;
import jreadify.epub.EPUBAssembler;
import jreadify.pdf.PDFAssembler;
import lombok.Getter;

@Getter
public enum EbookFormat {
   PDF(new PDFAssembler()),
   EPUB(new EPUBAssembler());

   private EbookAssembler assembler;

   EbookFormat(EbookAssembler assembler) {
      this.assembler = assembler;
   }

}
