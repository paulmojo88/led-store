package com.company.ledstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    public UserDTO toUserDTO(PasswordEncoder passwordEncoder) {
        return new UserDTO(id, username, passwordEncoder.encode(password), fullName, street, city, state, zip, phoneNumber);
    }
}
