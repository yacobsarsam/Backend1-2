package com.example.pensionat.ControllerTests;

import com.example.pensionat.Controllers.BlackListController;
import com.example.pensionat.Models.BlackListPerson;
import com.example.pensionat.Services.BlackListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BlackListControllerTests {

    @Autowired
    private BlackListController mockBlackListController;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlackListService mockBlackListService;

    @BeforeEach
    void init(){
        assertNotNull(mockBlackListController);
        assertNotNull(mockBlackListService);
    }

    @Test
    void getAllBlKunderTest() throws Exception {
        String expectedResponse = "visablkunder";
        BlackListPerson blackListPerson = mock(BlackListPerson.class);
        when(mockBlackListService.getAllBLKunder()).thenReturn(List.of(blackListPerson));

        mockMvc.perform(get("/blacklist"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse));
    }

    @Test
    void createKundTest_noExistingCustomer() throws Exception {
        String expectedResponse = "visablkunder";
        when(mockBlackListService.getAllAvailableBLKundInfo(anyString(), anyString(), anyString(), any())).thenReturn("visablkunder");
        when(mockBlackListService.checkIfBLKundExistByEmailUtanAttSkapa(anyString())).thenReturn(false);

        mockMvc.perform(post("/blacklist/addtoblacklist")
                .param("name", "test")
                .param("email", "test")
                .param("group", "test")
                .param("model", "test"))
                .andExpect(view().name(expectedResponse));
    }

    @Test
    void createKundTest_withExistingCustomer() throws Exception {
        String expectedResponse = "visablkunder";
        when(mockBlackListService.getAllAvailableBLKundInfo(anyString(), anyString(), anyString(), any())).thenReturn("visablkunder");
        when(mockBlackListService.checkIfBLKundExistByEmailUtanAttSkapa(anyString())).thenReturn(true);

        mockMvc.perform(post("/blacklist/addtoblacklist")
                .param("name", "test")
                .param("email", "test")
                .param("group", "test")
                .param("model", "test"))
                .andExpect(view().name(expectedResponse));
    }

    @Test
    void editKundInfoTest_noExistingCustomer() throws Exception {
        //TODO
    }

    @Test
    void editKundInfoTest_withExistingCustomer() throws Exception {
        String expectedResponse = "updateblkund";
        BlackListPerson blackListPerson = new BlackListPerson();
        blackListPerson.setName("name");
        blackListPerson.setEmail("email");
        blackListPerson.setGroup("group");
        blackListPerson.setId(1);

        when(mockBlackListService.getBlackListPersonByEmail(anyString())).thenReturn(blackListPerson);

        mockMvc.perform(get("/blacklist/editByemail/{email}", "test")
                .flashAttr("updateblkund", blackListPerson)
                .param("model", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse))
                .andExpect(model().attributeExists("updateblkund"))
                .andExpect(model().attribute("updateblkund", blackListPerson));
    }

    @Test
    void updateKundInfoTest() throws Exception {
        String expectedResponse = "updateBLKundDone";
        BlackListPerson blackListPerson = mock(BlackListPerson.class);
        when(mockBlackListService.getAllBLKunder()).thenReturn(List.of(blackListPerson));

        mockMvc.perform(post("/blacklist/update")
                .flashAttr("allakunder", List.of(blackListPerson)))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedResponse))
                .andExpect(model().attributeExists("allakunder"))
                .andExpect(model().attribute("allakunder", List.of(blackListPerson)));
    }
}
