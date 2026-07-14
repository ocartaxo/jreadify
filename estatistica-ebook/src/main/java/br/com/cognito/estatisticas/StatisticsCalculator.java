package br.com.cognito.estatisticas;

import jreadify.domain.Ebook;
import jreadify.plugin.Plugin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class StatisticsCalculator implements Plugin {
    @Override
    public String afterRender(String html) {
        return html;
    }

    @Override
    public void afterAssemble(Ebook ebook) {
        for (var c : ebook.chapters()) {
            String html = c.getHtmlContent();
            Document doc = Jsoup.parse(html);
            String chapterText = doc.body().text();
            String[] words = chapterText.split("\\s+");
            for (var w : words) {
                System.out.println(w);
            }
        }
    }
}
