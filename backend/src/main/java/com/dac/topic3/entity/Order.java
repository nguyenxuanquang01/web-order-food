package com.dac.topic3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "res_id",referencedColumnName = "id")
    private Restaurant restaurant;
    private String dish;
    private int quantity;
    private String status;
    private Date orderDate;
    private Time orderTime;
    private double price;
    private double finalPrice;
    private Integer dish_id;
    private String image_link;

    public Order(){}

    public Order(User user, Restaurant restaurant, String dish, int quantity, String status, Date orderDate, Time orderTime, double price, double finalPrice) {
        this.user = user;
        this.restaurant = restaurant;
        this.dish = dish;
        this.quantity = quantity;
        this.status = status;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.price = price;
        this.finalPrice = finalPrice;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public Integer getDish_id() {
        return dish_id;
    }

    public void setDish_id(Integer dish_id) {
        this.dish_id = dish_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Time getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Time orderTime) {
        this.orderTime = orderTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
