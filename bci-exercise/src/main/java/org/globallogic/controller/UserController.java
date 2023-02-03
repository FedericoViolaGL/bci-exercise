package org.globallogic.controller;

import lombok.RequiredArgsConstructor;
import org.globallogic.dto.LoginDto;
import org.globallogic.dto.UserDto;
import org.globallogic.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public UserDto signUp(@Valid @RequestBody UserDto userDto) {
        return this.userService.createUser(userDto);
    }

    @PostMapping("/login")
    public UserDto login(@Valid @RequestBody LoginDto loginDto) {
        return this.userService.readUser(loginDto);
    }

}
