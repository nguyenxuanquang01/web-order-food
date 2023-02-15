package com.dac.topic3.repository;

import com.dac.topic3.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
    List<Restaurant> findRestaurantByStatus(Integer status);
}
