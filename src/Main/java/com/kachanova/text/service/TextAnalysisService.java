package com.kachanova.text.service;

import com.kachanova.text.entity.TextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Service for text analysis tasks (read-only operations).
public class TextAnalysisService {

    private static final Logger LOGGER = LogManager.getLogger(TextAnalysisService.class);

    // Counts only alphabetic letters in the restored text.
    public int countLetters(TextComponent component) {
        return (int) component.restore().chars()
                .filter(Character::isLetter)
                .count();
    }

    // Counts all characters, including spaces and punctuation.
    public int countSymbols(TextComponent component) {
        return component.restore().length();
    }

    // Task 1: Finds words occurring in the maximum number of sentences.
    public List<String> findWordsWithMaxSentenceFrequency(TextComponent component) {
        Map<String, Integer> wordSentenceCount = new HashMap<>();

        // Iterate through paragraphs (children of the root text component).
        for (TextComponent paragraph : component.getChildren()) {
            // Iterate through sentences (children of the paragraph).
            for (TextComponent sentence : paragraph.getChildren()) {
                Set<String> uniqueWords = new HashSet<>();

                // Iterate through lexemes (children of the sentence).
                for (TextComponent lexeme : sentence.getChildren()) {
                    // Clean the word from punctuation and convert to lowercase.
                    String word = lexeme.restore()
                            .replaceAll("[^\\p{L}\\p{N}]", "")
                            .toLowerCase();

                    if (!word.isBlank()) {
                        uniqueWords.add(word);
                    }
                }

                // Increment count for each unique word in this sentence.
                for (String word : uniqueWords) {
                    wordSentenceCount.merge(word, 1, Integer::sum);
                }
            }
        }

        if (wordSentenceCount.isEmpty()) {
            return List.of();
        }

        // Find the maximum frequency value.
        int maxFrequency = wordSentenceCount.values().stream()
                .max(Integer::compareTo)
                .orElse(0);

        // Filter words with max frequency, sort them, and return as an immutable list.
        List<String> result = wordSentenceCount.entrySet().stream()
                .filter(entry -> entry.getValue() == maxFrequency)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();

        LOGGER.info("Words with max sentence frequency ({} times): {}", maxFrequency, result);
        return result;
    }
}