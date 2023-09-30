package com.word.javawordcounter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.word.javawordcounter.controller.WordCountController;
import com.word.javawordcounter.serviceimpl.WordCountTranslatorServiceimpl;
import com.word.javawordcounter.utility.WordCounter;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WordCountController.class)
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration")
public class WordCountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordCounter wordCounter;

    private WordCountTranslatorServiceimpl wordCountTranslatorServiceimpl;

    @Test
    public void testAddWordsEndpoint() throws Exception {
        // Test the addWords endpoint
        mockMvc.perform(post("/word-counter/add-words")
                .content("[\"flower\", \"apple\"]")
                .contentType("application/json"))
                .andExpect(status().isOk());
        
        // Add assertions to verify the behavior of the addWords endpoint
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER") // Simulate an authenticated user
    public void testGetCountEndpoint() throws Exception {
        // Test the getCount endpoint
        MvcResult result = mockMvc.perform(get("/word-counter/count/flor")
                .param("sourceLanguage", "es"))
                .andExpect(status().isOk())
                .andReturn();
        
        // Mock the behavior of the wordCounter for testing purposes
        when(wordCounter.getCount("flower")).thenReturn(6);
        
        // Add assertions to verify the behavior of the getCount endpoint
    }
}
