package com.test.api.service;

import com.test.api.dto.user.UserCreateRequest;
import com.test.api.dto.user.UserResponse;
import com.test.api.dto.user.UserUpdateRequest;
import com.test.api.dto.WebResponse;
import com.test.api.entity.User;
import com.test.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public WebResponse<Void> createNewUser(UserCreateRequest request) {
        User newUser = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(newUser);

        return WebResponse.<Void>builder()
                .message("Berhasil menambahkan pengguna")
                .build();
    }

    @Transactional
    public WebResponse<Void>updateUser(Long id,UserUpdateRequest request) {
        User userData = getUserById(id);

        Optional.ofNullable(request.getName())
                .ifPresent(userData::setName);

        Optional.ofNullable(request.getUsername())
                .ifPresent(userData::setUsername);

        Optional.ofNullable(request.getPassword())
                .ifPresent(x -> {
                    userData.setPassword(passwordEncoder.encode(request.getPassword()));
                });

        Optional.ofNullable(request.getRole())
                        .ifPresent(userData::setRole);

        userRepository.save(userData);

        return WebResponse.<Void>builder()
                .message("User successfully updated")
                .build();
    }


    @Transactional
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
                        .role(x.getRole())
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
                .role(user.getRole())
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
