package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.data.User;
import cz.cvut.fel.nss.model.UserDto;
import cz.cvut.fel.nss.model.UserResponse;
import cz.cvut.fel.nss.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        ModelMapper mapper = new ModelMapper();
        List<UserDto> userDtos = userService.getAllUsers();
        List<UserResponse> responses = userDtos.stream()
                .map(userDto -> mapper.map(userDto, UserResponse.class))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping(value="/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable("userId") String userId
    ) {

        UserDto userDto = userService.getUserByUserId(userId);
        UserResponse returnValue = new ModelMapper().map(userDto, UserResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(
            @PathVariable("userId") String userId
    ) {
        UserDto user = userService.getUserByUserId(userId);
        if (user == null) return ResponseEntity.notFound().build();
        userService.deleteByUserId(userId);
        return ResponseEntity.ok().build();
    }
}
