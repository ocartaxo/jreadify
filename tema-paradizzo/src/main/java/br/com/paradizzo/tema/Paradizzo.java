package br.com.paradizzo.tema;

import jreadify.domain.Ebook;
import jreadify.plugin.Plugin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Paradizzo implements Plugin {

    public static final String THEME_CSS_PATH = "/theme.css";

    @Override
    public String afterRender(String chapterHtml) {
        return applyTheme(chapterHtml);
    }

    @Override
    public void afterAssemble(Ebook ebook) {
        return;
    }

    private String getCSSTheme(){
        return FileUtils.getResourceContents(THEME_CSS_PATH);
    }

    private String applyTheme(String html){
        Document doc = Jsoup.parse(html);
        var css = getCSSTheme();

        doc.select("head").append("<style> "  + css + " </style>");

        return doc.html();
    }
}
