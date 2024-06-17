package com.example.shopapi.Repository;

import com.example.shopapi.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
