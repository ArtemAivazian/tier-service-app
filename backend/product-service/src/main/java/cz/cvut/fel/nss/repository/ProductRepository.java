package cz.cvut.fel.nss.repository;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.model.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderId(Long orderId);
}
