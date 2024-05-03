package cz.cvut.fel.nss.config;

import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.dto.OrderDto;
import cz.cvut.fel.nss.service.OrderService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableCaching
@Slf4j
@AllArgsConstructor
public class CacheConfig {
//    private final CacheManager cacheManager;
//    private final OrderService orderService;
//    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);
//    private final ModelMapper mapper;
//
//    /**
//     * Preloading cache at the start of the app
//     */
//    @PostConstruct
//    public void preloadCache() {
//        Cache cache = cacheManager.getCache("orderServiceCache");
//        if (cache != null) {
//            LOGGER.info("*********** Initializing cache");
//
//            List<OrderDto> orderDtos = orderService.getAllOrders();
//
//            orderDtos.stream()
//                    .map(orderDto -> mapper.map(orderDto, Order.class))
//                    .forEach(
//                            order -> cache.put(order.getOrderId(), order)
//                    );
//        }
//    }
}
