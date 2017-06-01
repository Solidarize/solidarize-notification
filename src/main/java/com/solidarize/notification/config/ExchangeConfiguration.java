package com.solidarize.notification.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfiguration {

    private static final boolean DURABLE = true;
    private static final boolean AUTO_DELETE = false;

    @Bean
    @Qualifier("mailExchange")
    public FanoutExchange mailExchange(@Value("${rabbitmq.mail.exchange}") String mailExchange) {
        return new FanoutExchange(mailExchange, DURABLE, AUTO_DELETE);
    }
}
