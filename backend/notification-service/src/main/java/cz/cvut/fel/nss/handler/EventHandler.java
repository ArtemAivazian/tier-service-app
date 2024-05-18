package cz.cvut.fel.nss.handler;

import cz.cvut.fel.nss.event.OrderPlacedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class EventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @KafkaHandler
    @KafkaListener(topics = "order-placed-topic-async", groupId = "order-placed-notifications")
    public void handleOrderPlacedEvent(OrderPlacedEvent event) {
        LOGGER.info("Received a new event: " + event);
        LOGGER.info("SENDING EMAIL TO ...");
    }

}
