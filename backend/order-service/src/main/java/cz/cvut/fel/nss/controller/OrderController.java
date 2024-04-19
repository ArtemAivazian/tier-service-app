package cz.cvut.fel.nss.controller;

import java.lang.reflect.Type;
import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.model.*;
import cz.cvut.fel.nss.service.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

//    @GetMapping("/users/{id}/orders")
//    public List<OrderResponse> userOrders(
//            @PathVariable String id
//    ) {
//
//        List<OrderResponse> returnValue = new ArrayList<>();
//
//        List<Order> orders = orderService.getOrders(id);
//
//        if(orders == null || orders.isEmpty())
//        {
//            return returnValue;
//        }
//
//        Type listType = new TypeToken<List<OrderResponse>>(){}.getType();
//
//        returnValue = new ModelMapper().map(orders, listType);
//        return returnValue;
//    }
    @GetMapping("/users/{userId}/orders")
    @PreAuthorize("(principal == #userId) and hasAnyRole('ADMIN', 'USER')")
    public List<OrderResponse> userOrders(
            @PathVariable("userId") String userId
    ) {
        List<OrderResponse> returnValue = new ArrayList<>();

        List<OrderResponse> orders = orderService.getUserOrders(userId);

        if(orders == null || orders.isEmpty())
        {
            return returnValue;
        }

        Type listType = new TypeToken<List<OrderResponse>>(){}.getType();

        returnValue = new ModelMapper().map(orders, listType);
        return returnValue;

    }

    @PostMapping("/place")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody OrderRequest orderRequest
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getPrincipal().toString();
        orderRequest.setUserId(Long.valueOf(userId));
//        String currentUsername = authentication.getName();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = modelMapper.map(orderRequest, OrderDto.class);
        OrderDto placedOrder = orderService.placeOrder(orderDto);
        OrderResponse orderResponse = modelMapper.map(placedOrder, OrderResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

//    @PostMapping("/init")
//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//    public ResponseEntity<OrderInitResponse> initOrder(
//            @RequestBody OrderInitRequest request
//            ) {
//        OrderInitResponse response = orderService.initOrder(request);
//
//        return ResponseEntity.ok().body(response);
//    }

    //TO-DO: Place order



}
