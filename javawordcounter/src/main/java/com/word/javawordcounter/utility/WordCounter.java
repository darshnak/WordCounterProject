package com.word.javawordcounter.utility;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WordCounter {
    private static final Logger logger = LoggerFactory.getLogger(WordCounter.class);
    private final Map<String, Integer> wordCount;
    private final Translate translate;

    // Constructor to initialize the WordCounter
    public WordCounter(@Value("${google.cloud.translation.api.key}") String apiKey) {
        logger.info("Attempting to make a translation request...");
        wordCount = new HashMap<>();
        try {
            // Initialize the translation service with the provided API key
            translate = TranslateOptions.newBuilder().setApiKey(apiKey).build().getService();
        } catch (Exception e) {
            // Log and rethrow an exception if initialization fails
            logger.error("Error initializing Translate service with API key", e);
            throw new RuntimeException("Error initializing Translate service", e);
        }
    }

    // Method to add words to the word count
    public void addWords(String... words) {
        for (String word : words) {
            if (isValidWord(word)) {
                // Translate the word to English and update the word count
                String englishWord = translateToEnglish(word);
                wordCount.put(englishWord.toLowerCase(), wordCount.getOrDefault(englishWord.toLowerCase(), 0) + 1);
            }
        }
    }

    // Method to get the count of a word
    public int getCount(String word) {
        return wordCount.getOrDefault(word.toLowerCase(), 0);
    }

    // Method to check if a word contains only alphabetic characters
    private boolean isValidWord(String word) {
        return word.matches("[a-zA-Z]+");
    }

    // Method to translate text to English
    private String translateToEnglish(String text) {
        Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage("en"));
        return translation.getTranslatedText();
    }
}
