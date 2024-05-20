package com.example.pensionat.ControllerTests;

import com.example.pensionat.Controllers.BlackListController;
import com.example.pensionat.Models.BlackListPerson;
import com.example.pensionat.Services.BlackListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class BlackListControllerTests {

    @Autowired
    private BlackListController mockBlackListController;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlackListService mockBlackListService;

    @BeforeEach
    void init(){
        assertNotNull(mockBlackListController);
        assertNotNull(mockBlackListService);
    }

    @Test
    void getAllBlKunderTest() throws Exception {
        String expectedResponse = "visablkunder";
        BlackListPerson blackListPerson = mock(BlackListPerson.class);
        when(mockBlackListService.getAllBLKunder()).thenReturn(List.of(blackListPerson));

        mockMvc.perform(get("/blacklist"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse));
    }

    @Test
    void createKundTest(){
        //TODO
    }

    @Test
    void editKundInfoTest(){
        //TODO
    }

    @Test
    void updateKundInfoTest(){
        //TODO
    }
}
