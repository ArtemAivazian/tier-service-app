package cz.cvut.fel.nss.repository;

import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.data.OrderedProduct;
import cz.cvut.fel.nss.model.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    List<OrderedProduct> getOrderedProductsByOrderId(Long orderId);
    @Query("SELECT o.orderedProducts FROM Order o WHERE o.orderId = :id")
    List<OrderedProduct> getOrderedProductsByOrderId(@Param("id") Long orderId);

    List<Order> getOrdersByUserId(Long userId);

}
