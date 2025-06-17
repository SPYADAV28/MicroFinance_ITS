package sreenidhi.microfinanceSystem.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sreenidhi.microfinanceSystem.model.User;
import sreenidhi.microfinanceSystem.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    // Regex to quickly check if a string looks like an email
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // 'identifier' will be whatever the user typed into the login field (could be
        // username or email)
        User user = null; // Initialize user as null

        // 1. Try to find by Email if the identifier looks like an email
        Matcher matcher = EMAIL_PATTERN.matcher(identifier);
        if (matcher.matches()) {
            user = userRepository.findByEmail(identifier)
                    .orElse(null); // Try email, if not found, it's null for now
        }

        // 2. If user is still null (either not an email pattern, or email lookup
        // failed), try to find by Username
        if (user == null) {
            user = userRepository.findByUsername(identifier)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "User not found with identifier: " + identifier));
        }

        // Return UserDetails. Crucially, the principal name should be what you'll put
        // in the JWT token (e.g., email)
        // Using email is generally better for JWT unique subject claims.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // <--- IMPORTANT: Use the user's actual EMAIL as the principal name in
                                 // UserDetails
                                 // This ensures your JWT tokens consistently contain the user's email as
                                 // subject.
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(
            Collection<sreenidhi.microfinanceSystem.model.Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())) // Assuming your Role class has
                                                                                   // getName() for "USER", "ADMIN" etc.
                .collect(Collectors.toList());
    }
}