package com.kachanova.text.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Composite node containing Lexeme components.
public class Sentence implements TextComponent {

    private final List<Lexeme> lexemes;

    public Sentence() {
        lexemes = new ArrayList<>();
    }

    public void addLexeme(Lexeme lexeme) {
        if (lexeme == null) {
            throw new IllegalArgumentException("Lexeme cannot be null");
        }
        lexemes.add(lexeme);
    }

    public List<Lexeme> getLexemes() {
        return List.copyOf(lexemes);
    }

    @Override
    public String restore() {
        return lexemes.stream()
                .map(Lexeme::restore)
                .collect(Collectors.joining(" "));
    }
}