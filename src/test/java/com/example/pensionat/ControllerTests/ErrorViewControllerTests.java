package com.example.pensionat.ControllerTests;

import com.example.pensionat.Controllers.ErrorViewController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ErrorViewController.class)
public class ErrorViewControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommandLineRunner commandLineRunner;

    @Test
    void init(){
        assertNotNull(mockMvc);
    }
    @Test
    @WithMockUser
    void handleError() throws Exception {
        String expectedResponse = "error";

        mockMvc.perform(get("/error"))
                .andExpect(view().name(expectedResponse))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void handleNotFound() throws Exception {
        String expectedResponse = "error";

        mockMvc.perform(get("/404"))
                .andExpect(view().name(expectedResponse))
                .andExpect(status().isNotFound());
    }
}
