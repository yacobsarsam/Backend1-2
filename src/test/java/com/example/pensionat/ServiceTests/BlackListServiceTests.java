package com.example.pensionat.ServiceTests;

import com.example.pensionat.Models.BlackListPerson;
import com.example.pensionat.Services.Imp.BlackListServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Test
    void getAllBlackListCustomers(){
        //IntegrationsTest
    }

    @Test
    void isCustomerBlackListedTest() throws IOException {
        BlackListPerson b1 = new BlackListPerson(1,"TestEmail","TestName", "TestGroup", LocalDateTime.now().toString(),false);
        BlackListPerson b2 = new BlackListPerson(2,"TestEmail2","TestName2", "TestGroup2", LocalDateTime.now().plusDays(2).toString(),true);
        List<BlackListPerson> blackListPersonList = new ArrayList<>();
        blackListPersonList.add(b1);
        blackListPersonList.add(b2);

        when(mockBlackListServiceImp.getAllBLKunder()).thenReturn(blackListPersonList);
        assertFalse(mockBlackListServiceImp.checkIfBLKundExistByEmailUtanAttSkapa(b1.email));
        assertFalse(mockBlackListServiceImp.checkIfBLKundExistByEmailUtanAttSkapa(b2.email));
        assertFalse(mockBlackListServiceImp.checkIfBLKundExistByEmailUtanAttSkapa("notExistingEmail"));
    }

    @Test
    void addBlackListCustomerTest(){
        //IntegrationsTest
    }

    @Test
    void createBlackListedCustomerTest(){
        when(mockBlackListServiceImp.greateBlackListPerson("TestName", "TestEmail", "TestGroup"))
                .thenReturn(new BlackListPerson("TestName", "TestEmail", "TestGroup", LocalDateTime.now(), false));
        BlackListPerson b1 = mockBlackListServiceImp.greateBlackListPerson("TestName", "TestEmail", "TestGroup");
        assertEquals("TestName", b1.name);
        assertEquals("TestEmail", b1.email);
        assertEquals("TestGroup", b1.group);
        assertNotEquals("otherName", b1.name);
        assertNotEquals("otherEmail", b1.email);
        assertNotEquals("otherGroup", b1.group);
    }

    @Test
    void getBlackListCustomerInfoTest_withFullValues(){
        Model model = mock(Model.class);
        when(mockBlackListServiceImp.getAllAvailableBLKundInfo("TestName", "TestEmail", "TestGroup", model)).thenReturn("visablkunder");
        when(model.addAttribute(eq("felmeddelande"), anyString())).thenReturn(model);

        assertEquals("visablkunder", mockBlackListServiceImp.getAllAvailableBLKundInfo("TestName", "TestEmail", "TestGroup", model));
        assertNull(model.getAttribute("felmeddelande"));
    }

    @Test
    void getBlackListCustomerInfoTest_withFalseValues(){
        Model model = mock(Model.class);
        when(model.addAttribute(eq("felmeddelande"), anyString())).thenReturn(model);
        when(model.getAttribute("felmeddelande")).thenReturn("Fel i kund-fälten, kontrollera och försök igen");
        when(mockBlackListServiceImp.getAllAvailableBLKundInfo("TestName", "TestEmail", null, model)).thenReturn("visablkunder");
        when(mockBlackListServiceImp.addModelsAndReturn("TestName", "TestEmail", null, model)).thenReturn("visablkunder");
        when(mockBlackListServiceImp.isCustomerFieldsFilledAndCorrect("TestName", "TestEmail", null)).thenReturn(false);

        assertEquals("visablkunder", mockBlackListServiceImp.getAllAvailableBLKundInfo("TestName", "TestEmail", null, model));
        assertNotNull(model.getAttribute("felmeddelande"));
        assertEquals("Fel i kund-fälten, kontrollera och försök igen", model.getAttribute("felmeddelande"));
    }

    @Test
    void getBlackListCustomerTest() throws IOException {
        BlackListPerson b1 = new BlackListPerson(1,"TestEmail","TestName", "TestGroup", LocalDateTime.now().toString(),false);
        when(mockBlackListServiceImp.getBlackListPrson("TestEmail")).thenReturn(b1);

        assertEquals(b1, mockBlackListServiceImp.getBlackListPrson("TestEmail"));
        assertNotEquals(b1, mockBlackListServiceImp.getBlackListPrson("otherEmail"));
        assertNotNull(mockBlackListServiceImp.getBlackListPrson("TestEmail"));
    }

    @Test
    void updateBlackListCustomerTest(){
        //IntegrationsTest
    }
}
