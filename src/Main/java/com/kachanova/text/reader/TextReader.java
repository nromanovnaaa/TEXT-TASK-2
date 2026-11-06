package com.kachanova.text.reader;

import com.kachanova.text.exception.TextException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextReader {

    private static final Logger logger = LogManager.getLogger(TextReader.class);

    public String read(String filePath) throws TextException {
        if (filePath == null) {
            throw new TextException("File path cannot be null");
        }

        logger.info("Attempting to read text from file: {}", filePath);

        try {
            String content = Files.readString(Path.of(filePath));
            logger.info("Successfully read file. Total characters: {}", content.length());
            return content;
        } catch (IOException exception) {
            logger.error("Failed to read file: {}", filePath, exception);
            throw new TextException("Cannot read file: " + filePath, exception);
        }
    }
}