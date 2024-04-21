package cz.cvut.fel.nss.repository;

import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.data.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o.orderedProducts FROM Order o WHERE o.orderId = :id")
    List<OrderedProduct> getOrderedProductsByOrderId(@Param("id") Long orderId);

    List<Order> getOrdersByUserId(Long userId);

}
