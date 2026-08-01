package br.com.cognito.estatisticas;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WordsCount {

    private final Map<String, Integer> map = new TreeMap<>();

    public void addWord(String word) {
        Integer freq = map.get(word);

        if (freq != null) {
            freq++;
        } else {
            freq = 1;
        }

        map.put(word, freq);
    }

    public Set<Map.Entry<String, Integer>> entrySet() {
        return map.entrySet();
    }
}
