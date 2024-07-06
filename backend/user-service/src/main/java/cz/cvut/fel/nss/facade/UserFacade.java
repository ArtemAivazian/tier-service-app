package cz.cvut.fel.nss.facade;

import cz.cvut.fel.nss.dto.UserDto;
import cz.cvut.fel.nss.dto.UserLdo;
import cz.cvut.fel.nss.mapper.UserMapper;
import cz.cvut.fel.nss.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Facade class for handling user-related operations.
 */
@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;

    /**
     * Registers a new user.
     *
     * @param registerRequest the user data transfer object containing registration information
     * @return the registered UserDto object
     */
    public UserDto register(UserDto registerRequest) {
        UserLdo userLdo = UserMapper.toLdo(registerRequest);
        UserLdo registeredUser = userService.register(userLdo);
        return UserMapper.toDto(registeredUser);
    }

    /**
     * Retrieves user information by user ID.
     *
     * @param userId the ID of the user
     * @param authorization the authorization token
     * @return the UserDto object containing user information
     */
    public UserDto getUser(String userId, String authorization) {
        UserLdo returnedUser = userService.getUserByUserId(userId, authorization);
        return UserMapper.toDto(returnedUser);
    }

    /**
     * Updates user information.
     *
     * @param userId the ID of the user
     * @param userDto the user data transfer object containing updated information
     * @return the updated UserDto object
     */
    public UserDto updateUser(String userId, UserDto userDto) {
        UserLdo userLdo = UserMapper.toLdo(userDto);
        UserLdo updatedUser = userService.updateUser(userId, userLdo);
        return UserMapper.toDto(updatedUser);
    }

    /**
     * Deletes a user by user ID.
     *
     * @param userId the ID of the user
     */
    public void deleteUser(String userId) {
        userService.deleteUser(userId);
    }
}

