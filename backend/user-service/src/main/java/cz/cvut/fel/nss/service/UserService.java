package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.model.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserByUserId(String userId);

    List<UserDto> getAllUsers();
    void deleteByUserId(String userId);
}
