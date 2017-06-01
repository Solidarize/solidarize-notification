package com.solidarize.notification.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {

    private static final boolean DURABLE = true;

    @Bean
    @Qualifier("mailQueue")
    public Queue mailQueue(@Value("${rabbitmq.mail.queue}")
                                             String mailQueue) {
        return new Queue(mailQueue, DURABLE);
    }
}
