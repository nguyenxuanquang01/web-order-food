package com.dac.topic3.repository;

import com.dac.topic3.entity.ListRestaurantToday;
import com.dac.topic3.model.ResToday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface ListResTodayRepository extends JpaRepository<ListRestaurantToday, Integer> {
//    List<ListRestaurantToday> getByDate(Date date);
    List<ListRestaurantToday> getByDate(Date date);
}
