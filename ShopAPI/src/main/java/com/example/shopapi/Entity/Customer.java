package com.example.shopapi.Entity;

import jakarta.persistence.*;

import java.util.List;


@Entity(name="Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long   id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "Order", cascade = CascadeType.ALL)
    private List<Order> orders;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}