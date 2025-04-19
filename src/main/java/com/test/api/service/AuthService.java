package com.test.api.service;

import com.test.api.dto.WebResponse;
import com.test.api.dto.auth.LoginRequest;
import com.test.api.dto.auth.RegisterRequest;
import com.test.api.entity.User;
import com.test.api.repository.UserRepository;
import com.test.api.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public WebResponse<Void>loginUser(LoginRequest request) {
        User user = getUserByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username / password wrong");
        }

        try{
            String token = JwtUtil.generateToken(user.getUsername(), String.valueOf(user.getRole()));

            return WebResponse.<Void>builder()
                    .message("Login successfully")
                    .token(token)
                    .build();
        } catch (Exception e) {
            return WebResponse.<Void>builder()
                    .message(e.getMessage())
                    .build();
        }

    }

    public WebResponse<Void>registerNewUser(RegisterRequest request) {
        if (checkIfUserExistsByUsername(request.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exist");
        }

        User newUser = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(newUser);

        return WebResponse.<Void>builder()
                .message("Register successfully")
                .build();
    }

    private boolean checkIfUserExistsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + username + " not found"));
    }

}
