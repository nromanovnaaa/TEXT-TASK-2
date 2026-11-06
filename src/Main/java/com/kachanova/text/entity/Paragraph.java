package com.kachanova.text.entity;

import java.util.ArrayList;
import java.util.List;

public class Paragraph implements TextComponent {

    private final List<Sentence> sentences;

    public Paragraph() {
        sentences = new ArrayList<>();
    }

    public void addSentence(Sentence sentence) {
        sentences.add(sentence);
    }

    public List<Sentence> getSentences() {
        return new ArrayList<>(sentences);
    }

    @Override
    public String restore() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < sentences.size(); i++) {
            if (i > 0) {
                result.append(" ");
            }

            result.append(sentences.get(i).restore());
        }

        return result.toString();
    }
}

