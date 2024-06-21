package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.UserLdo;

/**
 * UserService provides operations for managing users.
 */
public interface UserService {
    /**
     * Registers a new user.
     *
     * @param userDetails the user details to register
     * @return the registered user details
     */
    UserLdo register(UserLdo userDetails);

    /**
     * Retrieves a user by their user ID.
     *
     * @param userId the ID of the user to retrieve
     * @param authorization the authorization header
     * @return the user details
     */
    UserLdo getUserByUserId(String userId, String authorization);

    /**
     * Updates a user's details.
     *
     * @param userId the ID of the user to update
     * @param userDetails the updated user details
     * @return the updated user details
     */
    UserLdo updateUser(String userId, UserLdo userDetails);

    /**
     * Deletes a user by their user ID.
     *
     * @param userId the ID of the user to delete
     */
    void deleteUser(String userId);
}
