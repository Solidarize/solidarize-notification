package com.solidarize.notification.service;


import com.solidarize.notification.client.SolidarizeClient;
import com.solidarize.notification.client.SolidarizeEvent;
import com.solidarize.notification.repository.SolidarizeEventRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SolidarizeEventsService {

    private static final String NO_ROUTING_KEY = "";
    public static final String MAIL = "mail";
    private SolidarizeClient client;
    private SolidarizeEventRepository repository;
    private RabbitTemplate rabbitTemplate;
    private String fetchMailExchange;
    private ConcurrentHashMap cache;

    @Autowired
    public SolidarizeEventsService(SolidarizeClient client,
                                   SolidarizeEventRepository repository,
                                   RabbitTemplate rabbitTemplate,
                                   @Value("${rabbitmq.fetchmail.exchange}") String fetchMailExchange,
                                   ConcurrentHashMap<String, Integer> cache) {
        this.client = client;
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.fetchMailExchange = fetchMailExchange;
        this.cache = cache;
    }

    public void saveEvents() {
        if ((Integer) cache.get(MAIL) == 0) {
            List<SolidarizeEvent> solidarizeEvents = client.fetchEvents();
            repository.save(new SolidarizeEventsWrapper(solidarizeEvents));
            FetchMailDTO fetchMailDTO = new FetchMailDTO("true");
            rabbitTemplate.convertAndSend(fetchMailExchange, NO_ROUTING_KEY, fetchMailDTO);
            increaseCache();
        } else {
            increaseCache();
        }
    }

    private void increaseCache() {
        if ((Integer) cache.get(MAIL) == 2) {
            cache.put(MAIL, 0);
        } else {
            Integer cache = (Integer) this.cache.get(MAIL);
            cache = cache + 1;
            this.cache.put(MAIL, cache);
        }
    }

    public List<SolidarizeEvent> getLatestEvents() {
        return repository.findAllByOrderByTimestampDesc().get(0).getSolidarizeEvents();
    }

    static class FetchMailDTO {
        private String isToFetchMail;

        public FetchMailDTO(String isToFetchMail) {
            this.isToFetchMail = isToFetchMail;
        }

        public String getIsToFetchMail() {
            return isToFetchMail;
        }
    }
}
