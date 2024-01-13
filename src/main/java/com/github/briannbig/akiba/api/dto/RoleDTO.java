package com.github.briannbig.akiba.api.dto;


import com.github.briannbig.akiba.entities.Role;

public record RoleDTO(String id, String name) {

    public static RoleDTO from(Role role) {
        return new RoleDTO(role.getId(), role.getRoleName().name());
    }
}
