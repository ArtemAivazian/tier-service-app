package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Role;
import cz.cvut.fel.nss.data.UserEntity;
import cz.cvut.fel.nss.model.*;
import cz.cvut.fel.nss.config.JwtService;
import cz.cvut.fel.nss.repository.AuthRepository;
import cz.cvut.fel.nss.service.AuthService;
import cz.cvut.fel.nss.service.OrdersServiceClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrdersServiceClient ordersServiceClient;

    public UserDto register(UserDto userDetails) {

        userDetails.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
        userDetails.setRole(Role.USER);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

        authRepository.save(userEntity);

        UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
        return returnValue;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = authRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);

        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId, String authorization) {
        UserEntity userEntity = authRepository.findByUserId(Long.valueOf(userId));
        if(userEntity == null) throw new UsernameNotFoundException("User not found");

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        List<OrderResponse> orderResponses = ordersServiceClient.getOrders(userId, authorization);


        userDto.setOrders(orderResponses);

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = authRepository.findByEmail(username);

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

