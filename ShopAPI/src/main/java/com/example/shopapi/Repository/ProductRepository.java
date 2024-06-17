package com.example.shopapi.Repository;

import com.example.shopapi.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
