package com.kachanova.text.parser;

public abstract class AbstractTextParser implements TextParser {

    private TextParser nextParser;

    public void setNextParser(TextParser nextParser) {
        this.nextParser = nextParser;
    }

    protected TextParser getNextParser() {
        return nextParser;
    }
}
