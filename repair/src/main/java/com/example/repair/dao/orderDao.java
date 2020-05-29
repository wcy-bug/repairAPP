package com.example.repair.dao;

import com.example.repair.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderDao extends JpaRepository<Order,Long> {


}