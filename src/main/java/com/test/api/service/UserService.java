package com.test.api.service;

import com.test.api.dto.UserCreateRequest;
import com.test.api.dto.UserResponse;
import com.test.api.dto.UserUpdateRequest;
import com.test.api.dto.WebResponse;
import com.test.api.entity.User;
import com.test.api.repository.UserRepository;
import com.test.api.util.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public WebResponse<Void> createNewUser(UserCreateRequest request) {
        User newUser = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()))
                .build();

        userRepository.save(newUser);

        return WebResponse.<Void>builder()
                .message("Berhasil menambahkan pengguna")
                .build();
    }

    public WebResponse<Void>updateUser(Long id,UserUpdateRequest request) {
        User userData = getUserById(id);

        if (request.getName() != null) {
            userData.setName(request.getName());
        }

        if (request.getUsername() != null) {
            userData.setUsername(request.getUsername());
        }

        if (request.getPassword() != null) {
            userData.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        userRepository.save(userData);

        return WebResponse.<Void>builder()
                .message("User successfully updated")
                .build();
    }


    public WebResponse<Void> deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);

        return WebResponse.<Void>builder()
                .message("User successfully deleted")
                .build();
    }


    public WebResponse<List<UserResponse>> getAllUser() {
        List<User> users = userRepository.findAll();

        List<UserResponse> mapToUserResponse = users.stream().map(
                x -> UserResponse.builder()
                        .id(x.getId())
                        .name(x.getName())
                        .username(x.getUsername())
                        .password(x.getPassword())
                        .createdAt(x.getCreatedAt())
                        .updatedAt(x.getUpdatedAt())
                        .build()
        ).toList();

        return WebResponse.<List<UserResponse>>builder()
                .message("Get data user success")
                .data(mapToUserResponse)
                .build();
    }

    public WebResponse<UserResponse>getOneUser(Long id) {
        User user = getUserById(id);

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        return WebResponse.<UserResponse>builder()
                .message("Get one user success")
                .data(userResponse)
                .build();
    }


    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found"));
    }

}
