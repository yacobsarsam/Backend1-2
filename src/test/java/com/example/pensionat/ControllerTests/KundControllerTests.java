package com.example.pensionat.ControllerTests;

import com.example.pensionat.Controllers.KundController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class KundControllerTests {

    @Autowired
    private KundController kundController;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init(){
        assertNotNull(kundController);
        assertNotNull(mockMvc);
    }

    @Test
    void getAllKunderTest() throws Exception {
        this.mockMvc.perform(get("/kunder")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Alla kunder"))) ;
    }

}
