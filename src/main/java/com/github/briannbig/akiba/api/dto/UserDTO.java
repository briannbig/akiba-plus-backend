package com.github.briannbig.akiba.api.dto;


import com.github.briannbig.akiba.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public record UserDTO(String id, String email, String userName, String telephone, String nationalID, String firstname,
                      String lastName, List<RoleDTO> roles) {

    public static UserDTO from(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getUsername(), user.getTelephone(), user.getNationalID(), user.getFirstName(), user.getLastName(),
                user.getRoles().stream().map(RoleDTO::from).collect(Collectors.toList()));
    }
}
