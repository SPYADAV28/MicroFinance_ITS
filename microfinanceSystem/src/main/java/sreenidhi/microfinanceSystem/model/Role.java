package sreenidhi.microfinanceSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "roles") // You can name your table whatever you prefer, 'roles' is common
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, unique = true, nullable = false) // Add unique and not null constraints
    private String name; // e.g., "ROLE_USER", "ROLE_ADMIN"

    // If you don't use Lombok, you'll need to manually add
    // a no-argument constructor, a constructor with name, getters, and setters.
    // Example manual constructor:
    // public Role(String name) {
    // this.name = name;
    // }
}