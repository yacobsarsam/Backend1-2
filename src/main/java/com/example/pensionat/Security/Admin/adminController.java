package com.example.pensionat.Security.Admin;

import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/edit/{email}")
    public String editUser(@PathVariable String email, Model model){
        User u = userService.updateUser(email);
        model.addAttribute("user", u);
        return "admin/edituser";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public String updateUserInfo(Model model, User u){

        List<User> allaUsers=userService.findAllUsers();//getAllCustomers();
        model.addAttribute("allaUsers", allaUsers);
        return userService.addUser(u, model);
    }
}
