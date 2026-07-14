package jreadify.plugin;

import jreadify.domain.Ebook;

// TODO separar em duas interfaces menores
public interface Plugin {
    String afterRender(String chapterHtml);
    void afterAssemble(Ebook ebook);
}
