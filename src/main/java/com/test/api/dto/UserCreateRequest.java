package com.test.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateRequest {

    @NotEmpty(message = "Name are required")
    private String name;

    @NotEmpty(message = "Username are required")
    private String username;

    @NotEmpty(message = "Password are required")
    private String password;
}
