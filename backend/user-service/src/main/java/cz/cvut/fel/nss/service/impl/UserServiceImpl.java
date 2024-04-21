package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Role;
import cz.cvut.fel.nss.data.UserEntity;
import cz.cvut.fel.nss.dto.UserDto;
import cz.cvut.fel.nss.repository.UserRepository;
import cz.cvut.fel.nss.service.UserService;
import cz.cvut.fel.nss.feignClient.OrdersServiceClient;
import cz.cvut.fel.nss.shared.OrderResponse;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrdersServiceClient ordersServiceClient;
    private final ModelMapper mapper;

    public UserDto register(UserDto userDetails) {

        userDetails.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
        userDetails.setRole(Role.USER);

        UserEntity userEntity = mapper.map(userDetails, UserEntity.class);

        UserEntity registeredUser = userRepository.save(userEntity);

        UserDto returnValue = mapper.map(registeredUser, UserDto.class);
        return returnValue;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException("User not found by email" +email);

        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId, String authorization) {
        UserEntity userEntity = userRepository.findByUserId(Long.valueOf(userId));
        if(userEntity == null) throw new UsernameNotFoundException("User not found by id " + userId);

        UserDto userDto = mapper.map(userEntity, UserDto.class);

        List<OrderResponse> orderResponses = ordersServiceClient.getOrders(userId, authorization);
        userDto.setOrders(orderResponses);

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null) throw new UsernameNotFoundException(username);

        return new User(
                    userEntity.getEmail(),
                    userEntity.getEncryptedPassword(),
                    true, true, true, true,
                    userEntity.getAuthorities()
                );
    }


    //    @Override
//    public UserDto getUserByUserId(String userId) {
//        UserEntity userEntity = userRepository.findByUserId(userId);
//        if(userEntity == null) throw new UsernameNotFoundException("User not found");
//
//        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
//
//        return userDto;
//    }
//
//    @Override
//    public List<UserDto> getAllUsers() {
//        ModelMapper mapper = new ModelMapper();
//        List<UserEntity> userEntities = userRepository.findAll();
//        return userEntities.stream()
//                .map(user -> mapper.map(user, UserDto.class))
//                .toList();
//    }
//
//    @Override
//    public void deleteByUserId(String userId) {
//        userRepository.deleteByUserId(userId);
//    }
}

