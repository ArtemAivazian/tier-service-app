package cz.cvut.fel.nss.util;

import cz.cvut.fel.nss.data.Role;
import cz.cvut.fel.nss.data.UserEntity;
import cz.cvut.fel.nss.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        UserEntity adminUser = new UserEntity();
        adminUser.setUserId(1L);
        adminUser.setFirstName("Artem");
        adminUser.setLastName("Aivazian");
        adminUser.setEmail("aivazart@fel.cvut.cz");
        adminUser.setEncryptedPassword(passwordEncoder.encode("P@ssword123"));
        adminUser.setRole(Role.ADMIN);

        userRepository.save(adminUser);
    }
}
