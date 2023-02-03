package org.globallogic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Builder
@Setter
@Getter
public class UserDto {
    private Long id;
    private String name;
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotBlank(message = "password is mandatory")
    @Size(min = 8, max = 12, message = "password must be between 8 - 12 characters length")
    @Pattern(regexp = "^(?=.*[A-Z]).*$", message = "password must have a capital letter")
    @Pattern(regexp = "^((.*?[0-9]){2,}).*$", message = "password must have 2 numbers ")
    private String password;
    private String token;
    private Date created;
    private Date lastLogin;
    private Boolean isActive;
    private List<PhoneDto> phones;
}
