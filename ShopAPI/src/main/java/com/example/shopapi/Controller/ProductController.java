package com.example.shopapi.Controller;

import com.example.shopapi.Entity.Product;
import com.example.shopapi.Repository.CustomerRepository;
import com.example.shopapi.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
     private final ProductRepository productRepository;
     private final CustomerRepository customerRepository;
    @Autowired
    public ProductController(ProductRepository repository, CustomerRepository customerRepository) {
        this.productRepository = repository;
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAll(Pageable pageable) {
        return ResponseEntity.ok(productRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id.longValue());
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalProduct.get());
    }

}
