package jreadify.application;

import jreadify.domain.Chapter;
import jreadify.domain.Ebook;
import jreadify.domain.EbookFormat;
import jreadify.md.MD2HtmlRender;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
public class JReadify {

    public void execute(JReadifyParams params){

        EbookFormat format = params.getFormat();
        Path mdFilesDir = params.getMdFilesDir();
        Path outputFilesDir = params.getOutputFilesDir();

        MD2HtmlRender md2HtmlRender = new MD2HtmlRender();
        List<Chapter> chapters = md2HtmlRender.render(mdFilesDir);
        Ebook ebook = new Ebook(format, outputFilesDir, chapters);

        EbookAssembler assembler = EbookAssembler.build(format);
        assembler.assemble(ebook);
    }

}
