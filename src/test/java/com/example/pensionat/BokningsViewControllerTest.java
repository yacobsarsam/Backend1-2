package com.example.pensionat;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BokningsViewControllerTest {

    @Mock
    private KundService mockKundService;

    @Mock
    private RumService mockRumService;

    @Mock
    private RumRepo mockRumRepo;

    @Mock
    private BokningRepo mockBokningRepo;

    @InjectMocks
    private BokningServiceImp mockBokningsServiceImp = new BokningServiceImp(mockBokningRepo, mockRumRepo, mockKundService, mockRumService);

    @BeforeEach
    public void init(){
    }

    @Test
    public void getNonAvailableRoomsIdTest(){
        Kund k1 = new Kund(1L, "Test", "1234567890", "test@test.se", null);
        Rum r1 = new Rum(1L, 10, false, 1);
        Rum r2 = new Rum(2L, 11, false, 1);
        Bokning b1 = new Bokning(1L, LocalDate.parse("2024-04-01"), LocalDate.parse("2024-04-05"), 1, k1, r1);
        Bokning b2 = new Bokning(2L, LocalDate.parse("2024-05-01"), LocalDate.parse("2024-05-05"), 1, k1, r2);

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
        int dates = mockBokningsServiceImp.checkBookingsPerCustomer(k1);
        assertEquals(dates, 6);
    }

    @Test
    public void checkDiscountPriceTest(){
        List<Bokning> fylldBokningsLista = new ArrayList<>();
        List<Bokning> tomBokningsLista = new ArrayList<>();
        Kund k1 = new Kund(1L, "Test", "1234567890", "test@test.se", fylldBokningsLista);
        Kund k2 = new Kund(1L, "Test", "1234567890", "test@test.se", tomBokningsLista);
        Rum r1 = new Rum(false, 1, 10, 1000);
        Bokning b1 = new Bokning(k2, r1, LocalDate.parse("2024-05-19"), LocalDate.parse("2024-05-20"), 1);
        Bokning b2 = new Bokning(k1, r1, LocalDate.parse("2024-05-13"), LocalDate.parse("2024-05-18"), 1);
        Bokning b3 = new Bokning(k1, r1, LocalDate.parse("2024-05-01"), LocalDate.parse("2024-05-10"), 1);
        Bokning b4 = new Bokning(k2, r1, LocalDate.parse("2024-05-13"), LocalDate.parse("2024-05-14"), 1);
        fylldBokningsLista.add(b2);
        fylldBokningsLista.add(b3);
        b1 = mockBokningsServiceImp.checkDiscountPrice(b1); // söndag
        b2 = mockBokningsServiceImp.checkDiscountPrice(b2); // flera dagar + minst 10 nätter
        b3 = mockBokningsServiceImp.checkDiscountPrice(b3); // flera dagar + söndag + minst 10 nätter
        b4 = mockBokningsServiceImp.checkDiscountPrice(b4); // en vardag
        assertEquals(b1.getTotalPrice(), 980);
        assertEquals(b2.getTotalPrice(), 4876);
        assertEquals(b3.getTotalPrice(), 8756);
        assertEquals(b4.getTotalPrice(), 1000);
    }

}
