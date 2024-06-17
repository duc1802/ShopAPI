package com.example.shopapi.Repository;


import com.example.shopapi.Entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {
    Page<Order> findByCustomerId(Long CustomerId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Orders b WHERE b.product.id = ?1")
    void deleteByProductId(long productId);


    @Modifying
    @Transactional
    @Query("DELETE FROM Orders b WHERE b.customer.id = ?1")
    void deleteByCustomerId(long customerId);

    Optional<Order> findById(Integer id);

}
