package org.globallogic.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.globallogic.dto.LoginDto;
import org.globallogic.dto.PhoneDto;
import org.globallogic.dto.UserDto;
import org.globallogic.entity.Phone;
import org.globallogic.entity.User;
import org.globallogic.exception.NotValidCredentialsException;
import org.globallogic.exception.UserAlreadyExistException;
import org.globallogic.repository.UserRepository;
import org.globallogic.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserDto createUser(UserDto userDto) {

        Optional<User> userOpt = this.userRepository.findByEmail(userDto.getEmail());
        if (userOpt.isPresent()) {
            throw new UserAlreadyExistException(userDto.getEmail());
        }

        String token = this.jwtUtil.createJwt(userDto.getEmail());

        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .created(new Date()) //TODO: usar LocalDate
                .isActive(Boolean.TRUE)
                .build();

        List<PhoneDto> phoneDtos = userDto.getPhones();
        List<Phone> phones = new ArrayList<>();
        if (phoneDtos != null && !phoneDtos.isEmpty()) {
            for(PhoneDto phoneDto : phoneDtos) {
                phones.add(
                        Phone.builder()
                                .user(user)
                                .number(phoneDto.getNumber())
                                .cityCode(phoneDto.getCityCode())
                                .countryCode(phoneDto.getCountryCode())
                                .build()
                );
            }
        }
        user.setPhones(phones);
        user = userRepository.save(user);

        //TODO: reemplazar por un mapper
        return UserDto.builder()
                .id(user.getId())
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .token(token)
                .isActive(user.getIsActive())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phones(phoneDtos)
                .build();
    }

    public UserDto readUser(LoginDto loginDto) {
        Optional<User> userOpt = this.userRepository.findByEmail(loginDto.getEmail());
        if (!userOpt.isPresent()) {
            throw new NotValidCredentialsException();
        }

        User user = userOpt.get();
        if (!loginDto.getPassword().equals(user.getPassword())) {
            throw new NotValidCredentialsException();
        }

        // al parsear el token se valida tiempo de expiracion, la firma y ademas que el email sea el correcto
        this.jwtUtil.parseToken(loginDto.getToken(), loginDto.getEmail());

        // creamos un nuevo token
        String newToken = this.jwtUtil.createJwt(user.getEmail());
        user.setLastLogin(new Date());

        List<Phone> phones = user.getPhones();
        List<PhoneDto> phoneDtos = phones.stream().map(phone -> {
           return PhoneDto
                   .builder()
                   .number(phone.getNumber())
                   .cityCode(phone.getCityCode())
                   .countryCode(phone.getCountryCode())
                   .build();
        }).collect(Collectors.toList());

        this.userRepository.save(user);

        return UserDto
                .builder()
                .id(user.getId())
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .token(newToken)
                .isActive(user.getIsActive())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phones(phoneDtos)
                .build();
    }
}
