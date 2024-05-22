package com.example.pensionat.ServiceTests;

import com.example.pensionat.Models.BlackListPerson;
import com.example.pensionat.Services.BlackListDataProvider;
import com.example.pensionat.Services.BokningService;
import com.example.pensionat.Services.Imp.BlackListServiceImp;
import com.example.pensionat.Services.KundService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
public class BlackListServiceTests {

    @MockBean
    private BlackListServiceImp mockBlackListServiceImp;
    @Mock
    private BlackListDataProvider mockBlackListDataProvider;
    @Mock
    private KundService mockKundService;
    @Mock
    private BokningService mockBokningsService;

    @BeforeEach
    void init(){
        mockBlackListServiceImp = new BlackListServiceImp(mockBlackListDataProvider, mockBokningsService, mockKundService);
        mockBlackListDataProvider = mock(BlackListDataProvider.class);
    }

    @Test
    void getAllBlackListCustomersTest(){
        //IntegrationsTest
    }

    @Test
    void checkIfBLKundExistByEmailUtanAttSkapaTest() throws IOException {
        BlackListPerson b1 = new BlackListPerson(2, "TestEmail","TestName", "TestGroup", LocalDateTime.now().plusDays(2).toString(), true);
        BlackListPerson b2 = new BlackListPerson(2, "TestEmail2","TestName2", "TestGroup2", LocalDateTime.now().plusDays(2).toString(), false);
        when(mockBlackListServiceImp.getAllBLKunder()).thenReturn(Arrays.asList(b1, b2));

        assertTrue(mockBlackListServiceImp.checkIfBLKundExistByEmailUtanAttSkapa(b1.email));
        assertFalse(mockBlackListServiceImp.checkIfBLKundExistByEmailUtanAttSkapa(b2.email));
        assertFalse(mockBlackListServiceImp.checkIfBLKundExistByEmailUtanAttSkapa("notExistingEmail"));
    }

    @Test
    void addBlackListCustomerTest(){
        //IntegrationsTest
    }

    @Test
    void createBlackListedCustomerTest(){
        BlackListPerson b1 = mockBlackListServiceImp.greateBlackListPerson("TestName", "TestEmail", "TestGroup");
        assertEquals("TestName", b1.name);
        assertEquals("TestEmail", b1.email);
        assertEquals("TestGroup", b1.group);
        assertNotEquals("otherName", b1.name);
        assertNotEquals("otherEmail", b1.email);
        assertNotEquals("otherGroup", b1.group);
    }

    @Test
    void getBlackListCustomerInfo_withFullValuesTest(){
        Model model = mock(Model.class);
        //when(model.addAttribute(eq("felmeddelande"), anyString())).thenReturn(model);

        assertEquals("visablkunder", mockBlackListServiceImp.getAllAvailableBLKundInfo("TestName", "TestEmail", "TestGroup", model));
        assertNull(model.getAttribute("felmeddelande"));
    }

    @Test
    void getBlackListCustomerInfo_withFalseValuesTest(){
        Model model = mock(Model.class);
        when(model.getAttribute("felmeddelande")).thenReturn("Fel i kund-fälten, kontrollera och försök igen");

        assertEquals("visablkunder", mockBlackListServiceImp.getAllAvailableBLKundInfo("TestName", "TestEmail", "", model));
        assertNotNull(model.getAttribute("felmeddelande"));
        assertEquals("Fel i kund-fälten, kontrollera och försök igen", model.getAttribute("felmeddelande"));
    }

    @Test
    void getBlackListCustomerTest() throws IOException {
        BlackListPerson b1 = new BlackListPerson(1,"TestEmail","TestName", "TestGroup", LocalDateTime.now().toString(),false);
        when(mockBlackListServiceImp.getAllBLKunder()).thenReturn(List.of(b1));
        assertEquals(b1, mockBlackListServiceImp.getBlackListPersonByEmail("TestEmail"));
        assertNull(mockBlackListServiceImp.getBlackListPersonByEmail("otherEmail"));
        assertNotNull(mockBlackListServiceImp.getBlackListPersonByEmail("TestEmail"));
    }

    @Test
    void updateBlackListCustomerTest(){
        //IntegrationsTest
    }
}
