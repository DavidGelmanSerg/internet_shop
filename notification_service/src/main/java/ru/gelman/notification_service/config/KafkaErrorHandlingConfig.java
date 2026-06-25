package ru.gelman.notification_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaErrorHandlingConfig {
    private static final Logger log = LoggerFactory.getLogger(KafkaErrorHandlingConfig.class);

    @Bean
    public CommonErrorHandler kafkaErrorHandler() {
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                (record, exception) -> log.error(
                        "Skipping unprocessable record from topic {} partition {} offset {}",
                        record.topic(), record.partition(), record.offset(), exception),
                new FixedBackOff(1000L, 3L)
        );
        return errorHandler;
    }
}