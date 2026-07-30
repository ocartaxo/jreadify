package br.com.cognito.estatisticas;

import jreadify.domain.Ebook;
import jreadify.plugin.Plugin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.Normalizer;

public class StatisticsCalculator implements Plugin {
    @Override
    public String afterRender(String html) {
        return html;
    }

    @Override
    public void afterAssemble(Ebook ebook) {

        WordsCount wordsCount = new WordsCount();

        for (var c : ebook.chapters()) {

            String[] words = normalizeText(c.getHtmlContent()).split("\\s+");

            for (final String word : words) {
                wordsCount.addWord(word.toUpperCase());
            }

        }

        for (var count : wordsCount.entrySet()) {
            String word = count.getKey();
            Integer freq = count.getValue();
            System.out.println(word + ": " + freq);
        }
    }

    private String normalizeText(String html) {
        Document doc = Jsoup.parse(html);
        String chapterText = doc.body().text();
        String chapterTextWithoutPunc = chapterText.replaceAll("\\p{Punct}", " ");
        return Normalizer
                .normalize(chapterTextWithoutPunc, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
