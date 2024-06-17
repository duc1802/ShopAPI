package com.example.shopapi.repository;

import com.example.shopapi.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Page<Order> findByProductId(Integer ProductId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Order b WHERE b.product.id = ?1")
    void deleteByProductId(Integer productId);

    Page<Order> findByCustomerId(Integer CustomerId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Order b WHERE b.customer.id = ?1")
    void deleteByCustomerId(Integer customerId);
}
