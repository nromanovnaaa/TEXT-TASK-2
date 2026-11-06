package com.kachanova.text.service;

import com.kachanova.text.entity.Text;

// Service interface for text transformation tasks.
public interface TextTransformService {

    // Task 2: Sort sentences by count of a specific letter.
    Text sortSentencesByLetterCount(Text text, char letter);

    // Task 3: Swap first and last lexeme in each sentence.
    Text swapFirstAndLastLexemes(Text text);
}