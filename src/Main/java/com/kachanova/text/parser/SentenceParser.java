package com.kachanova.text.parser;

import com.kachanova.text.entity.Lexeme;
import com.kachanova.text.entity.Sentence;
import com.kachanova.text.entity.TextComponent;
import com.kachanova.text.exception.TextException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Splits sentence text into lexemes.
public class SentenceParser extends AbstractTextParser {

    private static final Logger logger = LogManager.getLogger(SentenceParser.class);

    private static final String LEXEME_DELIMITER_REGEX = "\\s+";

    @Override
    public TextComponent parse(String text) {
        if (text == null) {
            throw new TextException("Input text cannot be null");
        }

        logger.info("Starting to parse sentence into lexemes.");

        Sentence sentence = new Sentence();

        if (getNextParser() == null) {
            throw new TextException("Next parser in the chain is not set. Cannot parse lexemes.");
        }

        String strippedText = text.strip();

        if (strippedText.isBlank()) {
            logger.info("Empty text provided, returning empty sentence.");
            return sentence;
        }

        String[] lexemes = strippedText.split(LEXEME_DELIMITER_REGEX);

        for (String lexemeText : lexemes) {
            TextComponent component = getNextParser().parse(lexemeText);
            sentence.addLexeme((Lexeme) component);
        }

        logger.info("Finished parsing sentence. Total lexemes found: {}", sentence.getLexemes().size());
        return sentence;
    }
}