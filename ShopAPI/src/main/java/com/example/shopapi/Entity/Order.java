package com.example.shopapi.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Entity(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date order_date;
    private double total_price;
    private int status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cus_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Customer customer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pro_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Product product;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
