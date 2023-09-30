package com.word.javawordcounter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.word.javawordcounter.service.TranslationService;
import com.word.javawordcounter.serviceimpl.WordCountTranslatorServiceimpl;

@ExtendWith(MockitoExtension.class)
public class WordCountTranslatorServiceTest {

    @InjectMocks
    private WordCountTranslatorServiceimpl translatorService; // The service under test

    @Mock
    private TranslationService translationService; // Mock dependency

    @Test
    public void testTranslateToEnglish() {
        // Define the expected input and output
        String inputText = "flor";
        String expectedTranslation = "flower";

        // Mock the behavior of the translationService
        when(translationService.translateToEnglish(inputText)).thenReturn(expectedTranslation);

        // Call the method being tested
        String result = translatorService.translateToEnglish(inputText);

        // Assert that the result matches the expected translation
        assertEquals(expectedTranslation, result);
    }
}
