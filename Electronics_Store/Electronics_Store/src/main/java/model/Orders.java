package model;

import model.builder.OrderBuilder;

import java.time.LocalDate;

public class Orders extends OrderBuilder {
    private Long id;
    private Long user_id;
    private Long electro_id;
    private int quantity;
    private int price;
    private LocalDate date_created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public Long setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getElectro_id() {
        return electro_id;
    }

    public void setElectro_id(Long electro_id) {
        this.electro_id = electro_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate_created() {
        return date_created;
    }

    public void setDate_created(LocalDate date_created) {
        this.date_created = date_created;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}