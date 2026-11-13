package com.kachanova.text.entity;

import java.util.Collections;
import java.util.List;

// Leaf node in the Composite pattern. Represents a single character.
public class Symbol implements TextComponent {

    private final char value;

    public Symbol(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public String restore() {
        return String.valueOf(value);
    }

    // Реализация метода getChildren() — возвращает пустой список
    @Override
    public List<TextComponent> getChildren() {
        return Collections.emptyList();
    }
}