package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.dto.OrderDto;
import cz.cvut.fel.nss.model.*;
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


@RestController
@RequestMapping
@AllArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper mapper;

    @GetMapping("/users/{userId}/orders")
    @PreAuthorize("(principal == #userId) and hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<OrderResponse>> userOrders(
            @PathVariable("userId") String userId
    ) {
        List<OrderDto> orderDtos = orderService.getUserOrders(userId);

        List<OrderResponse> response = orderDtos.stream()
                .map(orderDto -> mapper.map(orderDto, OrderResponse.class))
                .toList();

        return ResponseEntity.ok(response);

    }

    @PostMapping("/place")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Object> placeOrder(
            @Valid @RequestBody OrderRequest orderRequest,
            @RequestHeader("Authorization") String authorization
    ) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getPrincipal().toString();
        orderRequest.setUserId(Long.valueOf(userId));


        OrderDto orderDto = mapper.map(orderRequest, OrderDto.class);
        OrderDto placedOrder;
        placedOrder = orderService.placeOrder(orderDto, authorization);

        OrderResponse orderResponse = mapper.map(placedOrder, OrderResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }
}
