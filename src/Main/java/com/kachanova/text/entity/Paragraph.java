package com.kachanova.text.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Composite node containing Sentence components.
public class Paragraph implements TextComponent {

    private final List<Sentence> sentences;

    public Paragraph() {
        sentences = new ArrayList<>();
    }

    public void addSentence(Sentence sentence) {
        if (sentence == null) {
            throw new IllegalArgumentException("Sentence cannot be null");
        }
        sentences.add(sentence);
    }

    public List<Sentence> getSentences() {
        return List.copyOf(sentences);
    }

    @Override
    public String restore() {
        return sentences.stream()
                .map(Sentence::restore)
                .collect(Collectors.joining(" "));
    }
}