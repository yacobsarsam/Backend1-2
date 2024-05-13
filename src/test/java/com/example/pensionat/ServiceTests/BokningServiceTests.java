package com.example.pensionat.ServiceTests;

import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.BokningRepo;
import com.example.pensionat.Repositories.RumRepo;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        Rum r1 = new Rum(1L, 10, false, 1, 100);
        Rum r2 = new Rum(2L, 11, false, 1, 100);
        Bokning b1 = new Bokning(1L, LocalDate.parse("2024-04-01"), LocalDate.parse("2024-04-05"), 1, k1, r1, 100);
        Bokning b2 = new Bokning(2L, LocalDate.parse("2024-05-01"), LocalDate.parse("2024-05-05"), 1, k1, r2, 100);

        List<Long> roomIds = mockBokningsServiceImp.getNonAvailableRoomsId(List.of(b1), LocalDate.parse("2024-04-01"), LocalDate.parse("2024-04-05"));
        assertEquals(1, roomIds.get(0));
        assertNotEquals(0, roomIds.get(0));
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
