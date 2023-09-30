package com.word.javawordcounter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.translate.Translate;
import com.google.cloud.translate.Translation;
import com.word.javawordcounter.service.TranslationService;
import com.word.javawordcounter.serviceimpl.WordCountTranslatorServiceimpl;
import com.word.javawordcounter.utility.WordCounter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Activate the "test" profile
@TestPropertySource(locations = "classpath:security-config.json")
@SpringJUnitConfig
public class WordCountTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private TranslationService translationService;

    @InjectMocks
    private WordCountTranslatorServiceimpl translatorService;

    @Autowired
    private WordCounter wordCounter; // The service under test

    @BeforeEach
    public void setUp() {
        when(translationService.translateToEnglish("flor")).thenReturn("flower");
    }

    @Test
    @WithMockUser // This annotation disables security for this test
    public void testAddWords() throws Exception {
        // Test the addWords endpoint with various word inputs

        mockMvc.perform(MockMvcRequestBuilders.post("/word-counter/add-words")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new String[]{"flower", "flor", "blume"})))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/word-counter/add-words")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new String[]{"123", "!!!"})))
                .andExpect(status().isOk()); // Non-alphabetic characters should be ignored

        mockMvc.perform(MockMvcRequestBuilders.post("/word-counter/add-words")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new String[]{"flower", "123"})))
                .andExpect(status().isOk()); // Mix of valid and invalid words
    }

    @Test
    public void testTranslateToEnglish() {
        // Test the translation service
        String inputText = "flor";
        String expectedTranslation = "flower";

        String result = translatorService.translateToEnglish(inputText);

        assertEquals(expectedTranslation, result);
    }

    @Test
    @WithMockUser // This annotation disables security for this test
    public void testGetCount() throws Exception {
        // Test the getCount endpoint

        // Add words to the word counter
        wordCounter.addWords("flower", "flor", "blume");

        // Test counting a word in different languages
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/word-counter/count/flor")
                .param("sourceLanguage", "es")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int count = Integer.parseInt(content);

        // Debugging: Print the count and check the translation
        System.out.println("Count: " + count);
        String translated = translatorService.translateToEnglish("flor");
        System.out.println("Translated: " + translated);
        //it is converted two word in English
        assertEquals(2, count); // Count should be 2
    }
}
