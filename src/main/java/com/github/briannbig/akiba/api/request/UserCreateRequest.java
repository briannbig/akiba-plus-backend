package com.github.briannbig.akiba.api.request;

import com.github.briannbig.akiba.api.dto.RoleDTO;

import java.util.List;

public record UserCreateRequest(String username, String email, String firstName, String lastName, String nationalID,
                                String telephone, String password, String passwordConfirm, List<RoleDTO> roles) {
}
