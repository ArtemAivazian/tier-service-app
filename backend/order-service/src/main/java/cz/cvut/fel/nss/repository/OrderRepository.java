package cz.cvut.fel.nss.repository;

import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.data.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository interface for managing Order entities.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Retrieves the list of ordered products associated with a specific order ID.
     *
     * @param orderId the order ID
     * @return the list of ordered products
     */
    @Query("SELECT o.orderedProducts FROM Order o WHERE o.orderId = :id")
    List<OrderedProduct> getOrderedProductsByOrderId(@Param("id") Long orderId);

    /**
     * Retrieves the list of orders associated with a specific user ID.
     *
     * @param userId the user ID
     * @return the list of orders
     */
    List<Order> getOrdersByUserId(Long userId);

    /**
     * Retrieves all orders.
     *
     * @return the list of all orders
     */
    List<Order> findAll();
}
