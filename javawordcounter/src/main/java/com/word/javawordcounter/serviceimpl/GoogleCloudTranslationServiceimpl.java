package com.word.javawordcounter.serviceimpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.word.javawordcounter.service.TranslationService;

@Service
public class GoogleCloudTranslationServiceimpl implements TranslationService {
    private final Translate translate;

    // Constructor to initialize the Google Cloud Translation service
    public GoogleCloudTranslationServiceimpl(@Value("${google.cloud.translation.api.key}") String apiKey) {
        // Create a Translate service instance with the provided API key
        translate = TranslateOptions.newBuilder().setApiKey(apiKey).build().getService();
    }

    // Method to translate text to English
    public String translateToEnglish(String text) {
        // Perform translation to English using the Translate service
        Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage("en"));
        return translation.getTranslatedText();
    }
}
