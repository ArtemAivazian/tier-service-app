package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.model.ProductUpdateRequest;
import cz.cvut.fel.nss.model.ProductUpdatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProductProducer {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ProductProducer.class);

    private NewTopic topic;
    private KafkaTemplate<String, ProductUpdatedEvent> kafkaTemplate;

    public void sendMessage(ProductUpdatedEvent event) {
//        LOGGER.info(String.format("ProductUpdated event => %s", event.toString()));

        //create message
        Message<ProductUpdatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();

        //send message
        kafkaTemplate.send(message);
    }


}
