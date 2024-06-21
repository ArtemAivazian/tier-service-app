package cz.cvut.fel.nss.service.impl;


import cz.cvut.fel.nss.data.UserEntity;
import cz.cvut.fel.nss.dto.AuthDto;
import cz.cvut.fel.nss.repository.AuthRepository;
import cz.cvut.fel.nss.service.AuthService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AuthService interface.
 * This class handles authentication-related operations.
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final ModelMapper mapper;

    /**
     * Retrieves the user details by email.
     *
     * @param email the email of the user whose details are to be retrieved
     * @return an AuthDto containing the user details
     * @throws UsernameNotFoundException if the user is not found by email
     */
    @Override
    public AuthDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = authRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException("User not found by email: " +email);

        return mapper.map(userEntity, AuthDto.class);
    }

    /**
     * Loads the user details by username.
     *
     * @param username the username of the user whose details are to be retrieved
     * @return UserDetails containing the user's details
     * @throws UsernameNotFoundException if the user is not found by username
     */
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

}

