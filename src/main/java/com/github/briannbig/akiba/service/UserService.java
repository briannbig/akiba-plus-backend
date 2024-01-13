package com.github.briannbig.akiba.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.briannbig.akiba.api.dto.RoleDTO;
import com.github.briannbig.akiba.api.dto.JWTResponse;
import com.github.briannbig.akiba.api.request.LoginParams;
import com.github.briannbig.akiba.api.request.TokenRefreshParams;
import com.github.briannbig.akiba.api.request.UserCreateRequest;
import com.github.briannbig.akiba.api.validator.UserCreateRequestValidator;
import com.github.briannbig.akiba.entities.Role;
import com.github.briannbig.akiba.entities.User;
import com.github.briannbig.akiba.entities.enums.RoleName;
import com.github.briannbig.akiba.repository.RoleRepository;
import com.github.briannbig.akiba.repository.UserRepository;
import com.github.briannbig.akiba.util.JWTUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authManager, JWTUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public Optional<User> registerUser(UserCreateRequest request) throws Exception {

        request = new UserCreateRequestValidator().validate(request);

        var optionalUser = userRepository.findByUsername(request.username());
        if (optionalUser.isPresent()) {
            log.error("Username {} already taken", request.username());
            throw new Exception("Username cannot be used");
        }

        optionalUser = userRepository.findByEmail(request.email());
        if (optionalUser.isPresent()) {
            log.error("Email {} already taken", request.email());
            throw new Exception("Email cannot be used");
        }

        optionalUser = userRepository.findByNationalID(request.nationalID());
        if (optionalUser.isPresent()) {
            log.error("National Id {} already taken", request.nationalID());
            throw new Exception("National ID number cannot be used");
        }
        optionalUser = userRepository.findByTelephone(request.telephone());
        if (optionalUser.isPresent()) {
            log.error("Telephone {} already taken", request.telephone());
            throw new Exception("Telephone cannot be used");
        }

        var roleDTOS = request.roles();

        if (request.roles().isEmpty()) {
            roleDTOS = new ArrayList<>();
            roleDTOS.add(new RoleDTO(null, RoleName.CASHIER.name()));
        }

        var roles = roleDTOS.stream().map(roleDTO -> roleRepository.findByRoleName(RoleName.valueOf(roleDTO.name())).orElseThrow()).collect(Collectors.toSet());

        var encodedPassword = passwordEncoder.encode(request.password());

        var user = new User(request.username(), request.email(), request.firstName(), request.lastName(), request.nationalID(), request.telephone(), encodedPassword, roles);
        user = userRepository.saveAndFlush(user);

        return Optional.of(user);
    }

    public Optional<User> findUserById(String id) throws Exception {

        return userRepository.findById(id);

    }

    public List<User> findUsersByRoleName(RoleName roleName) {

        return userRepository.findByRoles_RoleName(roleName);
    }

    public Optional<Role> findRoleByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public JWTResponse login(LoginParams request) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.userName(),
                        request.password()
                )
        );
        log.debug("authentication info: {}", authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User userDetails = (User) authentication.getPrincipal();

        var rolesSet = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        String accessToken = jwtUtil.generateJWT(userDetails.getUsername(), userDetails.getId(), rolesSet);
        String refreshToken = jwtUtil.generateRefreshJwtToken(userDetails.getUsername());

        var jwtResponse = new JWTResponse(accessToken, refreshToken, null);

        log.debug("jwtResponse: --> {}", jwtResponse);

        return jwtResponse;
    }

    public JWTResponse generateRefreshToken(TokenRefreshParams tokenRefreshRequest) {
        log.debug("old refresh-token requestBody():-->{}", tokenRefreshRequest);

        String refresh_token = tokenRefreshRequest.refreshToken();

        //logic to create new refresh/access token
        String newRefreshToken = "";
        String newAccessToken = "";

        if (refresh_token != null && jwtUtil.validateJwtToken(refresh_token)) {


            String username = jwtUtil.getUserNameFromJwtToken(refresh_token);
            log.debug("username from refresh token:-->{}", username);

            DecodedJWT jwt = JWT.decode(refresh_token);
            if (jwt.getExpiresAt().before(new Date())) {

                log.debug("-->refresh token is expired");
                newRefreshToken = jwtUtil.generateRefreshJwtToken(username);
            } else {
                log.debug("-->refresh token not expired");
                newRefreshToken = refresh_token;
            }
            // newRefreshToken = jwtUtils.newRefreshToken(username);

            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isEmpty()) {
                log.debug("user with username: {} does not exist", username);
                throw new IllegalStateException("username could not be found");
            }

            newAccessToken = jwtUtil.generateJWT(optionalUser.get().getUsername(), optionalUser.get().getId(),
                    optionalUser.get().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));

            log.debug("newRefreshToken:-->{} ,newAccessToken:-->{}", newRefreshToken, newAccessToken);

        }

        JWTResponse tokenRefreshResponse = new JWTResponse(newAccessToken, newRefreshToken, null);


        log.debug("new refresh-token/access-token responseBody():-->{}", tokenRefreshResponse);
        return tokenRefreshResponse;
    }


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void saveRole(Role role) {

        var optionalSuperAdminRole = roleRepository.findByRoleName(RoleName.SUPER_ADMIN);
        var optionalAdminRole = roleRepository.findByRoleName(RoleName.ADMIN);
        var optionalCashierRole = roleRepository.findByRoleName(RoleName.CASHIER);
        var optionalRegularRole = roleRepository.findByRoleName(RoleName.REGULAR);
        var optionalDeveloperRole = roleRepository.findByRoleName(RoleName.DEVELOPER);

        if (optionalSuperAdminRole.isPresent() && role.getRoleName() == RoleName.SUPER_ADMIN) {
            log.error("Role: {} already present", role.getRoleName());
        } else if (optionalAdminRole.isPresent() && Objects.equals(role.getRoleName(), RoleName.ADMIN)) {
            log.error("Role: {} already present", role.getRoleName());
        } else if (optionalCashierRole.isPresent() && Objects.equals(role.getRoleName(), RoleName.CASHIER)) {
            log.error("Role: {} already present", role.getRoleName());
        } else if (optionalRegularRole.isPresent() && Objects.equals(role.getRoleName(), RoleName.REGULAR)) {
            log.error("Role: {} already present", role.getRoleName());
        } else if (optionalDeveloperRole.isPresent() && role.getRoleName() == RoleName.DEVELOPER) {
            log.error("Role: {} already present", role.getRoleName());
        } else {
            roleRepository.save(role);
        }
    }


}
