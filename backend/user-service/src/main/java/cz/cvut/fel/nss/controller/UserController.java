package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.dto.UserDto;
import cz.cvut.fel.nss.dto.UserLdo;
import cz.cvut.fel.nss.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserDto> register(
            @RequestBody UserDto registerRequest
    ) {
        UserLdo userLdo = mapper.map(registerRequest, UserLdo.class);
        UserLdo registeredUser = userService.register(userLdo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.map(registeredUser, UserDto.class));
    }

    @GetMapping(value="/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and (principal == #userId)")
    public ResponseEntity<UserDto> getUser(
            @PathVariable("userId") String userId,
            @RequestHeader("Authorization") String authorization
    ) {
        UserLdo returnedUser = userService.getUserByUserId(userId, authorization);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapper.map(returnedUser, UserDto.class));
    }

}
