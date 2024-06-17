package com.example.shopapi.controller;

import com.example.shopapi.entity.Customer;
import com.example.shopapi.entity.Order;
import com.example.shopapi.entity.Product;
import com.example.shopapi.repository.CustomerRepository;
import com.example.shopapi.repository.OrderRepository;
import com.example.shopapi.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository){
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody @Valid Order order) {
        Integer productId = order.getProduct().getId();
        Integer customerId = order.getCustomer().getId();
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (!optionalCustomer.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        order.setProduct(optionalProduct.get());
        order.setCustomer(optionalCustomer.get());
        Order savedOrder = orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Order> delete(@PathVariable Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(!optionalOrder.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        orderRepository.delete(optionalOrder.get());
        return ResponseEntity.noContent().build();
    }

//    @GetMapping
//    public ResponseEntity<Page<Order>> getAll(){
//        return ResponseEntity.ok(orderRepository.findAll());
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Integer id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(optionalOrder.get());
    }


}
