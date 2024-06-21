package cz.cvut.fel.nss.repository;

import cz.cvut.fel.nss.data.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository is a JPA repository interface for managing UserEntity instances.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Finds a UserEntity by its userId.
     *
     * @param userId the ID of the user
     * @return the UserEntity with the specified userId, or null if not found
     */
    UserEntity findByUserId(Long userId);
}
