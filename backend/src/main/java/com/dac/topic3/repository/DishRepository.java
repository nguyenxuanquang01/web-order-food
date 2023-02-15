package com.dac.topic3.repository;

import com.dac.topic3.entity.Dish;
import com.dac.topic3.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish,Integer> {

    @Query("select d.id,d.name,d.price, d.image_link , r.id, r.name, r.link from Dish d inner join d.restaurant r on d.restaurant.id = ?1")
    List<Object> findAllInforDish (Integer id);
    List<Dish> findAll();
}
