package com.kachanova.text.parser;

import com.kachanova.text.entity.TextComponent;

// Base interface for Chain of Responsibility pattern.
public interface TextParser {

    // Parses input text and returns a TextComponent.
    TextComponent parse(String text);
}