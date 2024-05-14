package com.example.pensionat.ServiceTests;

import com.example.pensionat.Repositories.CustomerRepo;
import com.example.pensionat.Services.Imp.CompanyCustomerImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CompanyCustomerServiceTests {

    @Mock
    private CustomerRepo mockCustomerRepo;

    @InjectMocks
    private CompanyCustomerImp mockCompanyCustomerImp;

    @BeforeEach
    void init(){
        mockCompanyCustomerImp  = new CompanyCustomerImp(mockCustomerRepo);
        assertNotNull(mockCustomerRepo);
        assertNotNull(mockCompanyCustomerImp);
    }

    @Test
    void getCustomerByIdTest(){
        //TODO
    }

    @Test
    void addCustomerToDBTest(){
        //TODO
    }

    @Test
    void searchCompanyClientsTest(){
        //TODO
    }

    //DTO tester
    @Test
    void customerToCustomerDtoTest(){
        //TODO
    }
}
