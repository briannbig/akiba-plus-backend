package com.github.briannbig.akiba.config.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * This method "commence()"will be triggered anytime unauthenticated User requests
     * a secured HTTP resource and an AuthenticationException is thrown.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized error:--> {}", authException.getMessage());
        //HttpServletResponse.SC_UNAUTHORIZED is the 401 Status code.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized" + authException.getMessage());
        response.setHeader("error", authException.getMessage());
    }

}
