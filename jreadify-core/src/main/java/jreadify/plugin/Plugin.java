package jreadify.plugin;

import jreadify.domain.Chapter;
import jreadify.domain.Ebook;

import java.util.ServiceLoader;

public interface Plugin {
    String afterRender(String chapterHtml);
    void afterAssemble(Ebook ebook);

    public static void rendered(Chapter chapter) {
        ServiceLoader.load(Plugin.class).forEach(plugin -> {
            String html = chapter.getHtmlContent();
            String modifiedHtml = plugin.afterRender(html);
            chapter.setHtmlContent(modifiedHtml);
        });
    }

    public static void generated(Ebook ebook) {
        ServiceLoader.load(Plugin.class)
                .forEach(plugin -> plugin.afterAssemble(ebook));
    }
}
