package cz.cvut.fel.nss.repository;

import cz.cvut.fel.nss.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Product entities.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds a product by its name.
     *
     * @param name the name of the product
     * @return the product entity
     */
    Product findByName(String name);
}
