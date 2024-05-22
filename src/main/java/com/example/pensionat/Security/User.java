package com.example.pensionat.Security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true, length = 100)
    private String username;
    @Column(nullable = false, unique = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles")
    private List<Role> roles;

    public User(String username, String password, List<Role> roles){
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
