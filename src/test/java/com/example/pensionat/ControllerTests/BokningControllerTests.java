package com.example.pensionat.ControllerTests;

import com.example.pensionat.Controllers.BokningController;
import com.example.pensionat.Controllers.KundController;
import com.example.pensionat.Dtos.DetailedBokningDto;
import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Dtos.RumDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Security.Services.CustomerMailService;
import com.example.pensionat.Security.Services.Imp.CustomerMailServiceImp;
import com.example.pensionat.Services.BokningService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BokningControllerTests {

    @Autowired
    private BokningController mockBokningController;
    @Autowired
    private KundController mockKundController;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BokningService mockBokningService;
    @MockBean
    private CustomerMailService mockCustomerMailService;

    @Test
    void init(){
        assertNotNull(mockBokningController);
        assertNotNull(mockBokningService);
    }

    @Test
    @WithMockUser(username = "receptionist", roles = {"RECEPTIONIST"})
    void updateInfoTest() throws Exception {
        String expectedResponse = "updateBooking";
        Kund kund = new Kund();
        kund.setNamn("Test");
        kund.setEmail("Test");
        kund.setTel("Test");
        kund.setId(1);
        Rum rum = new Rum();
        rum.setId(1);
        Bokning bokning = new Bokning();
        bokning.setRum(rum);
        bokning.setKund(kund);

        when(mockBokningService.getBookingDetailsById(anyLong())).thenReturn(bokning);

        mockMvc.perform(get("/bokningar/updateBokning/{id}", 1)
                .flashAttr("booking", bokning))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse))
                .andExpect(model().attributeExists("booking"))
                .andExpect(model().attribute("booking", bokning));
    }

    @Test
    @WithMockUser(username = "receptionist", roles = {"RECEPTIONIST"})
    void makeBookingUpdateTest() throws Exception {
        String expectedResponse = "bookingDetails";
        Kund kund = new Kund();
        kund.setNamn("Test");
        kund.setEmail("Test");
        kund.setTel("Test");
        kund.setId(1);
        Rum rum = new Rum();
        rum.setId(1);
        Bokning bokning = mock(Bokning.class);
        bokning.setRum(rum);
        bokning.setKund(kund);
        bokning.setTotalPrice(1);

        when(bokning.getRum()).thenReturn(rum);
        when(bokning.getKund()).thenReturn(kund);
        when(bokning.getTotalPrice()).thenReturn(1);
        when(mockBokningService.updateBokning(anyLong(), any(LocalDate.class), any(LocalDate.class), anyInt(), anyLong())).thenReturn(bokning);

        mockMvc.perform(post("/bokningar/update")
                .param("bokId", "1")
                .param("rumId", "1")
                .param("startDate", "2023-01-01")
                .param("endDate", "2023-01-10")
                .param("extraBeds", "1")
                .param("antalPersoner", "2")
                .flashAttr("booking", bokning)
                .flashAttr("rumInfo", rum)
                .flashAttr("kundInfo", kund)
                .flashAttr("bookingPris", 1))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse));
    }

    @Test
    @WithMockUser(username = "receptionist", roles = {"RECEPTIONIST"})
    void getAllBokningarTest() throws Exception {
        String expectedResponse = "visaBokningar";
        KundDto kund = new KundDto();
        kund.setNamn("Test");
        kund.setEmail("Test");
        kund.setTel("Test");
        kund.setId(1);
        DetailedBokningDto bokning = new DetailedBokningDto();
        bokning.setId(1);
        bokning.setKund(kund);
        RumDto rum = new RumDto(1, 10);
        bokning.setRum(rum);

        when(mockBokningService.getAllBokningar()).thenReturn(List.of(bokning));

        mockMvc.perform(get("/bokningar")
                .flashAttr("allBookings", List.of(bokning)))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse))
                .andExpect(model().attributeExists("allBookings"))
                .andExpect(model().attribute("allBookings", List.of(bokning)));
    }

    @Test
    @WithMockUser(username = "receptionist", roles = {"RECEPTIONIST"})
    void addBokningTest() throws Exception {
        String expectedResponse = "bookingDetails";
        Bokning bokning = mock(Bokning.class);
        Kund kund = new Kund();
        kund.setNamn("Test");
        kund.setEmail("Test");
        kund.setTel("Test");
        kund.setId(1L);
        Rum rum = new Rum(false, 1, 10, 1);

        when(bokning.getRum()).thenReturn(rum);
        when(bokning.getKund()).thenReturn(kund);
        when(bokning.getTotalPrice()).thenReturn(1);
        when(mockBokningService.newBokning(anyString(), anyString(), anyString(), any(LocalDate.class), any(LocalDate.class), anyLong(), anyInt()))
                .thenReturn(bokning);
        doNothing().when(mockCustomerMailService).sendConfirmationMail(any(Bokning.class));

        mockMvc.perform(post("/bokningar/add")
                .param("namn", "Test")
                .param("telNr", "Test")
                .param("email", "Test@email.com")
                .param("startDate", "2023-01-01")
                .param("endDate", "2023-01-10")
                .param("rumId", "1")
                .param("extraBeds", "1")
                .param("antalPersoner", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse))
                .andExpect(model().attributeExists("booking"))
                .andExpect(model().attributeExists("rumInfo"))
                .andExpect(model().attributeExists("kundInfo"))
                .andExpect(model().attributeExists("bookingPris"))
                .andExpect(model().attribute("booking", bokning))
                .andExpect(model().attribute("rumInfo", rum))
                .andExpect(model().attribute("kundInfo", kund))
                .andExpect(model().attribute("bookingPris", 1));
    }



    @Test
    @WithMockUser(username = "receptionist", roles = {"RECEPTIONIST"})
    void deleteBokningTest() throws Exception {
        String expectedResponse = "visabokningperkund";

        mockMvc.perform(post("/bokningar/delete/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse));
    }
}
