package cz.cvut.fel.nss.util;

import cz.cvut.fel.nss.model.Role;
import cz.cvut.fel.nss.model.User;
import cz.cvut.fel.nss.repsitory.UserRepository;
import cz.cvut.fel.nss.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminLoader implements CommandLineRunner {

    private final UserService userService;

    private static final List<User> ADMINS = List.of(
            new User("Artem", "Aivazian", "aivazart@fel.cvut.cz", "123", Role.ROLE_ADMIN),
            new User("Ilia", "Zozuliak", "zozulill@fel.cvut.cz", "456", Role.ROLE_ADMIN)
    );

    @Override
    public void run(String... args) {
        for (User admin : ADMINS) {
            userService.registerAdmin(admin);
        }
    }
}