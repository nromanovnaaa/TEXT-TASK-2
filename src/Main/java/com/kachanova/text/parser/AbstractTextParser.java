package com.kachanova.text.parser;

// Abstract base class for the Chain of Responsibility.
public abstract class AbstractTextParser implements BasicParser {

    // Reference to the next handler in the chain.
    private BasicParser nextParser;

    // Sets the next parser.
    public void setNextParser(BasicParser nextParser) {
        this.nextParser = nextParser;
    }

    // Returns the next parser.
    protected BasicParser getNextParser() {
        return nextParser;
    }
}