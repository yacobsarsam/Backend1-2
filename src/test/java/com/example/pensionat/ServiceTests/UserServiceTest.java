package com.example.pensionat.ServiceTests;

import com.example.pensionat.Security.Admin.UserService;
import com.example.pensionat.Security.Models.Role;
import com.example.pensionat.Security.Models.User;
import com.example.pensionat.Security.PasswordResetToken;
import com.example.pensionat.Security.Repositories.PasswordResetTokenRepository;
import com.example.pensionat.Security.Repositories.RoleRepository;
import com.example.pensionat.Security.Repositories.UserRepository;
import com.example.pensionat.Security.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository mockUserRepo;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private BCryptPasswordEncoder mockPasswordEncoder;
    @Mock
    private PasswordResetTokenRepository mockTokenRepository;
    @Mock
    private JavaMailSender mockMailSender;
    @InjectMocks
    private UserService mockUserService;
    private static final UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    @Test
    void findAllUsers() {
        User u1 = new User(id, "Test", "Password", "test@test.se", null);
        User u2 = new User(id, "Test2", "Password2", "test2@test.se", null);
        when(mockUserRepo.findAll()).thenReturn(List.of(u1, u2));
        List<User> mockList = mockUserService.findAllUsers();
        assertTrue(mockList.size() == 2);
        assertTrue(Objects.equals(mockList.get(0).getUsername(), "Test"));

    }

    @Test
    void createPasswordResetTokenForUser() {
        String token = UUID.randomUUID().toString();
        User u1 = new User(id, "Test", "Password", "test@test.se", null);

        mockUserService.createPasswordResetTokenForUser(u1, token);

        ArgumentCaptor<PasswordResetToken> tokenGet = ArgumentCaptor.forClass(PasswordResetToken.class);
        verify(mockTokenRepository).save(tokenGet.capture());
        PasswordResetToken savedToken = tokenGet.getValue();

        assertEquals(token, savedToken.getToken());
        assertEquals(u1, savedToken.getUser());
    }

    @Test
    void resetPassword() {
        String token = UUID.randomUUID().toString();
        User u1 = new User(id, "Test", "Password", "test@test.se", null);
        PasswordResetToken pass = mock(PasswordResetToken.class);
        pass.setToken("passToken");
        pass.setUser(u1);

        when(mockTokenRepository.findByToken(anyString())).thenReturn(pass);
        when(pass.getUser()).thenReturn(u1);
        mockUserService.resetPassword(token, "newPass");
        verify(mockUserRepo).save(u1);

        verify(mockTokenRepository).delete(pass);
    }

    @Test
    void sendEmail() {
        String to = "test@test.se";
        String subject = "Hej";
        String text = "Hej hej";

        mockUserService.sendEmail(to, subject, text);
        ArgumentCaptor<SimpleMailMessage> mess = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mockMailSender).send(mess.capture());
        SimpleMailMessage sentMess = mess.getValue();

        assertEquals(subject, sentMess.getSubject());
        assertEquals(text, sentMess.getText());
        assertEquals("noreply@example.com", sentMess.getFrom());
    }

    @Test
    void sendPasswordResetEmail() {
        //TODO
    }

    @Test
    void findUserByEmail() {
        User u1 = new User(id, "Test", "Password", "test@test.se", null);
        when(mockUserRepo.findByEmail(anyString())).thenReturn(u1);
        assertEquals(mockUserService.findUserByEmail("test@test.se"), u1);
    }

    @Test
    void findUserById() {
        User u1 = new User(id, "Test", "Password", "test@test.se", null);
        when(mockUserRepo.findById(id)).thenReturn(Optional.of(u1));
        assertEquals(mockUserService.findUserById(id), u1);
    }

    @Test
    void updateUser() {
        User u1 = new User(id, "Test", "Password", "test@test.se", null);
        when(mockUserRepo.findById(id)).thenReturn(Optional.of(u1));
        Model model = mock(Model.class);
        mockUserService.updateUser(u1, model);
        verify(mockUserRepo, times(1)).save(u1);
    }

    @Test
    void deleteUserById() {
        User u1 = new User(id, "Test", "Password", "test@test.se", null);
        when(mockUserRepo.findById(id)).thenReturn(Optional.of(u1));
        mockUserService.deleteUserById(id);
        verify(mockUserRepo, times(1)).save(u1);
        verify(mockUserRepo, times(1)).deleteById(id);
    }

    @Test
    void addUser() {
        UserDTO u1 = new UserDTO("Test", "Password", "test@test.se", List.of("ADMIN"));
        Role adminRole = Role.builder().role("ADMIN").build();
        when(mockRoleRepository.findByRole("ADMIN")).thenReturn(adminRole);

        User u = mockUserService.addUser(u1);
        assertTrue(u.getRoles().size() == 1);
        assertEquals("Test", u.getUsername());
        assertEquals("test@test.se", u.getEmail());
        assertTrue(u.getRoles().contains(adminRole));
    }
}
