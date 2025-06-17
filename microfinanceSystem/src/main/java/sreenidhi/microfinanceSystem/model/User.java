package sreenidhi.microfinanceSystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor; // Add this if you haven't explicitly added it before
import lombok.AllArgsConstructor; // Add this if you use all-args constructor, otherwise optional
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.HashSet; // Import for HashSet
import java.util.Set; // Import for Set
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data // Provides getters and setters for all fields, including 'roles'
@NoArgsConstructor // JPA requires a no-argument constructor
@AllArgsConstructor // Optional: Add if you need a constructor with all arguments
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    // Define the Many-to-Many relationship with Role
    @ManyToMany(fetch = FetchType.EAGER) // Fetch roles eagerly when User is loaded
    @JoinTable(name = "user_roles", // Name of the join table
            joinColumns = @JoinColumn(name = "user_id"), // Column in join table for User ID
            inverseJoinColumns = @JoinColumn(name = "role_id") // Column in join table for Role ID
    )
    private Set<Role> roles = new HashSet<>(); // Change from String role to Set<Role> roles

    // UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map the Set of Role objects to a Collection of GrantedAuthority
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}