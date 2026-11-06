package com.kachanova.text.service.impl;

import com.kachanova.text.entity.Lexeme;
import com.kachanova.text.entity.Paragraph;
import com.kachanova.text.entity.Sentence;
import com.kachanova.text.entity.Text;
import com.kachanova.text.entity.TextComponent;
import com.kachanova.text.exception.TextException;
import com.kachanova.text.service.TextAnalysisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TextAnalysisServiceImpl implements TextAnalysisService {

    private static final Logger LOGGER = LogManager.getLogger(TextAnalysisServiceImpl.class);

    @Override
    public int countLetters(TextComponent component) {
        if (component == null) {
            throw new TextException("TextComponent cannot be null");
        }
        return (int) component.restore().chars()
                .filter(Character::isLetter)
                .count();
    }

    @Override
    public int countSymbols(TextComponent component) {
        if (component == null) {
            throw new TextException("TextComponent cannot be null");
        }
        return component.restore().length();
    }

    @Override
    public List<String> findWordsWithMaxSentenceFrequency(TextComponent component) {
        if (!(component instanceof Text)) {
            LOGGER.warn("Provided component is not a Text instance. Returning empty list.");
            return List.of();
        }

        Text text = (Text) component;
        Map<String, Integer> wordSentenceCount = new HashMap<>();

        for (Paragraph paragraph : text.getParagraphs()) {
            for (Sentence sentence : paragraph.getSentences()) {
                Set<String> uniqueWordsInSentence = new HashSet<>();

                for (Lexeme lexeme : sentence.getLexemes()) {
                    String word = lexeme.restore()
                            .replaceAll("[^\\p{L}\\p{N}]", "")
                            .toLowerCase();

                    if (!word.isBlank()) {
                        uniqueWordsInSentence.add(word);
                    }
                }

                for (String word : uniqueWordsInSentence) {
                    wordSentenceCount.merge(word, 1, Integer::sum);
                }
            }
        }

        if (wordSentenceCount.isEmpty()) {
            return List.of();
        }

        int maxFrequency = wordSentenceCount.values().stream()
                .max(Integer::compareTo)
                .orElse(0);

        List<String> result = wordSentenceCount.entrySet().stream()
                .filter(entry -> entry.getValue() == maxFrequency)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();

        LOGGER.info("Words with max sentence frequency ({} times): {}", maxFrequency, result);
        return result;
    }
}