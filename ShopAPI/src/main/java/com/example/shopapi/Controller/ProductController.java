package com.example.shopapi.Controller;

import com.example.shopapi.Entity.Product;
import com.example.shopapi.Repository.CustomerRepository;
import com.example.shopapi.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class ProductController {
     private final ProductRepository productRepository;
     private final CustomerRepository customerRepository;
    @Autowired
    public ProductController(ProductRepository repository, CustomerRepository customerRepository) {
        this.productRepository = repository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/")
    public String productList(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products",products);
        return "product-list";
    }
}
