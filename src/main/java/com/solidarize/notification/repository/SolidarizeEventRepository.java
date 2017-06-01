package com.solidarize.notification.repository;

import com.solidarize.notification.service.SolidarizeEventsWrapper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolidarizeEventRepository extends MongoRepository<SolidarizeEventsWrapper, Integer> {
    List<SolidarizeEventsWrapper> findAllByOrderByTimestamp();
}
