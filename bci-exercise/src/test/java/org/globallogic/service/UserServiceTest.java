package org.globallogic.service;

import org.globallogic.dto.LoginDto;
import org.globallogic.dto.UserDto;
import org.globallogic.entity.User;
import org.globallogic.exception.NotValidCredentialsException;
import org.globallogic.exception.TokenNotValidException;
import org.globallogic.exception.UserAlreadyExistException;
import org.globallogic.repository.UserRepository;
import org.globallogic.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private UserService userService;

    @Test
    void createUserTest() {
        UserDto userDto = UserDto.builder().email("algo@gmail.com").password("22222C222").build();
        User user = User.builder().id(1L).email("algo@gmail.com").password("22222C222").build();
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(jwtUtil.createJwt(any())).thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbGdvNkBhYWFhYWEuY29tIiwiaWF0IjoxNjc1MjE1NzEzLCJleHAiOjE2NzUyMTYwMTN9.48wQE1BwDZPm1TcXkbpckxWW3K3vIDoOuk69uCoC04WMzUlAYqIxSzSQG7XaRB-rbOjP39vh_afFfdLWPGLK1Q");
        when(userRepository.save(any())).thenReturn(user);

        assertNotNull(userService.createUser(userDto));
    }
    @Test
    void userAlreadyExistTest() {
        User user = User.builder().id(1L).email("algo@gmail.com").password("22222C222").build();
        UserDto userDto = UserDto.builder().email("algo@gmail.com").password("22222C222").build();
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistException.class, () -> userService.createUser(userDto));
    }

    @Test
    void readWrongUserTest() {
        LoginDto loginDto = LoginDto.builder().email("algo@gmail.com").password("22222C222")
                .token("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbGdvNkBhYWFhYWEuY29tIiwiaWF0IjoxNjc1MjE1NzEzLCJleHAiOjE2NzUyMTYwMTN9.48wQE1BwDZPm1TcXkbpckxWW3K3vIDoOuk69uCoC04WMzUlAYqIxSzSQG7XaRB-rbOjP39vh_afFfdLWPGLK1Q")
                .build();
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        assertThrows(NotValidCredentialsException.class, () -> userService.readUser(loginDto));
    }

    @Test
    void tokenNotValidTest() {
        User user = User.builder().id(1L).email("algo@gmail.com").password("22222C222").build();
        LoginDto loginDto = LoginDto.builder().email("algo@gmail.com").password("22222C222")
                .token("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbGdvNkBhYWFhYWEuY29tIiwiaWF0IjoxNjc1MjE1NzEzLCJleHAiOjE2NzUyMTYwMTN9.48wQE1BwDZPm1TcXkbpckxWW3K3vIDoOuk69uCoC04WMzUlAYqIxSzSQG7XaRB-rbOjP39vh_afFfdLWPGLK1Q")
                .build();
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(jwtUtil.parseToken(any(), any())).thenThrow(TokenNotValidException.class);

        assertThrows(TokenNotValidException.class, () -> userService.readUser(loginDto));
    }
}
