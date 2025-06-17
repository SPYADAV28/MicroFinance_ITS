package sreenidhi.microfinanceSystem.config; // Or a suitable package

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sreenidhi.microfinanceSystem.model.Role;
import sreenidhi.microfinanceSystem.repository.RoleRepository;

import java.util.Optional;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if "ROLE_USER" exists, if not, create it
        createRoleIfNotExists("ROLE_USER");

        // Check if "ROLE_ADMIN" exists, if not, create it
        createRoleIfNotExists("ROLE_ADMIN");

        // Add any other roles your application needs
        // createRoleIfNotExists("ROLE_MODERATOR");
    }

    private void createRoleIfNotExists(String roleName) {
        Optional<Role> existingRole = roleRepository.findByName(roleName);
        if (existingRole.isEmpty()) {
            Role newRole = new Role();
            newRole.setName(roleName);
            roleRepository.save(newRole);
            System.out.println("Created role: " + roleName);
        } else {
            System.out.println("Role already exists: " + roleName);
        }
    }
}