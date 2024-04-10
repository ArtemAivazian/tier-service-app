package cz.cvut.fel.nss.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/users")
    public String getAllUsers() {
        return "Secured Endpoint :: GET - Admin controller";
    }

    @PutMapping
    public String updateUser() {
        return "Secured Endpoint :: PUT - Admin controller";
    }

    @DeleteMapping
    public String deleteUser() {
        return "Secured Endpoint :: DELETE - Admin controller";
    }

}
