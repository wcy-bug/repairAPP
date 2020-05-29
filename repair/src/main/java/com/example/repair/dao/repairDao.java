package com.example.repair.dao;

import com.example.repair.entity.Order;
import com.example.repair.entity.RepairMan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface repairDao extends JpaRepository<RepairMan,Long> {


}