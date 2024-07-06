package cz.cvut.fel.nss.mapper;

import cz.cvut.fel.nss.data.UserEntity;
import cz.cvut.fel.nss.dto.UserDto;
import cz.cvut.fel.nss.dto.UserLdo;

/**
 * Mapper class for converting between UserEntity, UserLdo, and UserDto objects.
 */
public class UserMapper {

    /**
     * Converts UserLdo to UserDto.
     *
     * @param userLdo the UserLdo object
     * @return the UserDto object
     */
    public static UserDto toDto(UserLdo userLdo) {
        if (userLdo == null) {
            return null;
        }

        return UserDto.builder()
                .userId(userLdo.getUserId())
                .firstName(userLdo.getFirstName())
                .lastName(userLdo.getLastName())
                .email(userLdo.getEmail())
                .password(userLdo.getPassword())
                .role(userLdo.getRole())
                .orders(userLdo.getOrders())
                .build();
    }

    /**
     * Converts UserDto to UserLdo.
     *
     * @param userDto the UserDto object
     * @return the UserLdo object
     */
    public static UserLdo toLdo(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return UserLdo.builder()
                .userId(userDto.getUserId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .orders(userDto.getOrders())
                .build();
    }

    /**
     * Converts UserEntity to UserLdo.
     *
     * @param userEntity the UserEntity object
     * @return the UserLdo object
     */
    public static UserLdo toLdo(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return UserLdo.builder()
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .encryptedPassword(userEntity.getEncryptedPassword())
                .role(userEntity.getRole())
                .build();
    }

    /**
     * Converts UserLdo to UserEntity.
     *
     * @param userLdo the UserLdo object
     * @return the UserEntity object
     */
    public static UserEntity toEntity(UserLdo userLdo) {
        if (userLdo == null) {
            return null;
        }

        return UserEntity.builder()
                .userId(userLdo.getUserId())
                .firstName(userLdo.getFirstName())
                .lastName(userLdo.getLastName())
                .email(userLdo.getEmail())
                .encryptedPassword(userLdo.getEncryptedPassword())
                .role(userLdo.getRole())
                .build();
    }
}