package com.solidarize.notification.client;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(url = "https://solidarize-dev.herokuapp.com", name = "SolidarizeClient")
@Component
public interface SolidarizeClient {

    @RequestMapping(value = "/events?offset=4&order=desc", method = GET)
    List<SolidarizeEvent> fetchEvents();


}
