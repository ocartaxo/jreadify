package jreadify.assembler;

import jreadify.application.EbookAssembler;
import jreadify.domain.Chapter;
import jreadify.domain.Ebook;
import jreadify.domain.EbookFormat;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.Normalizer;

@Component
public class HTMLAssembler implements EbookAssembler {

    private static final String NON_ASCII = "[^\\p{ASCII}]";
    private static final String NON_WORDS_PATTERN = "[^\\w]";
    private static final String HTML_TEMPLATE = """
                        <!DOCTYPE html>
                        <html lang="pt-br">
                        <head>
                            <meta charset="UTF-8">
                            <title>%s</title>
                        </head>
                        <body>
                            %s
                        </body>
                        </html>
                        """;

    @Override
    public void assemble(Ebook ebook) {
        var outputFileDir = ebook.outputFileDir();
        try {
            var htmlDir = Files.createDirectories(outputFileDir);
            int i = 1;
            for (var chapter : ebook.chapters()) {
                var chapterFileName = getHTMLFileNameChapter(i++, chapter);

                var chapterFileHTML = htmlDir.resolve(chapterFileName);

                var html = HTML_TEMPLATE.formatted(chapter.getTitle(), chapter.getHtmlContent());

                Files.writeString(chapterFileHTML, html, StandardCharsets.UTF_8);
            }
        } catch (IOException ex){
            System.err.println(ex.getMessage());
            throw new IllegalStateException("Error ao criar HTML: " + outputFileDir.toAbsolutePath(), ex);
        }

    }

    private String getHTMLFileNameChapter(int i, Chapter chapter) {
        return i + " - " + removeAccents(chapter.getTitle().toLowerCase())
                .replaceAll(NON_WORDS_PATTERN, "")
                + ".html";

    }

    private String removeAccents(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll(NON_ASCII, "");
    }

    @Override
    public boolean accept(EbookFormat format) {
        return EbookFormat.HTML.equals(format);
    }
}
