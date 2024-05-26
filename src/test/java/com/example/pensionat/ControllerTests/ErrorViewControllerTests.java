package com.example.pensionat.ControllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ErrorViewControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void init(){
        assertNotNull(mockMvc);
    }
    @Test
    void handleError() throws Exception {
        String expectedResponse = "error";

        mockMvc.perform(get("/error"))
                .andExpect(view().name(expectedResponse))
                .andExpect(status().isOk());
    }

    @Test
    void handleNotFound() throws Exception {
        String expectedResponse = "error";

        mockMvc.perform(get("/404"))
                .andExpect(view().name(expectedResponse))
                .andExpect(status().isNotFound());
    }
}
