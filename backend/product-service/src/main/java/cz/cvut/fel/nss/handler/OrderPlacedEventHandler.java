package cz.cvut.fel.nss.handler;

import cz.cvut.fel.nss.event.OrderFailedEvent;
import cz.cvut.fel.nss.exception.InsufficientAmountOfProductException;
import cz.cvut.fel.nss.exception.ProductNotFoundException;
import cz.cvut.fel.nss.shared.OrderedProduct;
import cz.cvut.fel.nss.event.OrderPlacedEvent;
import cz.cvut.fel.nss.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@KafkaListener(topics = "order-placed-topic-sync")
@Slf4j
@AllArgsConstructor
public class OrderPlacedEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private ProductRepository productRepository;
    @KafkaHandler
    public void handle(OrderPlacedEvent event) {
        LOGGER.info("Received a new event: " + event);
        List<OrderedProduct> orderedProducts = event.getOrderedProducts();
        orderedProducts.forEach(orderedProduct -> {
            var product = productRepository.findByName(orderedProduct.getName());
            if (product == null) {
                throw new ProductNotFoundException(
                        orderedProduct.getName() + " is not found.");
            }
            if (product.getQuantity() < orderedProduct.getQuantity()) {
                throw new InsufficientAmountOfProductException(
                        "Insufficient amount of product " + orderedProduct.getName());
            }
            product.setQuantity(product.getQuantity() - orderedProduct.getQuantity());
            productRepository.save(product);
        });

    }

}
