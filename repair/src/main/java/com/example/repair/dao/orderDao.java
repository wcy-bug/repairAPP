package com.example.repair.dao;

import com.example.repair.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface orderDao extends JpaRepository<Order,Long> {

    Order findOrderById(Long id);

    Page<Order> findByCode(String code, Pageable pageable);

}