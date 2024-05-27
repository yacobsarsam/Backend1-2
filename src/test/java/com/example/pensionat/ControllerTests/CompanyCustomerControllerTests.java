package com.example.pensionat.ControllerTests;

import com.example.pensionat.Controllers.CompanyCustomerController;
import com.example.pensionat.Models.customers;
import com.example.pensionat.Repositories.CustomerRepo;
import com.example.pensionat.Services.CompanyCustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CompanyCustomerController.class)
public class CompanyCustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyCustomerService mockCompanyCustomerService;
    @MockBean
    private CustomerRepo mockCustomerRepo;
    @MockBean
    private CommandLineRunner commandLineRunner;

    @Test
    void init(){
        assertNotNull(mockCompanyCustomerService);
        assertNotNull(mockCustomerRepo);

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void showIndividualCustomer_withCustomer() throws Exception {
        String expectedResponse = "visaCompanyCustomerIndividual";
        customers customer = mock(customers.class);
        when(mockCompanyCustomerService.getCustomerDetailsById(anyLong())).thenReturn(customer);

        mockMvc.perform(get("/company/customers/{id}", 1)
                .flashAttr("customer", customer))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse))
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("customer", customer));

    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void showIndividualCustomer_withoutCustomer() throws Exception {
        String expectedResponse = "error";
        customers customer = mock(customers.class);
        when(mockCompanyCustomerService.getCustomerDetailsById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/company/customers/{id}", 1)
                .flashAttr("customer", customer))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void showListOfCustomers() throws Exception {
        String expectedResponse = "visaAvtalsKunder";
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        customers customer = mock(customers.class);

        List<customers> allCustomers = new ArrayList<>(List.of(customer)); // or add some mock customers to the list
        Page<customers> clientsPage = new PageImpl<>(allCustomers, pageable, allCustomers.size());

        when(mockCustomerRepo.findAll(sort)).thenReturn(allCustomers);
        when(mockCustomerRepo.findAll(pageable)).thenReturn(clientsPage);

        mockMvc.perform(get("/company")
                .param("sortField", "id")
                .param("sortOrder", "asc")
                .param("page", "1")
                .param("size", "10")
                .param("searchWord", ""))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse))
                .andExpect(model().attribute("clientsPage", clientsPage))
                .andExpect(model().attribute("allCustomers", allCustomers))
                .andExpect(model().attribute("sortField", "id"))
                .andExpect(model().attribute("sortOrder", "asc"))
                .andExpect(model().attribute("searchWord", ""));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void search() throws Exception {
        String expectedResponse = "visaSoktaKunder";
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        customers customer = mock(customers.class);

        List<customers> allCustomers = new ArrayList<>(List.of(customer)); // or add some mock customers to the list
        Page<customers> clientsPage = new PageImpl<>(allCustomers, pageable, allCustomers.size());

        when(mockCustomerRepo.findAll(sort)).thenReturn(allCustomers);
        when(mockCompanyCustomerService.searchCompanyClients(anyString(), any(Sort.class))).thenReturn(allCustomers);

        mockMvc.perform(get("/company/search")
                        .param("sortField", "id")
                        .param("sortOrder", "asc")
                        .param("page", "1")
                        .param("size", "10")
                        .param("searchWord", ""))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse))
                .andExpect(model().attribute("clientsPage", clientsPage))
                .andExpect(model().attribute("sortField", "id"))
                .andExpect(model().attribute("sortOrder", "asc"))
                .andExpect(model().attribute("searchWord", ""));
    }
}
