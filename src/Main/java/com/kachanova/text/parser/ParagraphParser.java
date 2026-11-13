package com.kachanova.text.parser;

import com.kachanova.text.entity.TextComponent;
import com.kachanova.text.entity.TextComposite;

// Concrete parser. Splits paragraph text into sentences.
public class ParagraphParser extends AbstractTextParser {

    // Regex to split by sentence-ending punctuation followed by whitespace.
    private static final String SENTENCE_DELIMITER_REGEX = "(?<=[.!?])\\s+";

    @Override
    public TextComponent parse(String text) {
        TextComposite paragraph = new TextComposite(" ");
        String[] sentences = text.strip().split(SENTENCE_DELIMITER_REGEX);

        for (String sentenceText : sentences) {
            // Ensure the sentence is not blank.
            if (!sentenceText.strip().isBlank()) {
                paragraph.add(getNextParser().parse(sentenceText));
            }
        }
        return paragraph;
    }
}