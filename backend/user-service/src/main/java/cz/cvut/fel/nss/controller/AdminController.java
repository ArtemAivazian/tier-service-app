package cz.cvut.fel.nss.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String getAdmin() {
        return "Secured Endpoint :: GET - Admin controller";
    }

    @PostMapping
    public String post() {
        return "Secured Endpoint :: POST - Admin controller";
    }

}
