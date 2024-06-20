package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.UserLdo;

<<<<<<< HEAD
public interface UserService extends UserDetailsService {
    UserDto register(UserDto userDetails);
    UserDto getUserDetailsByEmail(String email);
    UserDto getUserByUserId(String userId, String authorization);
=======
public interface UserService {
    UserLdo register(UserLdo userDetails);
    UserLdo getUserByUserId(String userId, String authorization);

    UserLdo updateUser(String userId, UserLdo userDetails);

    void deleteUser(String userId);
>>>>>>> main
}
