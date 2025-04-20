package jreadify.domain;

import java.nio.file.Path;
import java.util.List;

public record Ebook(
        EbookFormat format,
        Path outputFileDir,
        List<Chapter> chapters
) {

    public boolean isLastChapter(Chapter c){
        return this.chapters.get(this.chapters.size() - 1).equals(c);
    }
}
