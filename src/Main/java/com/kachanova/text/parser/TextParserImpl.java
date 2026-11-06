package com.kachanova.text.parser;

import com.kachanova.text.entity.Paragraph;
import com.kachanova.text.entity.Text;
import com.kachanova.text.entity.TextComponent;
import com.kachanova.text.exception.TextException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Entry point parser. Splits raw text into paragraphs.
public class TextParserImpl extends AbstractTextParser {

    private static final Logger logger = LogManager.getLogger(TextParserImpl.class);

    private static final String PARAGRAPH_DELIMITER_REGEX = "(?:\\R[\\t ]*){2,}";

    @Override
    public TextComponent parse(String text) {
        if (text == null) {
            throw new TextException("Input text cannot be null");
        }

        logger.info("Starting to parse raw text into paragraphs.");

        Text result = new Text();

        if (getNextParser() == null) {
            throw new TextException("Next parser in the chain is not set. Cannot parse paragraphs.");
        }

        String[] paragraphs = text.strip().split(PARAGRAPH_DELIMITER_REGEX);

        for (String paragraphText : paragraphs) {
            if (!paragraphText.isBlank()) {
                TextComponent component = getNextParser().parse(paragraphText);
                result.addParagraph((Paragraph) component);
            }
        }

        logger.info("Finished parsing raw text. Total paragraphs found: {}", result.getParagraphs().size());
        return result;
    }
}