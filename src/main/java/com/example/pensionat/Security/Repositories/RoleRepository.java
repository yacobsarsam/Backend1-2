package com.example.pensionat.Security.Repositories;

import com.example.pensionat.Security.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByRole(String role);
}
