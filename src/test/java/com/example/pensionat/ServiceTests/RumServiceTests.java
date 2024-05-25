package com.example.pensionat.ServiceTests;

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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RumServiceTests {

    @Mock
    private RumRepo mockRumRepo;
    @Mock
    private BokningRepo mockBokningRepo;
    @Mock
    private KundService mockKundService;
    @Mock
    private RumService mockRumService;

    @InjectMocks
    private RumServiceImp mockRumServiceImp;
    @InjectMocks
    private BokningServiceImp mockBokningServiceImp;

    @BeforeEach
    void init(){
        mockRumServiceImp = new RumServiceImp(mockRumRepo);
        mockBokningServiceImp  = new BokningServiceImp(mockBokningRepo, mockRumRepo, mockKundService, mockRumService);
        assertNotNull(mockRumRepo);
        assertNotNull(mockBokningRepo);
        assertNotNull(mockKundService);
        assertNotNull(mockRumService);
        assertNotNull(mockRumServiceImp);
        assertNotNull(mockBokningServiceImp);
    }

    @Test
    void getAllrum2Test(){
        Rum r1 = new Rum(1L, 10, false, 1, 100, null);
        Rum r2 = new Rum(2L, 21, true, 2, 100, null);
        Rum r3 = new Rum(3L, 31, true, 3, 100, null);
        when(mockRumServiceImp.getAllRum2()).thenReturn(Arrays.asList(r1, r2, r3));
        List<Rum> result = mockRumServiceImp.getAllRum2();
        assertEquals(3, result.size());
        assertNotEquals(2, result.size());
        assertNotNull(result);
    }

    @Test
    public void deleteRumTest(){
        Rum r1 = new Rum(1L, 10, false, 1, 100, null);
        Rum r2 = new Rum(2L, 21, true, 2, 100, null);
        Rum r3 = new Rum(3L, 31, true, 3, 100, null);
        when(mockRumServiceImp.getAllRum2()).thenReturn(Arrays.asList(r1, r2, r3));
        when(mockRumRepo.findById(1L)).thenReturn(Optional.of(new Rum(1L, 10, false, 1, 100, null)));
        doNothing().when(mockRumRepo).deleteById(1L);

        List<Rum> result = mockRumServiceImp.getAllRum2();
        assertEquals(3, result.size());

        mockRumServiceImp.deleteRum(1L);
        verify(mockRumRepo, times(1)).findById(1L);
        verify(mockRumRepo, times(1)).deleteById(1L);
    }

    @Test
    void getAllRumTest(){
        Rum r1 = new Rum(1L, 10, false, 1, 100, null);
        Rum r2 = new Rum(2L, 21, true, 2, 100, null);
        when(mockRumRepo.findAll()).thenReturn(Arrays.asList(r1, r2));
        assertTrue(mockRumServiceImp.getAllRum2().size() == 2);
        assertTrue(mockRumServiceImp.getAllRum2().get(1).getRumsnr() == 21);
    }

    @Test
    void addRumTest(){
        Rum r1 = new Rum(1L, 10, false, 1, 100, null);
        mockRumServiceImp.addRum(r1);
        verify(mockRumRepo, times(1)).save(r1);
    }

    @Test
    void getRumByIdTest(){
        when(mockRumRepo.findById(anyLong())).thenReturn(Optional.of(new Rum(1L, 10, false, 1, 100, null)));
        Rum r1 = mockRumServiceImp.getRumById(1L);
        assertTrue(r1.getRumsnr() == 10);
        assertTrue(r1.getStorlek() == 1);
    }

    //DTO tester
    @Test
    void rumToDetailedRumDtoTest(){
        //TODO
    }
}