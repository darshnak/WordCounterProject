package com.word.javawordcounter.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.word.javawordcounter.service.TranslationService;

@Service
public class WordCountTranslatorServiceimpl {
    private final TranslationService translationService;

    // Constructor to inject the TranslationService dependency
    @Autowired
    public WordCountTranslatorServiceimpl(TranslationService translationService) {
        this.translationService = translationService;
    }

    // Method to translate text to English using the injected TranslationService
    public String translateToEnglish(String text) {
        return translationService.translateToEnglish(text);
    }
}
