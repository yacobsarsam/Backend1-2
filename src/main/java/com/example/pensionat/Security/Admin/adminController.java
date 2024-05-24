package com.example.pensionat.Security.Admin;

import com.example.pensionat.Security.Models.User;
import com.example.pensionat.Security.Repositories.UserRepository;
import com.example.pensionat.Security.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("addUser", false);
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/add")
    public String getAddUserForm(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("addUser", true);
        return "admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/adduser")
    public String addUser(Model model, @ModelAttribute UserDTO userDTO) {

        User newUser = userService.addUser(userDTO);
        System.out.println("Added new user: " + newUser.getUsername());

        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("addUser", false);

        return "admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable ("id") UUID id, Model model){
        User u = userService.findUserById(id);
        model.addAttribute("user", u);
        model.addAttribute("roles", Arrays.asList("ADMIN", "RECEPTIONIST"));
        model.addAttribute("userRoles", u.getRoles().stream().map(role -> role.getRole()).collect(Collectors.toList()));
        return "admin/edituser";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/done")
    public String doneUser() {
        return "redirect:/admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public String updateUserInfo(Model model, User u){
        return userService.updateUser(u, model);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID currentUserId = getUserID(authentication);
        if (currentUserId.equals(id)) {
            return "redirect:/error";
        } else {
            userService.deleteUserById(id);
        }
        return "redirect:/admin/users";
    }
    private UUID getUserID(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername()).getId();
    }
}
