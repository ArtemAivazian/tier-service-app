package cz.cvut.fel.nss.repository;

import cz.cvut.fel.nss.data.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing user data in the database.
 * This interface provides CRUD operations for UserEntity.
 */
@Repository
public interface AuthRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Finds a user entity by its email.
     *
     * @param email the email of the user to be found
     * @return the user entity with the given email, or null if no user found
     */
    UserEntity findByEmail(String email);
}
