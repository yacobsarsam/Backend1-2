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
    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public User updateUser(String email){
        User user = findUserByEmail(email);
        return user;
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String addUser(User u, Model model) {
        if (isUserFieldsFilledAndCorrect(u.getUsername(), u.getPassword(), u.getEmail())){
            userRepository.save(u);
            return "updateKundDone";
        } else {
            model.addAttribute("felmeddelande", "Fel i kundfälten, kontrollera och försök igen");
            model.addAttribute("updateuser", u);
            return "updateuser";
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
