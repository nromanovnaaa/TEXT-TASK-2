package com.kachanova.text.service;

import com.kachanova.text.entity.TextComponent;
import java.util.List;

// Service interface for text analysis tasks.
public interface TextAnalysisService {

    int countLetters(TextComponent component);

    int countSymbols(TextComponent component);

    // Task 1: Find words occurring in the maximum number of sentences.
    List<String> findWordsWithMaxSentenceFrequency(TextComponent component);
}