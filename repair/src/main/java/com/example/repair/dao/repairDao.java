package com.example.repair.dao;

import com.example.repair.entity.Order;
import com.example.repair.entity.RepairMan;
import com.example.repair.entity.totalCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface repairDao extends JpaRepository<RepairMan,Long> {

    RepairMan findRepairManById(Long id);

    RepairMan findRepairManByPhoneNumber(String phoneNumber);

    RepairMan findRepairManByUserName(String userName);

    RepairMan findRepairManByPhoneNumberAndPassword(String phoneNumber,String password);

    @Query("Select r from RepairMan r where r.valid=1")
    Page<RepairMan> findByQuery(Pageable pageable);
}