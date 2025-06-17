package sreenidhi.microfinanceSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sreenidhi.microfinanceSystem.security.JwtTokenProvider;
import sreenidhi.microfinanceSystem.dto.LoginDto;
import sreenidhi.microfinanceSystem.dto.AuthResponse;
import sreenidhi.microfinanceSystem.dto.RegisterDto;
import sreenidhi.microfinanceSystem.model.User;
import sreenidhi.microfinanceSystem.model.Role;
import sreenidhi.microfinanceSystem.repository.UserRepository;
import sreenidhi.microfinanceSystem.repository.RoleRepository;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final JwtTokenProvider tokenProvider;
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;

        public AuthController(AuthenticationManager authenticationManager,
                        JwtTokenProvider tokenProvider,
                        UserRepository userRepository,
                        RoleRepository roleRepository,
                        PasswordEncoder passwordEncoder) {
                this.authenticationManager = authenticationManager;
                this.tokenProvider = tokenProvider;
                this.userRepository = userRepository;
                this.roleRepository = roleRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @PostMapping("/login")
        public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
                // Authenticate using the generic 'username' field from LoginDto
                // The CustomUserDetailsService will handle if it's an email or username
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginDto.getUsername(), // <--- Use the generic 'username' field here
                                                loginDto.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Get the principal name that was successfully authenticated by
                // CustomUserDetailsService.
                // This will be the user's actual EMAIL (as configured in
                // CustomUserDetailsService).
                String authenticatedPrincipalEmail = authentication.getName();

                // Generate JWT token using this authenticated email as the subject
                String token = tokenProvider.generateToken(authentication);

                // Fetch user details from DB using the *actual email* for the AuthResponse
                // This lookup will now be reliable because authenticatedPrincipalEmail is
                // guaranteed to be the email.
                User user = userRepository.findByEmail(authenticatedPrincipalEmail) // <--- Fetch user using their EMAIL
                                .orElseThrow(() -> new RuntimeException(
                                                "User not found after authentication. This should not happen."));

                String roleName = user.getRoles().stream().findFirst().map(Role::getName).orElse("USER");

                return ResponseEntity.ok(new AuthResponse(
                                token,
                                user.getId(),
                                user.getUsername(), // Return the user's actual username field from the User object
                                user.getEmail(), // Return the user's actual email field from the User object
                                roleName));
        }

        @PostMapping("/register")
        public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
                // Check if username or email already exists (using correct methods)
                if (userRepository.existsByUsername(registerDto.getUsername())) {
                        return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
                }
                if (userRepository.existsByEmail(registerDto.getEmail())) {
                        return new ResponseEntity<>("Email is already registered!", HttpStatus.BAD_REQUEST);
                }

                User user = new User();
                user.setUsername(registerDto.getUsername());
                user.setEmail(registerDto.getEmail());
                user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

                Role roles = roleRepository.findByName("ROLE_USER")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                user.setRoles(Collections.singleton(roles));

                userRepository.save(user);

                return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
        }
}