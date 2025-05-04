package com.test.api.controller;

import com.test.api.dto.WebResponse;
import com.test.api.dto.auth.LoginRequest;
import com.test.api.dto.auth.RegisterRequest;
import com.test.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth Endpoint")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "Login for new user")
    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<Void>login(@Valid @RequestBody LoginRequest request) {
        return authService.loginUser(request);
    }

    @Operation(summary = "Register for new user")
    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<Void>register(@Valid @RequestBody RegisterRequest request){
        return authService.registerNewUser(request);
    }

}
