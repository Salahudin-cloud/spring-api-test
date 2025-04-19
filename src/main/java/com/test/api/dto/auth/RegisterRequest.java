package com.test.api.dto.auth;

import com.test.api.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequest {

    @NotEmpty(message = "Name are required")
    private String name;

    @NotEmpty(message = "Username are required")
    private String username;

    @NotEmpty(message = "Password are required")
    private String password;

    @NotNull(message = "Role are required")
    private User.RoleBase role;
}
