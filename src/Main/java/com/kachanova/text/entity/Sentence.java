package com.kachanova.text.entity;

import java.util.ArrayList;
import java.util.List;

public class Sentence implements TextComponent {

    private final List<Lexeme> lexemes;

    public Sentence() {
        lexemes = new ArrayList<>();
    }

    public void addLexeme(Lexeme lexeme) {
        lexemes.add(lexeme);
    }

    public List<Lexeme> getLexemes() {
        return new ArrayList<>(lexemes);
    }

    @Override
    public String restore() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < lexemes.size(); i++) {
            if (i > 0) {
                result.append(" ");
            }

            result.append(lexemes.get(i).restore());
        }

        return result.toString();
    }
}
