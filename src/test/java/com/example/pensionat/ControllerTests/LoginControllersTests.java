package com.example.pensionat.ControllerTests;


import com.example.pensionat.Security.Admin.UserService;
import com.example.pensionat.Security.Controllers.LoginControllers;
import com.example.pensionat.Security.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginControllers.class)
@AutoConfigureMockMvc
public class LoginControllersTests {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private CommandLineRunner commandLineRunner;
    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void init(){
        assertNotNull(userRepository);
        assertNotNull(userService);
        assertNotNull(mockMvc);
    }


    @Test
    @WithMockUser(username = "receptionist", roles = {"RECEPTIONIST"})
    void showForgotPasswordPageTest() throws Exception {
        this.mockMvc.perform(get("/forgot-password")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Glömt lösenord"))) ;
    }
}
