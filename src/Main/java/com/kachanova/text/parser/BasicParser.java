package com.kachanova.text.parser;

import com.kachanova.text.entity.TextComponent;

// Base interface for the Chain of Responsibility pattern.
// Defines the contract for all parsers in the chain.
public interface BasicParser {

    // Parses the input string and returns a TextComponent.
    TextComponent parse(String text);
}