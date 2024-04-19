package cz.cvut.fel.nss.repository;

import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.model.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order getOrderByOrderId(Long orderId);
    List<Order> getOrdersByUserId(Long userId);

}
