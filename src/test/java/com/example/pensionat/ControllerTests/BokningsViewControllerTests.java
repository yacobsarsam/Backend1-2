package com.example.pensionat.ControllerTests;

import com.example.pensionat.Controllers.BokningsViewController;
import com.example.pensionat.Services.BlackListService;
import com.example.pensionat.Services.BokningService;
import com.example.pensionat.Services.KundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class BokningsViewControllerTests {

    @Autowired
    private BokningsViewController bokningsViewController;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BokningService mockBokningService;
    @MockBean
    private KundService mockKundService;
    @MockBean
    private BlackListService mockBlackListService;

    @BeforeEach
    void init(){
        assertNotNull(bokningsViewController);
        assertNotNull(mockMvc);
    }

    @Test
    void addBokningSiteTest() throws Exception {
        String expectedResponse = "addBokning";
        when(mockBokningService.addBokning()).thenReturn(expectedResponse);
        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse));
    }

    @Test
    void showAllRoomsTest_withBlackListedCustomer() throws Exception {
        String expectedResponse = "blockedUserTemplate";
        String blackListedEmail = "test@email.com";
        when(mockBlackListService.checkIfBLKundExistByEmailUtanAttSkapa(blackListedEmail))
                .thenReturn(true);
        mockMvc.perform(get("/book/viewRooms")
                .param("email", blackListedEmail)
                .param("namn", "name")
                .param("telNr", "1234567890")
                .param("startDate", "20220202")
                .param("endDate", "20220204"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse));
    }

    @Test
    void showAllRoomsTest_withoutBlackListedCustomer_noPreviousBooking() throws Exception {
        String expectedResponse = "addBokning";
        String notBlackListedEmail = "test@email.com";
        Model model = mock(Model.class);
        when(mockBlackListService.checkIfBLKundExistByEmailUtanAttSkapa(notBlackListedEmail))
                .thenReturn(false);
        when(mockBokningService.getAllAvailableRooms(1L,1L,"Test", "1234567890","Test@test.com",
                "20241212","20241214", 1, model))
                .thenReturn(expectedResponse);

        mockMvc.perform(get("/book/viewRooms")
                .param("email", notBlackListedEmail)
                .param("namn", "name")
                .param("telNr", "1234567890")
                .param("startDate", "20220202")
                .param("endDate", "20220204"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse));
    }




}
