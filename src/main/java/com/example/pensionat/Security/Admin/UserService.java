package com.example.pensionat.Security.Admin;

import com.example.pensionat.Models.Kund;
import com.example.pensionat.Security.User;
import com.example.pensionat.Security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<User> findAllUsers() {
        return userRepository.findAll();
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
}
