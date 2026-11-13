package com.kachanova.text.main;

import com.kachanova.text.entity.TextComponent;
import com.kachanova.text.exception.TextException;
import com.kachanova.text.parser.AbstractTextParser;
import com.kachanova.text.parser.LexemeParser;
import com.kachanova.text.parser.ParagraphParser;
import com.kachanova.text.parser.SentenceParser;
import com.kachanova.text.parser.TextParser;
import com.kachanova.text.reader.TextReader;
import com.kachanova.text.service.TextAnalysisService;
import com.kachanova.text.service.TextTransformService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Entry point of the application.
// Responsible for assembling the Chain of Responsibility, reading the file, and executing tasks.
public class Main {

    // SLF4J logger for internal process tracking and error handling.
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        // Initialize the reader and services.
        var reader = new TextReader();
        var analysisService = new TextAnalysisService();
        var transformService = new TextTransformService();

        // Assemble the Chain of Responsibility.
        // Each parser is responsible for its own level of the text hierarchy.
        TextParser textParser = new TextParser();
        ParagraphParser paragraphParser = new ParagraphParser();
        SentenceParser sentenceParser = new SentenceParser();
        LexemeParser lexemeParser = new LexemeParser();

        // Link parsers together in the correct order:
        // TextParser -> ParagraphParser -> SentenceParser -> LexemeParser
        textParser.setNextParser(paragraphParser);
        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);

        try {
            // Read the raw text from the file.
            String sourceText = reader.read("data/text.txt");
            logger.info("File read successfully. Starting text parsing...");

            // Parse the text into the Composite structure.
            TextComponent text = textParser.parse(sourceText);
            logger.info("Text parsing completed.");

            // Display the restored original text to the user.
            System.out.println("=== Исходный текст ===");
            System.out.println(text.restore());

            // Task: Count letters and symbols.
            System.out.println("\n1. Подсчёт букв: " + analysisService.countLetters(text));
            System.out.println("2. Подсчёт символов: " + analysisService.countSymbols(text));

            // Task 1: Find words occurring in the maximum number of sentences.
            System.out.println("\n3. Слова, встречающиеся в наибольшем количестве предложений:");
            System.out.println(analysisService.findWordsWithMaxSentenceFrequency(text));

            // Task 2: Sort sentences by the count of letter 'a'.
            System.out.println("\n4. Сортировка предложений по количеству буквы 'a':");
            TextComponent sortedText = transformService.sortSentencesByLetterCount(text, 'a');
            System.out.println(sortedText.restore());

            // Task 3: Swap first and last lexemes in each sentence.
            System.out.println("\n5. Перестановка первой и последней лексемы:");
            TextComponent swappedText = transformService.swapFirstAndLastLexemes(text);
            System.out.println(swappedText.restore());

        } catch (TextException exception) {
            // Log the error with stack trace for debugging using Log4j2.
            logger.error("Application failed: {}", exception.getMessage(), exception);
            // Show a user-friendly message in the console.
            System.err.println("Ошибка: " + exception.getMessage());
        }
    }
}