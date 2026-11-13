package com.kachanova.text.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Universal composite class. Acts as both a container and a leaf.
public class TextComposite implements TextComponent {

    private final List<TextComponent> children = new ArrayList<>();
    private final String delimiter;
    private String value; // Used if this component is a leaf (e.g., a single lexeme)

    // Constructor for a composite node (e.g., Text, Paragraph)
    public TextComposite(String delimiter) {
        this.delimiter = delimiter;
    }

    // Constructor for a leaf node (e.g., Lexeme)
    public TextComposite(String delimiter, String value) {
        this.delimiter = delimiter;
        this.value = value;
    }

    @Override
    public void add(TextComponent child) {
        children.add(child);
    }

    @Override
    public List<TextComponent> getChildren() {
        return children;
    }

    @Override
    public String restore() {
        // If it's a leaf, just return its value
        if (value != null) {
            return value;
        }
        // If it's a composite, join children with the delimiter
        return children.stream()
                .map(TextComponent::restore)
                .collect(Collectors.joining(delimiter));
    }
}