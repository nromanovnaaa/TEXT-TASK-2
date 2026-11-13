package com.kachanova.text.parser;

import com.kachanova.text.entity.TextComponent;
import com.kachanova.text.entity.TextComposite;

// Concrete parser. Splits sentence text into lexemes.
public class SentenceParser extends AbstractTextParser {

    // Regex to split by one or more whitespace characters.
    private static final String LEXEME_DELIMITER_REGEX = "\\s+";

    @Override
    public TextComponent parse(String text) {
        TextComposite sentence = new TextComposite(" ");
        String[] lexemes = text.strip().split(LEXEME_DELIMITER_REGEX);

        for (String lexemeText : lexemes) {
            // Ensure the lexeme is not blank.
            if (!lexemeText.strip().isBlank()) {
                sentence.add(getNextParser().parse(lexemeText));
            }
        }
        return sentence;
    }
}