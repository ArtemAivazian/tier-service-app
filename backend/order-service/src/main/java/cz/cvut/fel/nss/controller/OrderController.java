package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.dto.OrderDto;
import cz.cvut.fel.nss.dto.OrderLdo;
import cz.cvut.fel.nss.facade.OrderFacade;
import cz.cvut.fel.nss.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * Controller for handling order-related requests.
 */
@RestController
@RequestMapping
@AllArgsConstructor
@Slf4j
public class OrderController {

    private final OrderFacade orderFacade;

    /**
     * Retrieves the orders for a specific user.
     *
     * @param userId the user ID
     * @return a ResponseEntity containing a list of OrderDto objects
     */
    @GetMapping("/users/{userId}/orders")
    @PreAuthorize("(principal == #userId) and hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<OrderDto>> userOrders(
            @PathVariable("userId") String userId
    ) {
        List<OrderDto> userOrders = orderFacade.getUserOrders(userId);
        return ResponseEntity.ok(userOrders);

    }

    /**
     * Places a new order.
     *
     * @param orderRequest   the order request object
     * @param authorization  the authorization header
     * @return a ResponseEntity containing the placed order
     */
    @PostMapping("/place")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Object> placeOrder(
            @Valid @RequestBody OrderDto orderRequest,
            @RequestHeader("Authorization") String authorization
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getPrincipal().toString();
        orderRequest.setUserId(Long.valueOf(userId));

        OrderDto placedOrder = orderFacade.placeOrder(orderRequest, authorization);
        return ResponseEntity.status(HttpStatus.CREATED).body(placedOrder);
    }
}
