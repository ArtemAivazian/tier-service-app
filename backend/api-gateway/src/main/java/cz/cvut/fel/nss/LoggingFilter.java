package cz.cvut.fel.nss;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * A logging filter for Spring Cloud Gateway that logs all request headers.
 * Implements {@link GlobalFilter} to be applied globally to all routes and
 * {@link Ordered} to define the order of execution.
 */
@Component
@Slf4j
public class LoggingFilter implements GlobalFilter, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    /**
     * Filters each request and logs its headers.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return a {@link Mono} that indicates filter completion
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Log each request header
        request.getHeaders().forEach((name, values) ->
                values.forEach(value -> logger.info("Header '{}': {}", name, value)));

        return chain.filter(exchange);
    }

    /**
     * Specifies the order of this filter.
     *
     * @return the order value
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
