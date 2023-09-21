package jreadify.epub;

import jreadify.application.EpubAssembler;
import jreadify.domain.Chapter;
import jreadify.domain.Ebook;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EpubAssemblerWthEpublib implements EpubAssembler {

    @Override
    public void assemble(Ebook ebook) {

        var epub = new Book();

        Path outputFileDir = ebook.outputFileDir();

        for (Chapter chapter : ebook.chapters()) {
            String html = chapter.getHtmlContent();
            String chapterTitle = chapter.getTitle();

            epub.addSection(chapterTitle, new Resource(html.getBytes(), MediatypeService.XHTML));
        }

        var epubWriter = new EpubWriter();
        try {
            epubWriter.write(epub, Files.newOutputStream(outputFileDir));
        } catch (IOException ex) {
            throw new IllegalStateException("Erro ao criar arquivo EPUB: " + outputFileDir.toAbsolutePath(), ex);
        }

    }

}
