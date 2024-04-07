package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.UserDto;
import cz.cvut.fel.nss.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


public interface UserService extends UserDetailsService {
    UserDto registerUser(UserDto request);
    void registerAdmin(User admin);
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    UserDto getUserById(Long userId);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long userId, UserDto updatedUser);
    void deleteUser(Long userId);
}
