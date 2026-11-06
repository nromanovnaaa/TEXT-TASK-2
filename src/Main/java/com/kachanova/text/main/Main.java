package com.kachanova.text.main;

import com.kachanova.text.entity.Text;
import com.kachanova.text.exception.TextException;
import com.kachanova.text.parser.LexemeParser;
import com.kachanova.text.parser.ParagraphParser;
import com.kachanova.text.parser.SentenceParser;
import com.kachanova.text.parser.TextParser;
import com.kachanova.text.parser.TextParserImpl;
import com.kachanova.text.reader.TextReader;
import com.kachanova.text.service.TextAnalysisService;
import com.kachanova.text.service.TextTransformService;
import com.kachanova.text.service.impl.TextAnalysisServiceImpl;
import com.kachanova.text.service.impl.TextTransformServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        //'var' (Local Variable Type Inferenc) I make the code shorter and cleaner.
        // The compiler automatically infers that 'reader' is of type 'TextReader'.
        //TextReader is responsible ONLY for I/O operations (reading the raw text from the file).
        var reader = new TextReader();
        var analysisService = new TextAnalysisServiceImpl();
        var transformService = new TextTransformServiceImpl();

        // Build the Chain of Responsibility
        TextParserImpl textParser = new TextParserImpl();
        ParagraphParser paragraphParser = new ParagraphParser();
        SentenceParser sentenceParser = new SentenceParser();
        LexemeParser lexemeParser = new LexemeParser();

        textParser.setNextParser(paragraphParser);
        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);

        TextParser parser = textParser;

        try {
            String sourceText = reader.read("data/text.txt");
            logger.info("File read successfully. Starting text parsing...");

            Text text = (Text) parser.parse(sourceText);
            logger.info("Text parsing completed. Total paragraphs: {}", text.getParagraphs().size());

            System.out.println("=== Исходный текст ===");
            System.out.println(text.restore());

            System.out.println("\n1. Подсчёт букв: " + analysisService.countLetters(text));
            System.out.println("2. Подсчёт символов: " + analysisService.countSymbols(text));

            System.out.println("\n3. Слова, встречающиеся в наибольшем количестве предложений:");
            System.out.println(analysisService.findWordsWithMaxSentenceFrequency(text));

            System.out.println("\n4. Сортировка предложений по количеству буквы 'a':");
            Text sortedText = transformService.sortSentencesByLetterCount(text, 'a');
            System.out.println(sortedText.restore());

            System.out.println("\n5. Перестановка первой и последней лексемы:");
            Text swappedText = transformService.swapFirstAndLastLexemes(text);
            System.out.println(swappedText.restore());

        } catch (TextException exception) {
            logger.error("Application failed: {}", exception.getMessage(), exception);
            System.err.println("Ошибка: " + exception.getMessage());
        }
    }
}