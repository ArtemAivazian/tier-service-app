package cz.cvut.fel.nss.handler;

import cz.cvut.fel.nss.exception.InsufficientAmountOfProductException;
import cz.cvut.fel.nss.exception.ProductNotFoundException;
import cz.cvut.fel.nss.shared.OrderedProductDto;
import cz.cvut.fel.nss.event.OrderPlacedEvent;
import cz.cvut.fel.nss.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Event handler for processing OrderPlacedEvent.
 */
@Component
@KafkaListener(topics = "order-placed-topic-sync")
@Slf4j
@AllArgsConstructor
public class OrderPlacedEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private ProductRepository productRepository;

    /**
     * Handles the OrderPlacedEvent.
     *
     * @param event the order placed event
     */
    @KafkaHandler
    public void handle(OrderPlacedEvent event) {
        LOGGER.info("Received a new event: " + event);
        List<OrderedProductDto> orderedProductDtos = event.getOrderedProducts();
        orderedProductDtos.forEach(orderedProductDto -> {
            var product = productRepository.findByName(orderedProductDto.getName());
            if (product == null) {
                throw new ProductNotFoundException(
                        orderedProductDto.getName() + " is not found.");
            }
            if (product.getQuantity() < orderedProductDto.getQuantity()) {
                throw new InsufficientAmountOfProductException(
                        "Insufficient amount of product " + orderedProductDto.getName());
            }
            product.setQuantity(product.getQuantity() - orderedProductDto.getQuantity());
            productRepository.save(product);
        });

    }

}
