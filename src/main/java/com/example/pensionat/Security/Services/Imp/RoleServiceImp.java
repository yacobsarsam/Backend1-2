package com.example.pensionat.Security.Services.Imp;

import com.example.pensionat.Security.Models.Role;
import com.example.pensionat.Security.Repositories.RoleRepository;
import com.example.pensionat.Security.Services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public Role findByName(String roleName) {
        System.out.println("i findByName");
        return roleRepository.findByRole(roleName);
    }
}
