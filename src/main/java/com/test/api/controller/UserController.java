package com.test.api.controller;

import com.test.api.dto.user.UserCreateRequest;
import com.test.api.dto.user.UserUpdateRequest;
import com.test.api.dto.WebResponse;
import com.test.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<Void>create(@Valid @RequestBody UserCreateRequest request) {
        return userService.createNewUser(request);
    }

    @PatchMapping(
            path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<Void>update(@RequestParam Long user_id, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(user_id,request);
    }

    @DeleteMapping(
            path = "/delete",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<Void>delete(@RequestParam Long user_id) {
        return userService.deleteUser(user_id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<?> getUsers(@RequestParam(required = false) Long user_id) {
        if (user_id != null) {
            return userService.getOneUser(user_id);
        } else {
            return userService.getAllUser();
        }
    }

}
