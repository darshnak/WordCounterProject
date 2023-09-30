package com.word.javawordcounter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.word.javawordcounter.serviceimpl.WordCountTranslatorServiceimpl;
import com.word.javawordcounter.utility.WordCounter;

@RestController
@RequestMapping("/word-counter")
public class WordCountController {

    private final WordCounter wordCounter;
    private final WordCountTranslatorServiceimpl wordcounttranslatorService;

    @Autowired
    public WordCountController(WordCounter wordCounter, WordCountTranslatorServiceimpl wordcounttranslatorService) {
        // Inject the WordCounter and WordCountTranslatorServiceimpl instances
        this.wordCounter = wordCounter;
        this.wordcounttranslatorService = wordcounttranslatorService;
    }

    // Endpoint to add words to the word counter
    @PostMapping("/add-words")
    public void addWords(@RequestBody String[] words) {
        wordCounter.addWords(words);
    }

    // Endpoint to get the count of a word
    @GetMapping("/count/{word}")
    public int getCount(@PathVariable String word) {
        // Translate the word to English before counting
        String englishWord = wordcounttranslatorService.translateToEnglish(word);
        return wordCounter.getCount(englishWord);
    }
}
