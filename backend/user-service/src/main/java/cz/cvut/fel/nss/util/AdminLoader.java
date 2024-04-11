package cz.cvut.fel.nss.util;

import cz.cvut.fel.nss.data.Role;
import cz.cvut.fel.nss.data.User;
import cz.cvut.fel.nss.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdminLoader implements CommandLineRunner {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public static Set<User> ADMINS = Set.of(
            new User(UUID.randomUUID().toString(), "Artem", "Aivazian", "aivazart@fel.cvut.cz", "P@ssword123", Role.ADMIN)
    );

    @Override
    public void run(String... args) throws Exception {
        ADMINS.stream()
                .peek(admin -> admin.setPassword(passwordEncoder.encode(admin.getPassword())))
                .forEach(authRepository::save);
    }
}
