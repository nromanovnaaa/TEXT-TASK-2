package com.kachanova.text.entity;

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
}