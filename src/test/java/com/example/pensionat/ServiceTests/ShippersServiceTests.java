package com.example.pensionat.ServiceTests;

import com.example.pensionat.Repositories.ShippersRepo;
import com.example.pensionat.Services.Imp.ShippersServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        //TODO
    }
}
