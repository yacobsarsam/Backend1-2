package com.example.pensionat.Security.Admin;

import com.example.pensionat.Security.Models.User;
import com.example.pensionat.Security.PasswordResetToken;
import com.example.pensionat.Security.Repositories.PasswordResetTokenRepository;
import com.example.pensionat.Security.Repositories.RoleRepository;
import com.example.pensionat.Security.Repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceTests {
    @Mock
    private UserRepository mockUserRepo;
    @Mock
    private PasswordResetTokenRepository mockTokenRepository;
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
        ArgumentCaptor<PasswordResetToken> tokenCaptor = ArgumentCaptor.forClass(PasswordResetToken.class);

        verify(mockTokenRepository).save(tokenCaptor.capture());
        PasswordResetToken savedToken = tokenCaptor.getValue();
        assertEquals(token, savedToken.getToken());
        assertEquals(u1, savedToken.getUser());
    }

    @Test
    void resetPassword() {
    }

    @Test
    void findUserByEmail() {
    }

    @Test
    void sendPasswordResetEmail() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void isUserFieldsFilledAndCorrect() {
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void addUser() {
    }

    @Test
    void sendEmail() {
    }
}