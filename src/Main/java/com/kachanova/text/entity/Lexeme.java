package com.kachanova.text.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Composite node containing Symbol leaves.
public class Lexeme implements TextComponent {

    private final List<Symbol> symbols;

    public Lexeme(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Lexeme value cannot be null");
        }
        symbols = new ArrayList<>(value.length());
        for (char character : value.toCharArray()) {
            symbols.add(new Symbol(character));
        }
    }

    public List<Symbol> getSymbols() {
        return List.copyOf(symbols);
    }

    @Override
    public String restore() {
        return symbols.stream()
                .map(Symbol::restore)
                .collect(Collectors.joining());
    }
}