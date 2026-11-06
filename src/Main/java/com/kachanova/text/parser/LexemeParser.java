package com.kachanova.text.parser;

import com.kachanova.text.entity.Lexeme;
import com.kachanova.text.entity.TextComponent;
import com.kachanova.text.exception.TextException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Final parser in the chain. Creates Lexeme leaf nodes.
public class LexemeParser extends AbstractTextParser {

    private static final Logger logger = LogManager.getLogger(LexemeParser.class);

    @Override
    public TextComponent parse(String text) {
        if (text == null) {
            throw new TextException("Input text cannot be null");
        }

        logger.info("Creating a Lexeme component from text chunk: '{}'.", text);
        return new Lexeme(text);
    }
}