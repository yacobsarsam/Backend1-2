package com.example.pensionat.ServiceTests;

import com.example.pensionat.Dtos.CustomerDto;
import com.example.pensionat.Models.customers;
import com.example.pensionat.Repositories.CustomerRepo;
import com.example.pensionat.Services.Imp.CompanyCustomerImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
        customers c1 = new customers(1L, "Företaget ab", "Ernst Ek", "VD", "Gatan 1", "Smögen", 12345, "Sverige", "1234567890", "111222333");
        when(mockCustomerRepo.findById(anyLong())).thenReturn(Optional.of(c1));
        customers fetchedCustomer = mockCompanyCustomerImp.getCustomerDetailsById(1L);
        assertEquals(c1.getCompanyName(), fetchedCustomer.getCompanyName());
        assertEquals(c1.getCity(), fetchedCustomer.getCity());
        assertEquals(c1.getContactName(), fetchedCustomer.getContactName());
        assertEquals(c1.getPhone(), fetchedCustomer.getPhone());
    }

    @Test
    void addCustomerToDB_passTest(){
        customers c1 = new customers(1L, "Företaget ab", "Ernst Ek", "VD", "Gatan 1", "Smögen", 12345, "Sverige", "1234567890", "111222333");
        customers c2 = new customers(2L, "Företaget2 ab", "Egon Ek", "VD", "Gatan 2", "Smögen", 12345, "Sverige", "9876543210", "111222333");
        when(mockCustomerRepo.findAll()).thenReturn(List.of(c1));

        // spara ny kund, save ska anropas en gång
        mockCompanyCustomerImp.addCustomerToDB(c2);
        verify(mockCustomerRepo, times(1)).save(c2);
    }

    @Test
    void addCustomerToDB_failTest(){
        customers c1 = new customers(1L, "Företaget ab", "Ernst Ek", "VD", "Gatan 1", "Smögen", 12345, "Sverige", "1234567890", "111222333");
        when(mockCustomerRepo.findAll()).thenReturn(List.of(c1));

        // spara existerande kund, save ska inte anropas
        mockCompanyCustomerImp.addCustomerToDB(c1);
        verify(mockCustomerRepo, never()).save(c1);
    }

    @Test
    void searchCompanyClientsTest(){
        customers c1 = new customers(1L, "Företaget ab", "Ernst Ek", "VD", "Gatan 1", "Smögen", 12345, "Sverige", "1234567890", "111222333");
        customers c2 = new customers(2L, "Företaget Bilar ab", "Egon Ek", "VD", "Gatan 2", "Smögen", 12345, "Sverige", "9876543210", "111222333");
        when(mockCustomerRepo.findAll(any(Sort.class))).thenReturn(Arrays.asList(c1, c2));

        // skapa ett sort objekt som sorterar på företagsnamn
        Sort sort = Sort.by(Sort.Direction.ASC, "companyName");

        List<customers> result = mockCompanyCustomerImp.searchCompanyClients("Företaget", sort);
        assertEquals(2, result.size());
        assertTrue(result.contains(c1));
        assertTrue(result.contains(c2));


        result = mockCompanyCustomerImp.searchCompanyClients("Egon", sort);
        assertEquals(1, result.size());
        assertEquals("Egon Ek", result.get(0).getContactName());
    }

    //DTO tester
    @Test
    void customerToCustomerDtoTest(){
        customers c1 = new customers(1L, "Företaget ab", "Ernst Ek", "VD", "Gatan 1", "Smögen", 12345, "Sverige", "1234567890", "111222333");
        CustomerDto cdto = mockCompanyCustomerImp.customersToCustomerDto(c1);
        assertEquals(c1.getCompanyName(), cdto.getCompanyName());
        assertEquals(c1.getContactName(), cdto.getContactName());
        assertEquals(c1.getFax(), cdto.getFax());
        assertEquals(c1.getCity(), cdto.getCity());
    }
}
