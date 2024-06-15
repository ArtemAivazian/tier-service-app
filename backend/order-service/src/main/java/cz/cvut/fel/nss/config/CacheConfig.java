package cz.cvut.fel.nss.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

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
