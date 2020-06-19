package com.example.repair.Service;

import com.example.repair.entity.Admin;
import com.example.repair.entity.Order;
import com.example.repair.entity.RepairMan;
import com.example.repair.entity.totalCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface UserService {

    List<RepairMan> findAll();

    Order findById(Long id);

    Order insert(Order order);

    Order judgeFalse(Long id);

    Order judgeTrue(Long id);

    Order findByOrderNumber(String orderNumber);

    Order findByUser(String userName);

    List<Order> findByRepairId(Long id);

    Page<Order> findByCode(String code,Pageable pageable);

    RepairMan Register(RepairMan repairMan);

    RepairMan Login(RepairMan repairMan);

    RepairMan findRepairById(Long id);

    RepairMan findByPhoneNumber(String phoneNumber);

    RepairMan findByRepair(String RepairName);

    totalCount insert(totalCount totalCount);

    totalCount findTotalById(Long id);

    List<totalCount> findByDate(String date);

    Page<totalCount> findTrue(Pageable pageable);

    Page<RepairMan> findTrue2(Pageable pageable);

    Admin RegisterAdmin(Admin admin);

    Admin loginAdmin(Admin admin);

    RepairMan update(Long id,RepairMan repairMan);

    totalCount update(Long id,totalCount totalCount);






}
