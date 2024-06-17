package com.example.shopapi.Controller;

import com.example.shopapi.Entity.Customer;
import com.example.shopapi.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import com.example.shopapi.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
@RestController
@RequestMapping("/api/vi/customers")
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, OrderRepository orderRepository){
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/")
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer){
        Customer savedCustomer = customerRepository.save(customer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCustomer.getId()).toUri();

        return ResponseEntity.created(location).body(savedCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @Valid @RequestBody Customer customer){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        customer.setId(optionalCustomer.get().getId());
        customerRepository.save(customer);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable Long id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(!optionalCustomer.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        deleteCustomerInTransaction(optionalCustomer.get());

        return ResponseEntity.noContent().build();
    }

    @Transactional
    public void deleteCustomerInTransaction(Customer customer){
        orderRepository.deleteByCustomerId(customer.getId());
        customerRepository.delete(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalCustomer.get());
    }

    @GetMapping("/")
    public ResponseEntity<Page<Customer>> getAll(Pageable pageable){
        return ResponseEntity.ok(customerRepository.findAll(pageable));
    }

}