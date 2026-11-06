package com.kachanova.text.parser;

// Abstract base class for Chain of Responsibility.
// Holds reference to the next parser in the chain.
public abstract class AbstractTextParser implements TextParser {

    private TextParser nextParser;

    public void setNextParser(TextParser nextParser) {
        this.nextParser = nextParser;
    }

    protected TextParser getNextParser() {
        return nextParser;
    }
}