package com.github.briannbig.akiba.api;


import com.github.briannbig.akiba.api.dto.UserDTO;
import com.github.briannbig.akiba.api.dto.JWTResponse;
import com.github.briannbig.akiba.api.request.LoginParams;
import com.github.briannbig.akiba.api.request.TokenRefreshParams;
import com.github.briannbig.akiba.api.request.UserCreateRequest;
import com.github.briannbig.akiba.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Brian Barasa
 */
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Login")

public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/sign-in")
    public ResponseEntity<JWTResponse> login(@RequestBody LoginParams request) {
//        log.debug("login request: --> {}", request);
        JWTResponse jwtResponse = userService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @PostMapping("/sign-up")

    public ResponseEntity<UserDTO> Register(@RequestBody UserCreateRequest request) {

        //return userService.signUp(request);
        try {
            var userDTO = userService.registerUser(request).map(UserDTO::from);

            return userDTO.map(u -> ResponseEntity.status(HttpStatus.CREATED).body(u)).orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
        }
        catch (Exception e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())).build();
        }

    }

    @PostMapping("/refresh-token")

    public ResponseEntity<JWTResponse> RefreshToken(@RequestBody TokenRefreshParams request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.generateRefreshToken(request));


    }

}
