package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.model.*;
import cz.cvut.fel.nss.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest registerRequest
    ) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(registerRequest, UserDto.class);
        UserDto registeredUser = authService.register(userDto);
        RegisterResponse response = modelMapper.map(registeredUser, RegisterResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value="/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and (principal == #userId)")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable("userId") String userId,
            @RequestHeader("Authorization") String authorization
    ) {
        UserDto userDto = authService.getUserByUserId(userId, authorization);
        UserResponse returnValue = new ModelMapper().map(userDto, UserResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

//    @PutMapping(value="/{userId}")
//    @PostAuthorize("returnObject.body.email == authentication.name")
//    public ResponseEntity<UserResponse> updateUser(
//            @PathVariable("userId") String userId,
//            @RequestBody UserRequest userRequest
//    ) {
//        ModelMapper mapper = new ModelMapper();
//        UserDto updatedUserDto = userService.updateUser(userId, userRequest);
//        UserResponse returnValue = mapper.map(updatedUserDto, UserResponse.class);
//
//        return ResponseEntity.ok().body(returnValue);
//    }

    //    @GetMapping("/all")
//    public ResponseEntity<List<UserResponse>> getAllUsers() {
//        ModelMapper mapper = new ModelMapper();
//        List<UserDto> userDtos = userService.getAllUsers();
//        List<UserResponse> responses = userDtos.stream()
//                .map(userDto -> mapper.map(userDto, UserResponse.class))
//                .toList();
//        return ResponseEntity.status(HttpStatus.OK).body(responses);
//    }
//
//    @GetMapping(value="/{userId}")
//    public ResponseEntity<UserResponse> getUser(
//            @PathVariable("userId") String userId
//    ) {
//
//        UserDto userDto = userService.getUserByUserId(userId);
//        UserResponse returnValue = new ModelMapper().map(userDto, UserResponse.class);
//
//        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
//    }
//
//    @DeleteMapping("/{userId}")
//    public ResponseEntity deleteUser(
//            @PathVariable("userId") String userId
//    ) {
//        UserDto user = userService.getUserByUserId(userId);
//        if (user == null) return ResponseEntity.notFound().build();
//        userService.deleteByUserId(userId);
//        return ResponseEntity.ok().build();
//    }

}
