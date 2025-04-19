package com.test.api.dto.user;

import com.test.api.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String username;
    private String password;
    private User.RoleBase role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
