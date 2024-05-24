package com.example.pensionat.ControllerTests;

import com.example.pensionat.Controllers.BokningsViewController;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
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
    @WithMockUser(username = "receptionist", roles = {"RECEPTIONIST"})
    void addBokningSiteTest() throws Exception {
        String expectedResponse = "addBokning";
        when(mockBokningService.addBokning()).thenReturn(expectedResponse);
        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse));
    }

    @Test
    @WithMockUser(username = "receptionist", roles = {"RECEPTIONIST"})
    void showAllRooms_withBlackListedCustomerTest() throws Exception {
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
    @WithMockUser(username = "receptionist", roles = {"RECEPTIONIST"})
    void showAllRooms_withoutBlackListedCustomer_noPreviousBookingTest() throws Exception {
        String expectedResponse = "addBokning";
        String notBlackListedEmail = "test@email.com";
        Model model = mock(Model.class);
        when(mockBlackListService.checkIfBLKundExistByEmailUtanAttSkapa(notBlackListedEmail))
                .thenReturn(false);
        when(mockBokningService.getAllAvailableRooms(anyLong(),anyLong(),anyString(), anyString(),anyString(),
                anyString(),anyString(), anyInt(), any(Model.class)))
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

    @Test
    void showAllRooms_withoutBlackListedCustomer_withPreviousBookingTest() throws Exception {
        //TODO
    }

    @Test
    @WithMockUser(username = "receptionist", roles = {"RECEPTIONIST"})
    void addBokningSite2_withoutCustomerTest() throws Exception {
        String expectedResponse = "error";
        when(mockKundService.getKundById(anyLong())).thenReturn(null);;

        mockMvc.perform(get("/nyBokning")
                .param("id", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse));
    }
    @Test
    @WithMockUser(username = "receptionist", roles = {"RECEPTIONIST"})
    void addBokningSite2_withCustomerTest() throws Exception {
        String expectedResponse = "makeBookingWithCustomer";
        Kund kund = mock(Kund.class);
        Bokning bokning = mock(Bokning.class);
        when(mockKundService.getKundById(anyLong())).thenReturn(kund);
        when(kund.getBokning()).thenReturn(List.of(bokning));

        mockMvc.perform(get("/nyBokning")
                .param("id", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse));
    }

}
