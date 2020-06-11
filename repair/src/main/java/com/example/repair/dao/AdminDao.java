package com.example.repair.dao;


import com.example.repair.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao extends JpaRepository<Admin, Integer> {

    Admin findByAdminNameAndPassword(String ownerName, String password);
}
