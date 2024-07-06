package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Role;
import cz.cvut.fel.nss.data.UserEntity;
import cz.cvut.fel.nss.dto.UserLdo;
import cz.cvut.fel.nss.exception.NotFoundException;
import cz.cvut.fel.nss.feign.OrdersServiceClient;
import cz.cvut.fel.nss.feign.proxy.OrdersServiceClientProxy;
import cz.cvut.fel.nss.mapper.UserMapper;
import cz.cvut.fel.nss.repository.UserRepository;
import cz.cvut.fel.nss.service.UserService;
import cz.cvut.fel.nss.shared.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserServiceImpl implements the UserService interface to provide operations for managing users.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrdersServiceClientProxy ordersServiceClient;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Registers a new user.
     *
     * @param userDetails the user details to register
     * @return the registered user details
     */
    @Override
    @CacheEvict(value = "users", allEntries = true)
    public UserLdo register(UserLdo userDetails) {
        userDetails.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
        userDetails.setRole(Role.USER);

        UserEntity userEntity = UserMapper.toEntity(userDetails);
        UserEntity registeredUser = userRepository.save(userEntity);

        return UserMapper.toLdo(registeredUser);
    }

    /**
     * Retrieves a user by their user ID.
     *
     * @param userId the ID of the user to retrieve
     * @param authorization the authorization header
     * @return the user details
     */
    @Override
    public UserLdo getUserByUserId(String userId, String authorization) {
        UserEntity userEntity = userRepository.findByUserId(Long.valueOf(userId));
        if(userEntity == null)
            throw new NotFoundException(HttpStatus.NOT_FOUND, "User not found by id" + userId);

        UserLdo userLdo = UserMapper.toLdo(userEntity);

        List<OrderDto> orderResponses = ordersServiceClient.getOrders(userId, authorization);
        userLdo.setOrders(orderResponses);

        return userLdo;
    }

    /**
     * Updates a user's details.
     *
     * @param userId the ID of the user to update
     * @param userDetails the updated user details
     * @return the updated user details
     */
    @Override
    @CachePut(value = "users", key = "#userId")
    public UserLdo updateUser(String userId, UserLdo userDetails) {
        UserEntity userEntity = userRepository.findByUserId(Long.valueOf(userId));
        if (userEntity == null)
            throw new NotFoundException(HttpStatus.NOT_FOUND, "User not found by id" + userId);

        userEntity.setFirstName(userDetails.getFirstName());
        userEntity.setLastName(userDetails.getLastName());
        userEntity.setEmail(userDetails.getEmail());
        if (userDetails.getPassword() != null) {
            userEntity.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        UserEntity updatedUser = userRepository.save(userEntity);

        return UserMapper.toLdo(updatedUser);
    }

    /**
     * Deletes a user by their user ID.
     *
     * @param userId the ID of the user to delete
     */
    @Override
    @CacheEvict(value = "users", key = "#userId")
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(Long.valueOf(userId));
        if (userEntity == null)
            throw new NotFoundException(HttpStatus.NOT_FOUND, "User not found by id" + userId);

        userRepository.delete(userEntity);
    }
}

