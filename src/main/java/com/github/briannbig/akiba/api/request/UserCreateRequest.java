package com.github.briannbig.akiba.api.request;

import java.util.List;

public record UserCreateRequest(String username, String email, String fullName, String password, String passwordConfirm,
                                List<String> roles) {
}
