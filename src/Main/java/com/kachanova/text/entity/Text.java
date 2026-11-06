
package com.kachanova.text.entity;

import java.util.ArrayList;
import java.util.List;

public class Text implements TextComponent {

    private final List<Paragraph> paragraphs;

    public Text() {
        paragraphs = new ArrayList<>();
    }

    public void addParagraph(Paragraph paragraph) {
        paragraphs.add(paragraph);
    }

    public List<Paragraph> getParagraphs() {
        return new ArrayList<>(paragraphs);
    }

    @Override
    public String restore() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < paragraphs.size(); i++) {
            if (i > 0) {
                result.append("\n\n");
            }

            result.append(paragraphs.get(i).restore());
        }

        return result.toString();
    }
}
