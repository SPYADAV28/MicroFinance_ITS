package sreenidhi.microfinanceSystem.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String email;
    private String password;
    // Add other fields if needed for registration (e.g., name, phone)
}