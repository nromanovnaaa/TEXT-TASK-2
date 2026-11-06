package com.kachanova.text.parser;

import com.kachanova.text.entity.Paragraph;
import com.kachanova.text.entity.Sentence;
import com.kachanova.text.entity.TextComponent;
import com.kachanova.text.exception.TextException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Splits paragraph text into sentences.
public class ParagraphParser extends AbstractTextParser {

    private static final Logger logger = LogManager.getLogger(ParagraphParser.class);

    private static final String SENTENCE_DELIMITER_REGEX = "(?<=[.!?])\\s+";

    @Override
    public TextComponent parse(String text) {
        if (text == null) {
            throw new TextException("Input text cannot be null");
        }

        logger.info("Starting to parse paragraph into sentences.");

        Paragraph paragraph = new Paragraph();

        if (getNextParser() == null) {
            throw new TextException("Next parser in the chain is not set. Cannot parse sentences.");
        }

        String[] sentences = text.strip().split(SENTENCE_DELIMITER_REGEX);

        for (String sentenceText : sentences) {
            if (!sentenceText.isBlank()) {
                TextComponent component = getNextParser().parse(sentenceText);
                paragraph.addSentence((Sentence) component);
            }
        }

        logger.info("Finished parsing paragraph. Total sentences found: {}", paragraph.getSentences().size());
        return paragraph;
    }
}