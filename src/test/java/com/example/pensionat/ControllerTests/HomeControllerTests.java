package com.example.pensionat.ControllerTests;

import com.example.pensionat.Controllers.HomeController;
import com.example.pensionat.Repositories.RumRepo;
import com.example.pensionat.Security.DataSeeder;
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

@WebMvcTest(HomeController.class)
public class HomeControllerTests {

    @MockBean
    private CommandLineRunner commandLineRunner;


    @Autowired
    private MockMvc mockMvc;

    @Test
    void init(){
        assertNotNull(mockMvc);
    }

    @Test
    @WithMockUser(username = "admin", roles = "{ADMIN}")
    void index() throws Exception {
        String expectedResponse ="index";

        mockMvc.perform(get("")).andExpect(status().isOk()).andExpect(view().name(expectedResponse));
    }
}
