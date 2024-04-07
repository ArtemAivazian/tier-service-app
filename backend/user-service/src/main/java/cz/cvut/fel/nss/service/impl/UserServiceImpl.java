package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.dto.UserDto;
import cz.cvut.fel.nss.exception.ResourceNotFoundException;
import cz.cvut.fel.nss.model.Role;
import cz.cvut.fel.nss.model.User;
import cz.cvut.fel.nss.repsitory.UserRepository;
import cz.cvut.fel.nss.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = mapToUser(userDto);
        User registeredUser = userRepository.save(user);
        log.info("User by email {} is registered", user.getEmail());
        return mapToUserDto(registeredUser);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException("Invalid username or password.");
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User does not exist by this ID: " + userId)
                );

        return mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long userId, UserDto updatedUser) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User does not exist by this ID: " + userId)
        );
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setRole(updatedUser.getRole());

        User updated = userRepository.save(user);
        log.info("User by email {} is updated", user.getEmail());
        return mapToUserDto(updated);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User does not exist by this ID: " + userId)
        );
        userRepository.deleteById(userId);
        log.info("User by email {} has been deleted", user.getEmail());
    }

    @Override
    public void registerAdmin(User admin) {
        var a = User.builder()
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .email(admin.getEmail())
                .password(passwordEncoder.encode(admin.getPassword()))
                .role(admin.getRole())
                .build();
        log.info("Admin by email {} is registered", a.getEmail());
        userRepository.save(a);
    }



    private UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    private User mapToUser(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.ROLE_USER)
                .build();
    }
}
