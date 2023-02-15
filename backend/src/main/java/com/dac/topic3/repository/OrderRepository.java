package com.dac.topic3.repository;

import com.dac.topic3.entity.Order;
import com.dac.topic3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select o.image_link, o.dish, r.name, r.link, sum(o.quantity), sum(o.quantity*o.price) from Order o inner join o.restaurant r where o.orderDate = ?1 group by o.dish_id")
    List<Object> getAllGroupDish(Date date);

    @Query("select o.id,u.name, o.dish, r.name, r.link, o.quantity, o.price,o.status, o.finalPrice  from Order o inner join o.user u inner join o.restaurant r where o.orderDate = ?1")
    List<Object> getAllOrderedByEmpls(Date date);

    @Query("select sum(o.price*o.quantity) from Order o where o.orderDate = ?1")
    Double getTotalPrice(Date date);

    List<Order> getAllByOrderDate(Date date);

    @Query("select o.id, o.dish, o.finalPrice, o.image_link, r.id, r.name, r.link, o.quantity, o.status, o.price from Order o inner join o.user u inner join o.restaurant r where o.orderDate = ?1 and u.id = ?2")
    List<Object> getAllDishOrderWithOrderDateAndUser(Date date, long id);

    List<Order> getByUserAndOrderDate(User user, Date date);

    @Query("select o.id,o.orderDate, o.image_link, o.dish, r.name, r.link, o.quantity, o.finalPrice, o.status from Order o inner join o.user u inner join o.restaurant r where u.id = ?1 and o.status = ?2")
    List<Object> getAllInfoOrderByUserAndStatus(long id,String status);

    @Query("select o.id, o.orderDate, o.image_link, o.dish, r.name, r.link, o.quantity, o.finalPrice, o.status from Order o inner join o.user u inner join o.restaurant r where u.id = ?1")
    List<Object> getAllInfoOrderByUser(long id);

    @Query("select o.id, u.name,o.dish, r.name, r.link, o.orderDate, o.quantity,o.finalPrice, o.status from Order o inner join o.user u inner join o.restaurant r where o.status = ?1")
    List<Object> getAllInfoOrderByStatus(String status);

    @Query("select o.id, u.name,o.dish, r.name, r.link, o.orderDate, o.quantity,o.finalPrice, o.status from Order o inner join o.user u inner join o.restaurant r where u.name like %?1%")
    List<Object> getAllInfoOrderByName(String name);

    @Query("select o.id, u.name,o.dish, r.name, r.link, o.orderDate, o.quantity,o.finalPrice, o.status from Order o inner join o.user u inner join o.restaurant r")
    List<Object> getAllInfoOrder();

    @Query("select o.id, u.name,o.dish, r.name, r.link, o.orderDate, o.quantity,o.finalPrice, o.status from Order o inner join o.user u inner join o.restaurant r where u.name like %?1% and o.status = ?2")
    List<Object> getAllInfoOrderByNameAndStatus(String name,String status);
}
