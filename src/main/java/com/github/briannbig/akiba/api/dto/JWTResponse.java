package com.github.briannbig.akiba.api.dto;


import jakarta.annotation.Nullable;

import java.util.Objects;


public record JWTResponse(String access_token, String refresh_token, @Nullable String type) {
    public JWTResponse {
        if (Objects.isNull(type)) type = "Bearer";
    }
}
