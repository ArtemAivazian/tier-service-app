package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.model.UserDto;
import cz.cvut.fel.nss.model.UserResponse;
import cz.cvut.fel.nss.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {
    private UserService userService;

    @GetMapping(value="/{userId}")
    @PostAuthorize("returnObject.body.email == authentication.name")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable("userId") String userId
    ) {

        UserDto userDto = userService.getUserByUserId(userId);
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




}
