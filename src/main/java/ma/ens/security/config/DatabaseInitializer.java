package ma.ens.security.config;

import ma.ens.security.entities.Role;
import ma.ens.security.entities.User;
import ma.ens.security.repositories.RoleRepository;
import ma.ens.security.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitializer {

    @Bean
    CommandLineRunner init(RoleRepository roleRepo,
                           UserRepository userRepo,
                           BCryptPasswordEncoder encoder) {
        return args -> {

            // Création des rôles si inexistants
            if (!roleRepo.existsByName("ROLE_ADMIN")) {
                roleRepo.save(new Role(null, "ROLE_ADMIN"));
            }
            if (!roleRepo.existsByName("ROLE_USER")) {
                roleRepo.save(new Role(null, "ROLE_USER"));
            }

            Role adminRole = roleRepo.findByName("ROLE_ADMIN");
            Role userRole  = roleRepo.findByName("ROLE_USER");

            // Création des utilisateurs si inexistants
            if (!userRepo.existsByUsername("admin")) {
                userRepo.save(new User(null, "admin",
                        encoder.encode("1234"), true,
                        List.of(adminRole, userRole)));
                System.out.println("✅ Utilisateur 'admin' créé");
            }

            if (!userRepo.existsByUsername("user")) {
                userRepo.save(new User(null, "user",
                        encoder.encode("1111"), true,
                        List.of(userRole)));
                System.out.println("✅ Utilisateur 'user' créé");
            }

            System.out.println("✅ Base de données JWT initialisée !");
        };
    }
}
