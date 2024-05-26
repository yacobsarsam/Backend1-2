package com.example.pensionat.ServiceTests;

import com.example.pensionat.Models.Shippers;
import com.example.pensionat.Repositories.ShippersRepo;
import com.example.pensionat.Services.Imp.ShippersServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ShippersServiceTests {

    @Mock
    private ShippersRepo mockShippersRepo;

    @InjectMocks
    private ShippersServiceImp mockShippersServiceImp;

    @BeforeEach
    void init(){
        mockShippersServiceImp = new ShippersServiceImp(mockShippersRepo);
        assertNotNull(mockShippersRepo);
        assertNotNull(mockShippersServiceImp);
    }

    @Test
    void addShippersToDBTest(){
        Shippers s1 = new Shippers("Företag", "1234567890");
        Shippers s2 = new Shippers("Företag2", "1234567890");
        when(mockShippersRepo.findAll()).thenReturn(List.of(s1));
        mockShippersServiceImp.addShippersToDB(s2);
        verify(mockShippersRepo, times(1)).save(s2);
        verify(mockShippersRepo, times(0)).save(s1);
    }
}
