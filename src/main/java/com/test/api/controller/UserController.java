package com.test.api.controller;

import com.test.api.dto.user.UserCreateRequest;
import com.test.api.dto.user.UserResponse;
import com.test.api.dto.user.UserUpdateRequest;
import com.test.api.dto.WebResponse;
import com.test.api.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Endpoint")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<Void>create(@Valid @RequestBody UserCreateRequest request) {
        return userService.createNewUser(request);
    }

    @PatchMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<Void>update(@PathVariable("id") Long user_id, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(user_id,request);
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<Void>delete(@PathVariable("id") Long user_id) {
        return userService.deleteUser(user_id);
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> getUsers(@PathVariable("id") Long user_id) {
        return userService.getOneUser(user_id);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<UserResponse>>getAllUser(){
        return userService.getAllUser();
    }

}
