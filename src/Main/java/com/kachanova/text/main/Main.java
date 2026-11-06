
package com.kachanova.text.main;

import com.kachanova.text.entity.Text;
import com.kachanova.text.parser.TextParser;
import com.kachanova.text.reader.TextReader;
import com.kachanova.text.exception.TextException;
import com.kachanova.text.service.TextAnalysisService;
import com.kachanova.text.service.TextTransformService;
import com.kachanova.text.service.impl.TextAnalysisServiceImpl;
import com.kachanova.text.service.impl.TextTransformServiceImpl;

public class Main {

    public static void main(String[] args) {
        TextReader reader = new TextReader();
        TextParserChain parserChain = new TextParserChain();

        TextAnalysisService analysisService = new TextAnalysisServiceImpl();
        TextTransformService transformService = new TextTransformServiceImpl();

        try {
            String sourceText = reader.read("data/text.txt");

            TextParser parser = parserChain.getParser();
            Text text = (Text) parser.parse(sourceText);

            System.out.println("Исходный текст:");
            System.out.println(text.restore());

            System.out.println("\n1. Подсчёт букв:");
            System.out.println(analysisService.countLetters(text));

            System.out.println("\n2. Подсчёт символов:");
            System.out.println(analysisService.countSymbols(text));
            System.out.println("\n3. Слова, встречающиеся в наибольшем количестве предложений:");
            System.out.println(analysisService.findWordsWithMaxSentenceFrequency(text));

            System.out.println("\n4. Сортировка предложений по количеству буквы 'a':");
            Text sortedText = transformService.sortSentencesByLetterCount(text, 'a');
            System.out.println(sortedText.restore());

            System.out.println("\n5. Перестановка первой и последней лексемы:");
            Text swappedText = transformService.swapFirstAndLastLexemes(text);
            System.out.println(swappedText.restore());

        } catch (TextException exception) {
            System.out.println("Ошибка чтения файла: " + exception.getMessage());
        }
    }
}
