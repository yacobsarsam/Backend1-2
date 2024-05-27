package com.example.pensionat.Security.Admin;

import com.example.pensionat.Security.Models.Role;
import com.example.pensionat.Security.Models.User;
import com.example.pensionat.Security.PasswordResetToken;
import com.example.pensionat.Security.Repositories.PasswordResetTokenRepository;
import com.example.pensionat.Security.Repositories.RoleRepository;
import com.example.pensionat.Security.Repositories.UserRepository;
import com.example.pensionat.Security.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private AuthenticationManager authenticationManager;

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
        if (isUserFieldsFilledAndCorrect(u.getUsername(), u.getPassword(), u.getEmail())) {
            User existingUser = userRepository.findById(u.getId()).orElse(null);
            if (existingUser != null) {
                existingUser.setUsername(u.getUsername());
                existingUser.setPassword(passwordEncoder.encode(u.getPassword()));
                existingUser.setRoles(new HashSet<>(new ArrayList<>(u.getRoles())));
                userRepository.save(existingUser);


                Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
                String currentUsername = currentAuthentication.getName();
                if (currentUsername.equalsIgnoreCase(u.getUsername())){
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword()));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return "login";
                }
                return "admin/updateUserDone";
            } else {
                model.addAttribute("felmeddelande", "Användaren kunde inte hittas.");
                model.addAttribute("user", u);
                return "admin/edituser";
            }
        }return "admin/edituser";
    }

    private boolean isUserFieldsFilledAndCorrect(String username, String password, String email){
        if (username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty())
            return false;
    return true;
    }
    public void deleteUserById(UUID id) {
        User user = findUserById(id);
        user.setRoles(null);
        userRepository.save(user);
        userRepository.deleteById(id);
    }

    public User addUser(UserDTO userDTO) {
        User newUser = User.builder().username(userDTO.getUsername()).password(passwordEncoder.encode(userDTO.getPassword())).email(userDTO.getEmail()).build();
        Set<Role> roleList = new java.util.HashSet<>(Set.of());

        if (userDTO.getRoles().contains("ADMIN"))
            roleList.add(roleRepository.findByRole("ADMIN"));
        if (userDTO.getRoles().contains("RECEPTIONIST"))
            roleList.add(roleRepository.findByRole("RECEPTIONIST"));

        newUser.setRoles(roleList);

        userRepository.save(newUser);

        return newUser;
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

