package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Role;
import cz.cvut.fel.nss.data.UserEntity;
import cz.cvut.fel.nss.dto.UserLdo;
import cz.cvut.fel.nss.repository.UserRepository;
import cz.cvut.fel.nss.service.UserService;
import cz.cvut.fel.nss.feign.OrdersServiceClient;
import cz.cvut.fel.nss.shared.OrderDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrdersServiceClient ordersServiceClient;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public UserLdo register(UserLdo userDetails) {

        userDetails.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
        userDetails.setRole(Role.USER);

        UserEntity userEntity = mapper.map(userDetails, UserEntity.class);

        UserEntity registeredUser = userRepository.save(userEntity);

        return mapper.map(registeredUser, UserLdo.class);
    }

    @Override
    @Cacheable(value = "userServiceCache", key = "#userId")
    public UserLdo getUserByUserId(String userId, String authorization) {
        UserEntity userEntity = userRepository.findByUserId(Long.valueOf(userId));
        if(userEntity == null) throw new UsernameNotFoundException("User not found by id " + userId);

        UserLdo userLdo = mapper.map(userEntity, UserLdo.class);

        List<OrderDto> orderResponses = ordersServiceClient.getOrders(userId, authorization);
        userLdo.setOrders(orderResponses);

        return userLdo;
    }

}

