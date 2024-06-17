package com.example.shopapi.Controller;


import com.example.shopapi.Entity.Product;
import com.example.shopapi.Repository.OrderRepository;
import com.example.shopapi.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, OrderRepository orderRepository){
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/")
    public ResponseEntity<Product> create(@Valid @RequestBody Product product){
        Product savedProduct = productRepository.save(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProduct.getId()).toUri();

        return ResponseEntity.created(location).body(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product product){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        product.setId(optionalProduct.get().getId());
        productRepository.save(product);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delte(@PathVariable Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        deleteProductInTransaction(optionalProduct.get());

        return ResponseEntity.noContent().build();
    }

    @Transactional
    public void deleteProductInTransaction(Product product){
        orderRepository.deleteByProductId(product.getId());
        productRepository.delete(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalProduct.get());
    }

    @GetMapping("/")
    public ResponseEntity<Page<Product>> getAll(Pageable pageable) {
        return ResponseEntity.ok(productRepository.findAll(pageable));
    }
}