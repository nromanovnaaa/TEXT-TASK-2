package com.kachanova.text.parser;

import com.kachanova.text.entity.Symbol;
import com.kachanova.text.entity.TextComponent;
import com.kachanova.text.entity.TextComposite;

// Final parser in the chain. Creates a composite of Symbols (a lexeme).
public class LexemeParser extends AbstractTextParser {

    @Override
    public TextComponent parse(String text) {
        // Create a lexeme composite with an empty delimiter.
        TextComposite lexeme = new TextComposite("");
        // Break the string down into individual Symbol objects.
        for (char c : text.toCharArray()) {
            lexeme.add(new Symbol(c));
        }
        return lexeme;
    }
}