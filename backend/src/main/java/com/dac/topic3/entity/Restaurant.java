package com.dac.topic3.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String shop_code;
    @Column(nullable = false,length = 512)
    private String name;
    private String link;
    private Integer status;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private Set<Dish> dishes = new HashSet<>();
    @OneToMany(mappedBy = "restaurant")
    private Set<ListRestaurantToday> listRestaurantToday = new HashSet<>();

    public Restaurant(){}

    public Restaurant(Integer id) {
        this.id = id;
    }

    public Restaurant(Integer id, String shop_code, String name, String link) {
        this.id = id;
        this.shop_code = shop_code;
        this.name = name;
        this.link = link;

    }

    public Restaurant(String shop_code, String name, String link, Integer status) {
        this.shop_code = shop_code;
        this.name = name;
        this.link = link;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public String getShop_code() {
        return shop_code;
    }

    public void setShop_code(String shop_code) {
        this.shop_code = shop_code;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public Set<ListRestaurantToday> getListRestaurantTodays() {
        return listRestaurantToday;
    }

    public void setListRestaurantTodays(Set<ListRestaurantToday> listRestaurantToday) {
        this.listRestaurantToday = listRestaurantToday;
    }

}
