package com.example.pensionat.ServiceTests;

import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.BokningRepo;
import com.example.pensionat.Repositories.RumRepo;
import com.example.pensionat.Services.BokningService;
import com.example.pensionat.Services.Imp.BokningServiceImp;
import com.example.pensionat.Services.KundService;
import com.example.pensionat.Services.RumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BokningServiceTests {

    @Mock
    private KundService mockKundService;
    @Mock
    private RumService mockRumService;
    @Mock
    private RumRepo mockRumRepo;
    @Mock
    private BokningRepo mockBokningRepo;

    @Mock
    private BokningService mockBokningService;
    @InjectMocks
    private BokningServiceImp mockBokningsServiceImp;

    @BeforeEach
    void init(){
        mockBokningsServiceImp  = new BokningServiceImp(mockBokningRepo, mockRumRepo, mockKundService, mockRumService);
        assertNotNull(mockKundService);
        assertNotNull(mockRumService);
        assertNotNull(mockRumRepo);
        assertNotNull(mockBokningRepo);
        assertNotNull(mockBokningsServiceImp);
    }

    @Test
    void getNonAvailableRoomsIdTest(){
        Kund k1 = new Kund(1L, "Test", "1234567890", "test@test.se", null);
        Rum r1 = new Rum(1L, 10, false, 1, 100, null);
        Rum r2 = new Rum(2L, 11, false, 1, 100, null);
        Bokning b1 = new Bokning(1L, LocalDate.parse("2024-04-01"), LocalDate.parse("2024-04-05"), 1, k1, r1, 100);
        Bokning b2 = new Bokning(2L, LocalDate.parse("2024-05-01"), LocalDate.parse("2024-05-05"), 1, k1, r2, 100);

        List<Long> roomIds = mockBokningsServiceImp.getNonAvailableRoomsId(List.of(b1), LocalDate.parse("2024-04-01"), LocalDate.parse("2024-04-05"));
        assertEquals(1, roomIds.get(0));
        assertNotEquals(0, roomIds.get(0));
    }
@Test
    public void checkBookingsPerCustomerTest(){
        List<Bokning> fylldBokningsLista = new ArrayList<>();
        Kund k1 = new Kund(1L, "Test", "1234567890", "test@test.se", fylldBokningsLista);
        Rum r1 = new Rum(false, 1, 10, 1000);
        Bokning b1 = new Bokning(k1, r1, LocalDate.parse("2024-04-01"), LocalDate.parse("2024-04-05"), 1, 1000);
        Bokning b2 = new Bokning(k1, r1, LocalDate.parse("2024-04-08"), LocalDate.parse("2024-04-10"), 1, 1000);
        Bokning b3 = new Bokning(k1, r1, LocalDate.parse("2022-04-08"), LocalDate.parse("2022-04-10"), 1, 1000);
        fylldBokningsLista.add(b1);
        fylldBokningsLista.add(b2);
        fylldBokningsLista.add(b3);
        when(mockBokningRepo.findAll()).thenReturn(fylldBokningsLista);
        int dates = mockBokningsServiceImp.checkBookingsPerCustomer(1L);
        assertEquals(dates, 6);
    }

    @Test
    public void checkDiscountPriceTest(){
        List<Bokning> fylldBokningsLista = new ArrayList<>();
        List<Bokning> tomBokningsLista = new ArrayList<>();
        Kund k1 = new Kund(1L, "Test", "1234567890", "test@test.se", fylldBokningsLista);
        Kund k2 = new Kund(2L, "Test", "1234567890", "test@test.se", tomBokningsLista);
        Rum r1 = new Rum(false, 1, 10, 1000);
        Bokning b1 = new Bokning(k2, r1, LocalDate.parse("2024-05-19"), LocalDate.parse("2024-05-20"), 1);
        Bokning b2 = new Bokning(k1, r1, LocalDate.parse("2024-05-13"), LocalDate.parse("2024-05-18"), 1);
        Bokning b3 = new Bokning(k1, r1, LocalDate.parse("2024-05-01"), LocalDate.parse("2024-05-10"), 1);
        Bokning b4 = new Bokning(k2, r1, LocalDate.parse("2024-05-13"), LocalDate.parse("2024-05-14"), 1);
        fylldBokningsLista.add(b2);
        fylldBokningsLista.add(b3);
        when(mockBokningRepo.findAll()).thenReturn(fylldBokningsLista);
        b1.setTotalPrice(mockBokningsServiceImp.checkDiscountPrice(b1)); // söndag
        b2.setTotalPrice(mockBokningsServiceImp.checkDiscountPrice(b2)); // flera dagar + minst 10 nätter
        b3.setTotalPrice(mockBokningsServiceImp.checkDiscountPrice(b3)); // flera dagar + söndag + minst 10 nätter
        b4.setTotalPrice(mockBokningsServiceImp.checkDiscountPrice(b4)); // en vardag
        assertEquals(b1.getTotalPrice(), 980);
        assertEquals(b2.getTotalPrice(), 4876);
        assertEquals(b3.getTotalPrice(), 8756);
        assertEquals(b4.getTotalPrice(), 1000);

    }

    @Test
    void isCustomerFieldsFilledAndCorrectTest(){
        //TODO
    }

    @Test
    void addBokningTest(){
        //TODO
    }

    @Test
    void getBookingDetailsByIdTest(){
        //TODO
    }

    @Test
    void getAllBokningarTest(){
        //TODO
    }

    @Test
    void getAllBokningarByIdTest(){
        //TODO
    }

    @Test
    void getAllBokningarAsBokningByIdTest(){
        //TODO
    }

    @Test
    void updateBokningTest(){
        //TODO
    }

    @Test
    void deleteBokningTest(){
        //TODO
    }

    @Test
    void newBokningTest(){
        //TODO
    }

    //DTO tester
    @Test
    void bokningToBokningDtoTest(){
        //TODO
    }

    @Test
    void bokningToDetailedBokningDtoTest(){
        //TODO
    }

}
