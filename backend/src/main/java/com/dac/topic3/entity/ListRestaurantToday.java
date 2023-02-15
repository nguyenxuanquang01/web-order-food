package com.dac.topic3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "ListResToday")
public class ListRestaurantToday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "res_id",referencedColumnName = "id")
    private Restaurant restaurant;
    private Date date;

    public ListRestaurantToday(){}
    public ListRestaurantToday(Restaurant restaurant, Date date) {
        this.restaurant = restaurant;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
