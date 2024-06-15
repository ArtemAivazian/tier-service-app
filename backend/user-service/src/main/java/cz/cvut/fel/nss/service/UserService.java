package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.UserLdo;

public interface UserService {
    UserLdo register(UserLdo userDetails);
    UserLdo getUserByUserId(String userId, String authorization);
}
