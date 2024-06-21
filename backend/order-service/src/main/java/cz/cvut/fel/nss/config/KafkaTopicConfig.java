package cz.cvut.fel.nss.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration class for Kafka topics.
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Creates a Kafka topic for synchronous order placement.
     *
     * @return the new topic
     */
    @Bean
    public NewTopic orderPlacedTopicSync(){
        return TopicBuilder
                .name("order-placed-topic-sync")
                .partitions(1)
                .build();
    }

    /**
     * Creates a Kafka topic for asynchronous order placement.
     *
     * @return the new topic
     */
    @Bean
    public NewTopic orderPlacedTopicAsync(){
        return TopicBuilder
                .name("order-placed-topic-async")
                .partitions(1)
                .build();
    }
}
