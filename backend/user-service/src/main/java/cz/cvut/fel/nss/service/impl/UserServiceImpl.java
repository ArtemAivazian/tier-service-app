package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.User;
import cz.cvut.fel.nss.model.UserDto;
import cz.cvut.fel.nss.repository.UserRepository;
import cz.cvut.fel.nss.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Override
    public UserDto getUserByUserId(String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null) throw new UsernameNotFoundException("User not found");

        UserDto userDto = new ModelMapper().map(user, UserDto.class);

        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        ModelMapper mapper = new ModelMapper();
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> mapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public void deleteByUserId(String userId) {
        userRepository.deleteByUserId(userId);
    }
}
