package com.kachanova.text.entity;

import java.util.ArrayList;
import java.util.List;

public class Lexeme implements TextComponent {

    private final List<Symbol> symbols;

    public Lexeme(String value) {
        symbols = new ArrayList<>();

        for (char symbol : value.toCharArray()) {
            symbols.add(new Symbol(symbol));
        }
    }

    public List<Symbol> getSymbols() {
        return new ArrayList<>(symbols);
    }

    @Override
    public String restore() {
        StringBuilder result = new StringBuilder();

        for (Symbol symbol : symbols) {
            result.append(symbol.restore());
        }

        return result.toString();
    }
}
