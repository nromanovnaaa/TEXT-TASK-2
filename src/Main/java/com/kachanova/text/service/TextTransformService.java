package com.kachanova.text.service;

import com.kachanova.text.entity.TextComponent;
import com.kachanova.text.entity.TextComposite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Service for text transformation tasks (modifying structure).
public class TextTransformService {

    private static final Logger LOGGER = LogManager.getLogger(TextTransformService.class);

    // Task 2: Sorts sentences in each paragraph by the count of a specific letter.
    public TextComponent sortSentencesByLetterCount(TextComponent text, char letter) {
        TextComposite result = new TextComposite("\n\n");

        for (TextComponent paragraph : text.getChildren()) {
            TextComposite sortedParagraph = new TextComposite(" ");

            // Sort sentences using Stream API and a custom comparator.
            List<TextComponent> sortedSentences = paragraph.getChildren().stream()
                    .sorted(Comparator.comparingInt(sentence -> countLetter(sentence, letter)))
                    .toList();

            for (TextComponent sentence : sortedSentences) {
                sortedParagraph.add(sentence);
            }
            result.add(sortedParagraph);
        }

        LOGGER.info("Sorting completed successfully.");
        return result;
    }

    // Task 3: Swaps the first and last lexeme in every sentence.
    public TextComponent swapFirstAndLastLexemes(TextComponent text) {
        TextComposite result = new TextComposite("\n\n");

        for (TextComponent paragraph : text.getChildren()) {
            TextComposite transformedParagraph = new TextComposite(" ");

            for (TextComponent sentence : paragraph.getChildren()) {
                // Create a mutable copy of the lexemes list.
                List<TextComponent> lexemes = new ArrayList<>(sentence.getChildren());

                // Swap first and last elements if there are more than one.
                if (lexemes.size() > 1) {
                    Collections.swap(lexemes, 0, lexemes.size() - 1);
                }

                TextComposite transformedSentence = new TextComposite(" ");
                for (TextComponent lexeme : lexemes) {
                    transformedSentence.add(lexeme);
                }
                transformedParagraph.add(transformedSentence);
            }
            result.add(transformedParagraph);
        }

        LOGGER.info("Swapping completed successfully.");
        return result;
    }

    // Helper method to count occurrences of a specific letter in a sentence.
    private int countLetter(TextComponent sentence, char letter) {
        long count = sentence.restore().chars()
                .filter(ch -> Character.toLowerCase(ch) == Character.toLowerCase(letter))
                .count();
        return (int) count;
    }
}