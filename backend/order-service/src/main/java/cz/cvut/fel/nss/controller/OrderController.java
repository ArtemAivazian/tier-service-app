package cz.cvut.fel.nss.controller;

import java.lang.reflect.Type;
import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.model.OrderInitRequest;
import cz.cvut.fel.nss.model.OrderInitResponse;
import cz.cvut.fel.nss.model.OrderResponse;
import cz.cvut.fel.nss.service.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
//    @PreAuthorize("(principal == #userId) and hasAnyRole('ADMIN', 'USER')")
    public List<OrderResponse> userOrders(
            @PathVariable("userId") String userId,
            @RequestHeader("Authorization") String authorization
    ) {
        List<OrderResponse> returnValue = new ArrayList<>();

        List<OrderResponse> orders = orderService.getUserOrders(userId, authorization);

        if(orders == null || orders.isEmpty())
        {
            return returnValue;
        }

        Type listType = new TypeToken<List<OrderResponse>>(){}.getType();

        returnValue = new ModelMapper().map(orders, listType);
        return returnValue;

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
