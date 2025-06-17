package sreenidhi.microfinanceSystem.dto;

import lombok.AllArgsConstructor; // ADD THIS IMPORT
import lombok.Data;
import lombok.NoArgsConstructor; // ADD THIS IMPORT

@Data
@AllArgsConstructor // Generates a constructor with all fields
@NoArgsConstructor // Generates a no-argument constructor
public class AuthResponse {
    private String token;
    private Long id; // User ID
    private String username; // Username
    private String email; // User email
    private String role; // User role
    private String tokenType = "Bearer"; // Default token type for JWTs

    // You might want to generate a constructor specifically for token, id,
    // username, email, role
    public AuthResponse(String token, Long id, String username, String email, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}