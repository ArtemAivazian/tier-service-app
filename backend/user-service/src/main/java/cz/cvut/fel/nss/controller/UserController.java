package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.dto.UserDto;
import cz.cvut.fel.nss.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * UserController handles HTTP requests for user-related operations such as registration, retrieval, update, and deletion.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    /**
     * Registers a new user.
     *
     * @param registerRequest the user data transfer object containing registration information
     * @return the response entity containing the registered user data transfer object
     */
    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserDto> register(
            @RequestBody UserDto registerRequest
    ) {
        UserDto registeredUser = userFacade.register(registerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registeredUser);
    }

    /**
     * Retrieves a user by user ID.
     *
     * @param userId the ID of the user to retrieve
     * @param authorization the authorization header
     * @return the response entity containing the user data transfer object
     */
    @GetMapping(value="/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and (principal == #userId)")
    public ResponseEntity<UserDto> getUser(
            @PathVariable("userId") String userId,
            @RequestHeader("Authorization") String authorization
    ) {
        UserDto returnedUser = userFacade.getUser(userId, authorization);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(returnedUser);
    }

    /**
     * Updates a user by user ID.
     *
     * @param userId the ID of the user to update
     * @param userDto the user data transfer object containing updated user information
     * @return the response entity containing the updated user data transfer object
     */
    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and (principal == #userId)")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") String userId,
            @RequestBody @Valid UserDto userDto
    ) {
        UserDto updatedUser = userFacade.updateUser(userId, userDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedUser);
    }

    /**
     * Deletes a user by user ID.
     *
     * @param userId the ID of the user to delete
     * @return the response entity indicating the deletion status
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and (principal == #userId)")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("userId") String userId
    ) {
        userFacade.deleteUser(userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
