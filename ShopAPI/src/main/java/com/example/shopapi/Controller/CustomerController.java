package com.example.shopapi.Controller;

import com.example.shopapi.Entity.Customer;
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
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerController(CustomerRepository repository) {
        this.customerRepository = repository;
    }
    @GetMapping
    public ResponseEntity<Page<Customer>> getAll(Pageable pageable) {
        return ResponseEntity.ok(customerRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Integer id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalCustomer.get());
    }
    @PostMapping()
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer) {
        Customer saveCustomer = customerRepository.save(customer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saveCustomer.getId()).toUri();

        return ResponseEntity.created(location).body(saveCustomer);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Integer id, @Valid @RequestBody Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        customer.setId(optionalCustomer.get().getId());
        customerRepository.save(customer);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable Integer id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        customerRepository.delete(optionalCustomer.get());

        return ResponseEntity.noContent().build();
    }
}
