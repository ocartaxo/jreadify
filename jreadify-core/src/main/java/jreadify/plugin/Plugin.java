package jreadify.plugin;

import jreadify.domain.Chapter;
import jreadify.domain.Ebook;

public interface Plugin {
    String afterRender(String chapterHtml);
    void afterAssemble(Ebook ebook);
}
