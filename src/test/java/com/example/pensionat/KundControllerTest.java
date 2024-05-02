package com.example.pensionat;

import com.example.pensionat.Controllers.KundController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@WebMvcTest
class KundControllerTest {

    //skapa testKundKontroller
    @Autowired
    private KundController kundController;

    @Autowired
    private MockMvc mockMvc;

    //Testa att kundkontrollen finns och är inte Null
    @Test
    public void contextLoads() throws Exception{
        assertThat(kundController).isNotNull();
    }
    @Test
    void GetAllKunder() throws Exception {
        this.mockMvc.perform(get("/kunder")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Alla kunder"))) ;
    }
}