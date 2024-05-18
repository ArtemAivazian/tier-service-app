package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.dto.UserDto;
import cz.cvut.fel.nss.model.*;
import cz.cvut.fel.nss.service.UserService;
import cz.cvut.fel.nss.service.impl.UserServiceImpl;
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
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest registerRequest
    ) {
        UserDto userDto = mapper.map(registerRequest, UserDto.class);
        UserDto registeredUser = userService.register(userDto);
        RegisterResponse response = mapper.map(registeredUser, RegisterResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value="/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and (principal == #userId)")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable("userId") String userId,
            @RequestHeader("Authorization") String authorization
    ) {
        UserDto userDto = userService.getUserByUserId(userId, authorization);
        UserResponse returnValue = mapper.map(userDto, UserResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

}
