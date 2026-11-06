package com.kachanova.text.service.impl;

import com.kachanova.text.entity.Lexeme;
import com.kachanova.text.entity.Paragraph;
import com.kachanova.text.entity.Sentence;
import com.kachanova.text.entity.Text;
import com.kachanova.text.exception.TextException;
import com.kachanova.text.service.TextTransformService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TextTransformServiceImpl implements TextTransformService {

    private static final Logger LOGGER = LogManager.getLogger(TextTransformServiceImpl.class);

    @Override
    public Text sortSentencesByLetterCount(Text text, char letter) {
        if (text == null) {
            throw new TextException("Text cannot be null");
        }

        LOGGER.info("Sorting sentences by the count of letter: '{}'", letter);
        Text result = new Text();

        for (Paragraph paragraph : text.getParagraphs()) {
            Paragraph sortedParagraph = new Paragraph();

            List<Sentence> sortedSentences = paragraph.getSentences().stream()
                    .sorted(Comparator.comparingInt(sentence -> countLetter(sentence, letter)))
                    .toList();

            for (Sentence sentence : sortedSentences) {
                sortedParagraph.addSentence(sentence);
            }
            result.addParagraph(sortedParagraph);
        }

        LOGGER.info("Sorting completed successfully.");
        return result;
    }

    @Override
    public Text swapFirstAndLastLexemes(Text text) {
        if (text == null) {
            throw new TextException("Text cannot be null");
        }

        LOGGER.info("Swapping first and last lexemes in each sentence.");
        Text result = new Text();

        for (Paragraph paragraph : text.getParagraphs()) {
            Paragraph transformedParagraph = new Paragraph();

            for (Sentence sentence : paragraph.getSentences()) {
                List<Lexeme> lexemes = new ArrayList<>(sentence.getLexemes());

                if (lexemes.size() > 1) {
                    Collections.swap(lexemes, 0, lexemes.size() - 1);
                }

                Sentence transformedSentence = new Sentence();
                for (Lexeme lexeme : lexemes) {
                    transformedSentence.addLexeme(lexeme);
                }
                transformedParagraph.addSentence(transformedSentence);
            }
            result.addParagraph(transformedParagraph);
        }

        LOGGER.info("Swapping completed successfully.");
        return result;
    }

    private int countLetter(Sentence sentence, char letter) {
        long count = sentence.restore().chars()
                .filter(ch -> Character.toLowerCase(ch) == Character.toLowerCase(letter))
                .count();
        return (int) count;
    }
}