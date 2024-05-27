package com.example.pensionat.Security.Services;

import com.example.pensionat.Security.Models.Role;

public interface RoleService {
    public Role findByName(String roleName);
}
