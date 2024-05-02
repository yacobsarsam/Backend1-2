package com.example.pensionat;

import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.BokningRepo;
import com.example.pensionat.Repositories.KundRepo;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BokningsViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BokningService mockBokningsService;

    @Mock
    private KundService mockKundService;

    @Mock
    private RumService mockRumService;

    @Mock
    private KundRepo mockKundrepo;

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

}
