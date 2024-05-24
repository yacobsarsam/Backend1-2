package com.example.pensionat.Security.Admin;

import com.example.pensionat.Security.*;
import com.example.pensionat.Security.Models.User;
import com.example.pensionat.Security.Repositories.PasswordResetTokenRepository;
import com.example.pensionat.Security.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    private final BCryptPasswordEncoder passwordEncoder;


    private final PasswordResetTokenRepository tokenRepository;


    private final JavaMailSender mailSender;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUser(user);
        myToken.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 )); // 1 hour expiry
        tokenRepository.save(myToken);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken passToken = tokenRepository.findByToken(token);
        User user = passToken.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(passToken);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void sendPasswordResetEmail(String email) {
        User user = findUserByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            createPasswordResetTokenForUser(user, token);
            String resetUrl = "http://localhost:8080/reset-password?token=" + token;
            sendEmail(email, "Password Reset Request", "To reset your password, click the link below:\n" + resetUrl);
        }
    }

    public User findUserById(UUID id) {
        return userRepository.findById(id).get();
    }

    public String updateUser(User u, Model model) {
        if (isUserFieldsFilledAndCorrect(u.getUsername(), u.getPassword(), u.getEmail())){
            userRepository.save(u);
            return "admin/updateUserDone";
        } else {
            model.addAttribute("felmeddelande", "Fel i fälten, kontrollera och försök igen");
            model.addAttribute("user", u);
            return "admin/edituser";
        }
    }

    public boolean isUserFieldsFilledAndCorrect(String username, String password, String email){
        if (username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty())
            return false;
    return true;
    }
    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);

    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("noreply@example.com");

        mailSender.send(message);
    }


}

