package com.kachanova.text.service.impl;

import com.kachanova.text.entity.Lexeme;
import com.kachanova.text.entity.Paragraph;
import com.kachanova.text.entity.Sentence;
import com.kachanova.text.entity.Text;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextAnalysisServiceImplTest {

    private final TextAnalysisServiceImpl service = new TextAnalysisServiceImpl();

    @Test
    void countLettersShouldReturnNumberOfLetters() {
        Lexeme lexeme = new Lexeme("fact.");
        assertEquals(4, service.countLetters(lexeme));
    }

    @Test
    void countSymbolsShouldReturnNumberOfCharacters() {
        Lexeme lexeme = new Lexeme("fact.");
        assertEquals(5, service.countSymbols(lexeme));
    }

    @Test
    void findWordsWithMaxSentenceFrequencyShouldReturnMostFrequentWords() {
        Text text = new Text();
        Paragraph paragraph = new Paragraph();

        Sentence first = new Sentence();
        first.addLexeme(new Lexeme("It"));
        first.addLexeme(new Lexeme("is"));
        first.addLexeme(new Lexeme("a"));
        first.addLexeme(new Lexeme("long"));
        first.addLexeme(new Lexeme("established"));
        first.addLexeme(new Lexeme("fact."));

        Sentence second = new Sentence();
        second.addLexeme(new Lexeme("It"));
        second.addLexeme(new Lexeme("is"));
        second.addLexeme(new Lexeme("a"));
        second.addLexeme(new Lexeme("reader."));

        Sentence third = new Sentence();
        third.addLexeme(new Lexeme("The"));
        third.addLexeme(new Lexeme("fact"));
        third.addLexeme(new Lexeme("is"));
        third.addLexeme(new Lexeme("clear."));

        paragraph.addSentence(first);
        paragraph.addSentence(second);
        paragraph.addSentence(third);
        text.addParagraph(paragraph);

        assertEquals(List.of("is"), service.findWordsWithMaxSentenceFrequency(text));
    }
}