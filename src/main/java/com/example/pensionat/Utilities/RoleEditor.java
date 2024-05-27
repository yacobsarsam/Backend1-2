package com.example.pensionat.Utilities;

import com.example.pensionat.Security.Models.Role;
import com.example.pensionat.Security.Services.RoleService;
import org.springframework.beans.propertyeditors.PropertiesEditor;

public class RoleEditor extends PropertiesEditor {

    private final RoleService roleService;

    public RoleEditor(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Role role = roleService.findByName(text);
        setValue(role);
    }
}
