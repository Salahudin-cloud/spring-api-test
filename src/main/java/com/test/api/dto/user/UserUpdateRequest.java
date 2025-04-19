package com.test.api.dto.user;

import com.test.api.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserUpdateRequest {

    private String name;
    private String username;
    private String password;
    private User.RoleBase role;

}
