package com.github.briannbig.akiba.config.security;


import com.github.briannbig.akiba.UserContext;
import com.github.briannbig.akiba.entities.User;
import com.github.briannbig.akiba.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class AuthTokenFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());
    //filter that executes once per request.


    @Autowired
    private JWTUtil jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            //get JWT from the Authorization header (by removing Bearer prefix)
            String jwt = parseJwt(request);


            //if the request has JWT, validate it, parse username from it
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                //parsing  username from it
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                //from username, get UserDetails to create an Authentication object

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                //set the current UserDetails in SecurityContext
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                UserContext.setUser((User) userDetails);

            }
        } catch (Exception e) {
            log.error("Cannot set user authentication:--> {}", e.getMessage());
            response.setHeader("error", e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        //removing Bearer prefix
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
