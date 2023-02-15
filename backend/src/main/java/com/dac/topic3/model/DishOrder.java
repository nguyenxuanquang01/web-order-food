package com.dac.topic3.model;

public class DishOrder {
    private Integer dish_id;
    private String name;
    private String price;
    private String id_store;
    private String quantity;

    private String image_link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDish_id() {
        return dish_id;
    }

    public void setDish_id(Integer dish_id) {
        this.dish_id = dish_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId_store() {
        return id_store;
    }

    public void setId_store(String id_store) {
        this.id_store = id_store;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
}
