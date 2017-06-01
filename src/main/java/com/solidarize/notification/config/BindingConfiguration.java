package com.solidarize.notification.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
public class BindingConfiguration {

    @Bean
    @Qualifier("mailBinding")
    public Binding mailBinding(
            @Qualifier("mailQueue") Queue mailQueue,
            @Qualifier("mailExchange")
                    FanoutExchange mailExchange) {
        return bind(mailQueue).to(mailExchange);
    }
}
