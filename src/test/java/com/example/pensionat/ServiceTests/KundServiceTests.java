package com.example.pensionat.ServiceTests;

import com.example.pensionat.Dtos.BokningDto;
import com.example.pensionat.Dtos.KundDto;
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
import java.util.*;

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
        Kund k1 = new Kund(1L, "Test1", "1234567890", "test@test.se", null);
        Kund k2 = new Kund(2L, "Test2", "1234567890", "test@test.se", null);
        when(mockKundRepo.findAll()).thenReturn(List.of(k1, k2));

        List<KundDto> kundList = mockKundServiceImp.getAllKunder();
        assertEquals(kundList.size(), 2);
        assertEquals(kundList.get(1).getNamn(), k2.getNamn());
    }

    @Test
    void getAllKunder2Test(){
        Kund k1 = new Kund(1L, "Test1", "1234567890", "test@test.se", null);
        Kund k2 = new Kund(2L, "Test2", "1234567890", "test@test.se", null);
        when(mockKundRepo.findAll()).thenReturn(List.of(k1, k2));

        List<KundDto> kundList = mockKundServiceImp.getAllKunder();
        assertEquals(kundList.size(), 2);
        assertEquals(kundList.get(1).getNamn(), k2.getNamn());
    }

    @Test
    public void addKund_passTest(){
        Kund k1 = new Kund(1L, "Test", "1234567891", "test@test.com", null);
        Model model = mock(Model.class);

        when(mockKundRepo.save(any(Kund.class))).thenReturn(k1);
        assertEquals(mockKundServiceImp.addKund(k1, model), "updateKundDone");
        verify(mockKundRepo, times(1)).save(k1);
    }

    @Test
    public void addKund_failTest(){
        Kund k1 = new Kund(1L, "Test", "12345", "test@test.com", null);
        Model model = mock(Model.class);

        when(mockKundRepo.save(any(Kund.class))).thenReturn(k1);
        assertEquals(mockKundServiceImp.addKund(k1, model), "updatekund");
        verify(mockKundRepo, times(0)).save(k1);
    }

    @Test
    void updateKundTest(){
        Kund k1 = new Kund(1L, "Test", "12345", "test@test.com", null);
        when(mockKundRepo.findById(anyLong())).thenReturn(Optional.of(k1));
        assertEquals(mockKundServiceImp.updateKund(1L), k1);
    }

    @Test
    void checkIfKundExistByEmail_existsTest(){
        KundDto k1 = new KundDto(1L, "Test", "12345", "test@test.com");
        Kund k2 = new Kund(1L, "Test", "12345", "test@test.com", null);
        when(mockKundServiceImp.getAllKunder()).thenReturn(List.of(k1));
        when(mockKundRepo.findAll()).thenReturn(List.of(k2));
        KundDto hittadKund = mockKundServiceImp.checkIfKundExistByEmail("Test", "test@test.com", "12345");
        assertEquals(hittadKund.getNamn(), k1.getNamn());
        assertEquals(hittadKund.getEmail(), k1.getEmail());
    }

    @Test
    void checkIfKundExistByEmail_notExistingTest(){
        when(mockKundServiceImp.getAllKunder()).thenReturn(Collections.EMPTY_LIST);
        when(mockKundRepo.findAll()).thenReturn(Collections.EMPTY_LIST);
        KundDto sparadKund = mockKundServiceImp.checkIfKundExistByEmail("Test", "test@test.com", "12345");
        assertEquals(sparadKund.getNamn(), "Test");
        assertEquals(sparadKund.getEmail(), "test@test.com");
    }

    @Test
    public void checkIfKundHasBokningar_passTest(){
        List<Bokning> fylldBokningsLista = new ArrayList<>();
        Kund k1 = new Kund(1L, "Test", "1234567891", "test@test.com", fylldBokningsLista);
        Rum r1 = new Rum(1L, 10, false, 1, 100, null);
        Bokning b1 = new Bokning(1L, LocalDate.now(), LocalDate.now().plusDays(1), 1, k1, r1, 100);
        fylldBokningsLista.add(b1);
        when(mockKundRepo.findById(1L)).thenReturn(Optional.of(k1));

        assertTrue(mockKundServiceImp.checkIfKundHasBokningar(1L));
        verify(mockKundRepo, times(1)).findById(1L);
    }

    @Test
    public void checkIfKundHasBokningar_failTest(){
        List<Bokning> tomBokningsLista = new ArrayList<>();
        Kund k1 = new Kund(1L, "Test", "1234567891", "test@test.com", tomBokningsLista);
        when(mockKundRepo.findById(1L)).thenReturn(Optional.of(k1));

        assertFalse(mockKundServiceImp.checkIfKundHasBokningar(1L));
        verify(mockKundRepo, times(1)).findById(1L);
    }

    @Test
    void deleteKundTest(){
        List<Bokning> tomBokningsLista = new ArrayList<>();
        Kund k1 = new Kund(1L, "Test", "1234567891", "test@test.com", tomBokningsLista);
        when(mockKundRepo.findById(1L)).thenReturn(Optional.of(k1));
        mockKundServiceImp.deleteKund(1L);
        verify(mockKundRepo, times(1)).deleteById(1L);
    }

    @Test
    void getKundByIdTest(){
        Kund k1 = new Kund(1L, "Test", "1234567891", "test@test.com", null);
        when(mockKundRepo.findById(1L)).thenReturn(Optional.of(k1));
        assertEquals(mockKundServiceImp.getKundById(1L).getNamn(), "Test");
    }

    @Test
    void getAllAvailableKundInfoTest(){
        //TODO
    }

    //Nedan kanske inte behövs då den testas på andra ställen
    @Test
    void isCustomerFieldsFilledAndCorrect_passTest(){
        //TODO
    }
    //Nedan kanske inte behövs då den testas på andra ställen
    @Test
    void isCustomerFieldsFilledAndCorrect_failTest(){
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
        Rum r1 = new Rum(1L, 10, false, 1, 100, null);
        Bokning b1 = new Bokning(1L, LocalDate.now(), LocalDate.now().plusDays(1), 1, k1, r1, 100);
        BokningDto bDto1 = mockKundServiceImp.bokningToBokningDto(b1);

        assertEquals(b1.getId(), bDto1.getId());
        assertEquals(b1.getNumOfBeds(), bDto1.getNumOfBeds());
        assertEquals(b1.getStartdatum(), LocalDate.parse(bDto1.getStartdatum()));
        assertEquals(b1.getSlutdatum(), LocalDate.parse(bDto1.getSlutdatum()));
    }
}
