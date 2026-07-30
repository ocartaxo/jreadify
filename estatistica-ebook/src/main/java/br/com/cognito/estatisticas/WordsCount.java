package br.com.cognito.estatisticas;

import java.util.TreeMap;

public class WordsCount extends TreeMap<String, Integer> {

    public void addWord(String word) {
        Integer freq = get(word);

        if (freq != null) {
            freq++;
        } else {
            freq = 1;
        }

        put(word, freq);
    }
}
