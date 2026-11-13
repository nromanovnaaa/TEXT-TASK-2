package com.kachanova.text;

import com.kachanova.text.entity.Symbol;
import com.kachanova.text.entity.TextComponent;
import com.kachanova.text.entity.TextComposite;
import com.kachanova.text.parser.LexemeParser;
import com.kachanova.text.parser.ParagraphParser;
import com.kachanova.text.parser.SentenceParser;
import com.kachanova.text.parser.TextParser;
import com.kachanova.text.service.TextAnalysisService;
import com.kachanova.text.service.TextTransformService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// All-in-one test class for the entire text processing project.
// Tests parsers, analysis services, and transform services.
class TextTaskTest {

    private TextAnalysisService analysisService;
    private TextTransformService transformService;

    @BeforeEach
    void setUp() {
        analysisService = new TextAnalysisService();
        transformService = new TextTransformService();
    }

    // ==================== HELPER METHODS ====================

    // Helper: creates a lexeme from a string by adding Symbol leaves.
    private TextComponent createLexeme(String text) {
        TextComposite lexeme = new TextComposite("");
        for (char c : text.toCharArray()) {
            lexeme.add(new Symbol(c));
        }
        return lexeme;
    }

    // Helper: creates a sentence from a list of word strings.
    private TextComponent createSentence(String... words) {
        TextComposite sentence = new TextComposite(" ");
        for (String word : words) {
            sentence.add(createLexeme(word));
        }
        return sentence;
    }

    // Helper: creates a paragraph from sentences.
    private TextComponent createParagraph(TextComponent... sentences) {
        TextComposite paragraph = new TextComposite(" ");
        for (TextComponent sentence : sentences) {
            paragraph.add(sentence);
        }
        return paragraph;
    }

    // Helper: creates the root text from paragraphs.
    private TextComponent createText(TextComponent... paragraphs) {
        TextComposite text = new TextComposite("\n\n");
        for (TextComponent paragraph : paragraphs) {
            text.add(paragraph);
        }
        return text;
    }

    // Helper: assembles the full parser chain.
    private TextParser buildParserChain() {
        TextParser textParser = new TextParser();
        ParagraphParser paragraphParser = new ParagraphParser();
        SentenceParser sentenceParser = new SentenceParser();
        LexemeParser lexemeParser = new LexemeParser();

        textParser.setNextParser(paragraphParser);
        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);

        return textParser;
    }

    // ==================== PARSER TESTS ====================

    @Test
    void parserShouldCreateTextFromRawString() {
        // Arrange: Build the parser chain.
        TextParser parser = buildParserChain();
        String rawText = "Hello world. This is a test.";

        // Act: Parse the text.
        TextComponent result = parser.parse(rawText);

        // Assert: The result should not be null and should contain all words.
        assertNotNull(result);
        String restored = result.restore();
        assertTrue(restored.contains("Hello"));
        assertTrue(restored.contains("world."));
        assertTrue(restored.contains("This"));
        assertTrue(restored.contains("test."));
    }

    @Test
    void parserShouldHandleMultipleParagraphs() {
        // Arrange: Build the parser chain.
        TextParser parser = buildParserChain();
        String rawText = "First paragraph.\n\nSecond paragraph.";

        // Act
        TextComponent result = parser.parse(rawText);

        // Assert: Root text should have 2 children (paragraphs).
        assertEquals(2, result.getChildren().size());
    }

    // ==================== ANALYSIS SERVICE TESTS ====================

    @Test
    void countLettersShouldReturnNumberOfLetters() {
        // Arrange: Create a lexeme "fact." (4 letters + 1 punctuation).
        TextComponent lexeme = createLexeme("fact.");

        // Act & Assert: Expect exactly 4 letters.
        assertEquals(4, analysisService.countLetters(lexeme));
    }

    @Test
    void countSymbolsShouldReturnNumberOfCharacters() {
        // Arrange: Create a lexeme "fact." (5 total characters).
        TextComponent lexeme = createLexeme("fact.");

        // Act & Assert: Expect 5 symbols in total.
        assertEquals(5, analysisService.countSymbols(lexeme));
    }

    @Test
    void findWordsWithMaxSentenceFrequencyShouldReturnMostFrequentWords() {
        // Arrange: Build a text structure using phrases from the task.
        TextComponent sentence1 = createSentence("It", "is", "a", "long", "established", "fact.");
        TextComponent sentence2 = createSentence("It", "is", "a", "reader.");
        TextComponent sentence3 = createSentence("The", "fact", "is", "clear.");

        TextComponent paragraph = createParagraph(sentence1, sentence2, sentence3);
        TextComponent text = createText(paragraph);

        // Act
        List<String> result = analysisService.findWordsWithMaxSentenceFrequency(text);

        // Assert: "is" appears in all 3 sentences (max frequency = 3).
        assertEquals(List.of("is"), result);
    }

    @Test
    void findWordsWithMaxSentenceFrequencyShouldReturnMultipleWords() {
        // Arrange: Build a text where multiple words have the same max frequency.
        TextComponent sentence1 = createSentence("The", "dog", "runs.");
        TextComponent sentence2 = createSentence("The", "cat", "sleeps.");
        TextComponent sentence3 = createSentence("A", "dog", "plays.");

        TextComponent paragraph = createParagraph(sentence1, sentence2, sentence3);
        TextComponent text = createText(paragraph);

        // Act
        List<String> result = analysisService.findWordsWithMaxSentenceFrequency(text);

        // Assert: "the" and "dog" both appear in 2 sentences, sorted alphabetically.
        assertEquals(List.of("dog", "the"), result);
    }

    // ==================== TRANSFORM SERVICE TESTS ====================

    @Test
    void sortSentencesByLetterCountShouldSortAscending() {
        // Arrange: Create sentences with different counts of letter 'a'.
        TextComponent sentence1 = createSentence("Bye."); // 0 'a'
        TextComponent sentence2 = createSentence("It", "is", "a", "fact."); // 2 'a'
        TextComponent sentence3 = createSentence("A", "cat", "plays."); // 3 'a'

        TextComponent paragraph = createParagraph(sentence1, sentence2, sentence3);
        TextComponent text = createText(paragraph);

        // Act: Sort by letter 'a'.
        TextComponent sortedText = transformService.sortSentencesByLetterCount(text, 'a');

        // Assert: Expected order: "Bye." (0), "It is a fact." (2), "A cat plays." (3)
        String expected = "Bye. It is a fact. A cat plays.";
        assertEquals(expected, sortedText.restore());
    }

    @Test
    void swapFirstAndLastLexemesShouldSwapCorrectly() {
        // Arrange: Create a sentence with multiple lexemes.
        TextComponent sentence = createSentence("It", "has", "survived", "unchanged.");
        TextComponent paragraph = createParagraph(sentence);
        TextComponent text = createText(paragraph);

        // Act: Swap first and last lexemes.
        TextComponent swappedText = transformService.swapFirstAndLastLexemes(text);

        // Assert: Expected "unchanged. has survived It"
        String expected = "unchanged. has survived It";
        assertEquals(expected, swappedText.restore());
    }

    @Test
    void swapFirstAndLastLexemesShouldNotChangeSingleLexemeSentence() {
        // Arrange: Create a sentence with only one lexeme.
        TextComponent sentence = createSentence("Bye.");
        TextComponent paragraph = createParagraph(sentence);
        TextComponent text = createText(paragraph);

        // Act: Swap first and last lexemes.
        TextComponent swappedText = transformService.swapFirstAndLastLexemes(text);

        // Assert: Should remain unchanged.
        String expected = "Bye.";
        assertEquals(expected, swappedText.restore());
    }
}