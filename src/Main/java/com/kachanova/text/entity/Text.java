package com.kachanova.text.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Root Composite node containing Paragraph components.
public class Text implements TextComponent {

    private final List<Paragraph> paragraphs;

    public Text() {
        paragraphs = new ArrayList<>();
    }

    public void addParagraph(Paragraph paragraph) {
        if (paragraph == null) {
            throw new IllegalArgumentException("Paragraph cannot be null");
        }
        paragraphs.add(paragraph);
    }

    public List<Paragraph> getParagraphs() {
        return List.copyOf(paragraphs);
    }

    @Override
    public String restore() {
        return paragraphs.stream()
                .map(Paragraph::restore)
                .collect(Collectors.joining("\n\n"));
    }
}