package cz.cvut.fel.nss.config;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@AllArgsConstructor
public class KafkaTopicConfig {
    private Environment env;
    @Bean
    public NewTopic orderPlacedTopic(){
        return TopicBuilder
                .name(env.getProperty("spring.kafka.topic.name"))
                .build();
    }

}
