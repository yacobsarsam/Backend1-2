package com.example.pensionat.ServiceTests;

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
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
public class KundServiceTests {

    @Mock
    private KundRepo mockKundRepo;

    @InjectMocks
    private KundServiceImp mockKundServiceImp;

    @BeforeEach
    void init(){
        mockKundServiceImp  = new KundServiceImp(mockKundRepo);
        assertNotNull(mockKundRepo);
        assertNotNull(mockKundServiceImp);
    }

    @Test
    void getAllKunderTest(){
        //TODO
    }

    @Test
    void getAllKunder2Test(){
        //TODO
    }

    @Test
    public void addKundTest_pass(){
        Kund k1 = new Kund(1L, "Test", "1234567891", "test@test.com", null);
        Model model = mock(Model.class);

        when(mockKundRepo.save(any(Kund.class))).thenReturn(k1);
        assertEquals(mockKundServiceImp.addKund(k1, model), "updateKundDone");
        verify(mockKundRepo, times(1)).save(k1);
    }

    @Test
    public void addKundTest_fail(){
        Kund k1 = new Kund(1L, "Test", "12345", "test@test.com", null);
        Model model = mock(Model.class);

        when(mockKundRepo.save(any(Kund.class))).thenReturn(k1);
        assertEquals(mockKundServiceImp.addKund(k1, model), "updatekund");
        verify(mockKundRepo, times(0)).save(k1);
    }

    @Test
    void updateKundTest(){
        //TODO
    }

    @Test
    void checkIfKundExistByEmailTest_pass(){
        //TODO
    }

    @Test
    void checkIfKundExistByEmail_fail(){
        //TODO
    }

    @Test
    public void checkIfKundHasBokningarTest_pass(){
        List<Bokning> fylldBokningsLista = new ArrayList<>();
        Kund k1 = new Kund(1L, "Test", "1234567891", "test@test.com", fylldBokningsLista);
        Rum r1 = new Rum(1L, 10, false, 1, 100);
        Bokning b1 = new Bokning(1L, LocalDate.now(), LocalDate.now().plusDays(1), 1, k1, r1, 100);
        fylldBokningsLista.add(b1);
        when(mockKundRepo.findById(1L)).thenReturn(Optional.of(k1));

        assertTrue(mockKundServiceImp.checkIfKundHasBokningar(1L));
        verify(mockKundRepo, times(1)).findById(1L);
    }

    @Test
    public void checkIfKundHasBokningarTest_fail(){
        List<Bokning> tomBokningsLista = new ArrayList<>();
        Kund k1 = new Kund(1L, "Test", "1234567891", "test@test.com", tomBokningsLista);
        when(mockKundRepo.findById(1L)).thenReturn(Optional.of(k1));

        assertFalse(mockKundServiceImp.checkIfKundHasBokningar(1L));
        verify(mockKundRepo, times(1)).findById(1L);
    }

    @Test
    void deleteKundTest(){
        //TODO
    }

    @Test
    void getKundByIdTest(){
        //TODO
    }

    @Test
    void getAllAvailableKundInfoTest(){
        //TODO
    }

    //Nedan kanske inte behövs då den testas på andra ställen
    @Test
    void isCustomerFieldsFilledAndCorrect_pass(){
        //TODO
    }
    //Nedan kanske inte behövs då den testas på andra ställen
    @Test
    void isCustomerFieldsFilledAndCorrect_fail(){
        //TODO
    }

    //DTO tester
    @Test
    void kundDtoToKundTest(){
        //TODO
    }

    @Test
    void kundToKundDtoTest(){
        //TODO
    }

    @Test
    void kundToDetailedKundDtoTest(){
        //TODO
    }

    //TODO flyttas till bokningsServiceTest när metoden är flyttad i kodbasen
    @Test
    public void bokningToBokningDtoTest(){
        List<Bokning> fylldBokningsLista = new ArrayList<>();
        Kund k1 = new Kund(1L, "Test", "1234567891", "test@test.com", fylldBokningsLista);
        Rum r1 = new Rum(1L, 10, false, 1, 100);
        Bokning b1 = new Bokning(1L, LocalDate.now(), LocalDate.now().plusDays(1), 1, k1, r1, 100);
        BokningDto bDto1 = mockKundServiceImp.bokningToBokningDto(b1);

        assertEquals(b1.getId(), bDto1.getId());
        assertEquals(b1.getNumOfBeds(), bDto1.getNumOfBeds());
        assertEquals(b1.getStartdatum(), LocalDate.parse(bDto1.getStartdatum()));
        assertEquals(b1.getSlutdatum(), LocalDate.parse(bDto1.getSlutdatum()));
    }
}
