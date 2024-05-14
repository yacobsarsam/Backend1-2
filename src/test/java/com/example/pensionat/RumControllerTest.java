package com.example.pensionat;

import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.BokningRepo;
import com.example.pensionat.Repositories.RumRepo;
import com.example.pensionat.Services.Imp.BokningServiceImp;
import com.example.pensionat.Services.Imp.RumServiceImp;
import com.example.pensionat.Services.KundService;
import com.example.pensionat.Services.RumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
@AutoConfigureMockMvc
public class RumControllerTest {

    @Mock
    private RumRepo mockRumRepo;

    @Mock
    private BokningRepo mockBokningRepo;

    @Mock
    private KundService mockKundService;

    @Mock
    private RumService mockRumService;

    @InjectMocks
    private RumServiceImp mockRumServiceImpl;

    @InjectMocks
    private BokningServiceImp mockBokningServiceImp;

    @BeforeEach
    public void init(){
        mockRumServiceImpl = new RumServiceImp(mockRumRepo);
        mockBokningServiceImp = new BokningServiceImp(mockBokningRepo, mockRumRepo, mockKundService, mockRumService);
        Rum r1 = new Rum(1L, 10, false, 1);
        Rum r2 = new Rum(2L, 21, true, 2);
        Rum r3 = new Rum(3L, 31, true, 3);
        mockRumRepo.save(r1);
        mockRumRepo.save(r2);
        mockRumRepo.save(r3);

        when(mockRumService.getAllRum2()).thenReturn(Arrays.asList(r1, r2, r3), Arrays.asList(r2, r3));
        when(mockRumRepo.findById(1L)).thenReturn(Optional.of(new Rum(1L, 10, false, 1)));
        doNothing().when(mockRumRepo).deleteById(1L);
    }

    @Test
    public void isCustomerFieldsFilledAndCorrectTest(){
        //komplett
        assertTrue(mockBokningServiceImp.isCustomerFieldsFilledAndCorrect("test", "070-1234567", "test@test.test"));

        //saknar namn
        assertFalse(mockBokningServiceImp.isCustomerFieldsFilledAndCorrect(" ", "070-1234567", "test@test.test"));
        //för kort telefonnummer
        assertFalse(mockBokningServiceImp.isCustomerFieldsFilledAndCorrect("test", "070-1234", "test@test.test"));
        //saknar email, format sköts av input i HTML
        assertFalse(mockBokningServiceImp.isCustomerFieldsFilledAndCorrect("test", "070-1234567", "  "));
    }

    @Test
    public void getAllRum2Test(){
        List<Rum> rum = mockRumService.getAllRum2();
        assertEquals(3, rum.size());
    }

    @Test
    public void getAllLedigaRumByPeriod(){
        //TODO
    }

    @Test
    public void addRumTest(){
        //TODO
    }

    @Test
    public void deleteRumTest(){
        //kontroll att alla 3 rum ligger i listan
        List<Rum> rum = mockRumService.getAllRum2();
        assertEquals(3, rum.size());
        //ta bort ett rum
        mockRumServiceImpl.deleteRum(1L);
        //kontrollerar att metoderna i RumRepo körs
        verify(mockRumRepo, times(1)).findById(1L);
        verify(mockRumRepo, times(1)).deleteById(1L);
        //kontrollera att vi nu har 2 rum i listan
        List<Rum> rumAfterDelete = mockRumService.getAllRum2();
        assertEquals(2, rumAfterDelete.size());
        assertFalse(rumAfterDelete.stream().map(Rum::getId).anyMatch(id -> id.equals(1L)));
    }
}