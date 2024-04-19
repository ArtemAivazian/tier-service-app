package cz.cvut.fel.nss.util;

import cz.cvut.fel.nss.data.Role;
import cz.cvut.fel.nss.data.UserEntity;
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


    @Override
    public void run(String... args) throws Exception {
        UserEntity adminUser = new UserEntity();
        adminUser.setUserId(1L);
        adminUser.setFirstName("Artem");
        adminUser.setLastName("Aivazian");
        adminUser.setEmail("aivazart@fel.cvut.cz");
        adminUser.setEncryptedPassword(passwordEncoder.encode("P@ssword123"));
        adminUser.setRole(Role.ADMIN);

//        UserEntity storedAdminUser = authRepository.findByEmail("aivazart@fel.cvut.cz");
//
//        if(storedAdminUser == null) {
        authRepository.save(adminUser);
//        }
    }
}
