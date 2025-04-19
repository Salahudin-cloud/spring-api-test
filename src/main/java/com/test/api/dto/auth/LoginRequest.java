package com.test.api.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {

    @NotEmpty(message = "Username are required")
    private String username;

    @NotEmpty(message = "Password are required")
    private String password;

}
