package com.kachanova.text.entity;

import java.util.List;

// Base interface for the Composite pattern.
public interface TextComponent {
    String restore();
    void add(TextComponent child);
    List<TextComponent> getChildren();
}