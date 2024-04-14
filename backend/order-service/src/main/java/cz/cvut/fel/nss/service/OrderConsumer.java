package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.model.ProductUpdatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public ProductUpdatedEvent consumeProductUpdatedEvent(ProductUpdatedEvent event) {
        LOGGER.info(String.format("ProductUpdated event received in order-service =>  %s", event.toString()));
        //save data to db
        return event;
    }
}
