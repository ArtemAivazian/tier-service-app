package cz.cvut.fel.nss.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic orderPlacedTopicSync(){
        return TopicBuilder
                .name("order-placed-topic-sync")
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic orderPlacedTopicAsync(){
        return TopicBuilder
                .name("order-placed-topic-async")
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic orderFailedTopic(){
        return TopicBuilder
                .name("order-failed-topic")
                .partitions(1)
                .build();
    }



}
