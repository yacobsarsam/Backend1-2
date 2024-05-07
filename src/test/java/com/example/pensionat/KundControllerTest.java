package com.example.pensionat;

import com.example.pensionat.Controllers.KundController;
import com.example.pensionat.Dtos.BokningDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.KundRepo;
import com.example.pensionat.Services.Imp.KundServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@WebMvcTest
class KundControllerTest {

    //skapa testKundKontroller
    @Autowired
    private KundController kundController;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private KundRepo kundRepo;

    @InjectMocks
    private KundServiceImp kundServiceImp;

    Model model = new ExtendedModelMap();

    @BeforeEach
    public void init(){
        kundServiceImp = new KundServiceImp(kundRepo);
    }
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

    @Test
    public void bokningToBokningDtoTest(){
        List<Bokning> fylldBokningsLista = new ArrayList<>();
        Kund k1 = new Kund(1L, "Test", "1234567891", "test@test.com", fylldBokningsLista);
        Rum r1 = new Rum(1L, 10, false, 1);
        Bokning b1 = new Bokning(1L, LocalDate.now(), LocalDate.now().plusDays(1), 1, k1, r1);
        BokningDto bDto1 = kundServiceImp.bokningToBokningDto(b1);

        assertEquals(b1.getId(), bDto1.getId());
        assertEquals(b1.getNumOfBeds(), bDto1.getNumOfBeds());
        assertEquals(b1.getStartdatum(), LocalDate.parse(bDto1.getStartdatum()));
        assertEquals(b1.getSlutdatum(), LocalDate.parse(bDto1.getSlutdatum()));
    }

    @Test
    public void checkIfKundHasBokningarTest_fail(){
        List<Bokning> tomBokningsLista = new ArrayList<>();
        Kund k1 = new Kund(1L, "Test", "1234567891", "test@test.com", tomBokningsLista);
        when(kundRepo.findById(1L)).thenReturn(Optional.of(k1));

        assertFalse(kundServiceImp.checkIfKundHasBokningar(1L));
        verify(kundRepo, times(1)).findById(1L);
    }

    @Test
    public void checkIfKundHasBokningarTest_pass(){
        List<Bokning> fylldBokningsLista = new ArrayList<>();
        Kund k1 = new Kund(1L, "Test", "1234567891", "test@test.com", fylldBokningsLista);
        Rum r1 = new Rum(1L, 10, false, 1);
        Bokning b1 = new Bokning(1L, LocalDate.now(), LocalDate.now().plusDays(1), 1, k1, r1);
        fylldBokningsLista.add(b1);
        when(kundRepo.findById(1L)).thenReturn(Optional.of(k1));

        assertTrue(kundServiceImp.checkIfKundHasBokningar(1L));
        verify(kundRepo, times(1)).findById(1L);
    }

    @Test
    public void addKundTest_pass(){
        Kund k1 = new Kund(1L, "Test", "1234567891", "test@test.com", null);

        when(kundRepo.save(any(Kund.class))).thenReturn(k1);
        assertEquals(kundServiceImp.addKund(k1, model), "updateKundDone");
        verify(kundRepo, times(1)).save(k1);
    }

    @Test
    public void addKundTest_fail(){
        //ej godkänd kund
        Kund k1 = new Kund(1L, "Test", "12345", "test@test.com", null);

        when(kundRepo.save(any(Kund.class))).thenReturn(k1);
        assertEquals(kundServiceImp.addKund(k1, model), "updatekund");
        verify(kundRepo, times(0)).save(k1);
    }
}