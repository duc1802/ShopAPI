package com.example.shopapi.Controller;

import com.example.shopapi.Entity.Customer;
import com.example.shopapi.Entity.Order;
import com.example.shopapi.Entity.Product;
import com.example.shopapi.Repository.CustomerRepository;
import com.example.shopapi.Repository.OrderRepository;
import com.example.shopapi.Repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Long productId = order.getProduct().getId();
        Long customerId = order.getCustomer().getId();
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
//    public ResponseEntity<Page<Order>> getAll(Pageable pageable){
//        return ResponseEntity.ok(orderRepository.findAll(pageable));
//
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