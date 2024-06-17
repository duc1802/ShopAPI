package com.example.shopapi.Entity;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "cus_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "pro_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Product product;


}
