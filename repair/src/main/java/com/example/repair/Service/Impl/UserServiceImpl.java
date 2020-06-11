package com.example.repair.Service.Impl;


import com.example.repair.Service.UserService;
import com.example.repair.Utils.MD5Utils;
import com.example.repair.dao.AdminDao;
import com.example.repair.dao.orderDao;
import com.example.repair.dao.repairDao;
import com.example.repair.dao.totalDao;
import com.example.repair.entity.Admin;
import com.example.repair.entity.Order;
import com.example.repair.entity.RepairMan;
import com.example.repair.entity.totalCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private orderDao orderDao;
    @Autowired
    private repairDao repairDao;
    @Autowired
    private totalDao totalDao;
    @Autowired
    private AdminDao adminDao;


    @Override
    public List<RepairMan> findAll() {
        return this.repairDao.findAll();
    }

    @Override
    public Order findById(Long id) {
        return this.orderDao.findOrderById(id);
    }

    @Override
    public Order insert(Order order) {
        return this.orderDao.save(order);
    }

    @Override
    public Order judgeFalse(Long id) {
       Order order=this.orderDao.findOrderById(id);
       order.setCode("0");
        return order;
    }

    @Override
    public Order judgeTrue(Long id) {
        Order order=this.orderDao.findOrderById(id);
        order.setCode("2");
        return order;
    }

    @Override
    public Page<Order> findByCode(String code,Pageable pageable) {
        return this.orderDao.findByCode(code,pageable);
    }

    @Override
    public RepairMan Register(RepairMan repairMan) {
        return this.repairDao.save(repairMan);
    }

    @Override
    public RepairMan Login(RepairMan repairMan) {
        return this.repairDao.findRepairManByPhoneNumberAndPassword(repairMan.getPhoneNumber(), MD5Utils.md5(repairMan.getPassword()));
    }

    @Override
    public RepairMan findRepairById(Long id) {
        return this.repairDao.findRepairManById(id);
    }

    @Override
    public RepairMan findByPhoneNumber(String phoneNumber) {
        return this.repairDao.findRepairManByPhoneNumber(phoneNumber);
    }

    @Override
    public totalCount insert(totalCount totalCount) {
        return totalDao.save(totalCount);
    }

    @Override
    public totalCount findTotalById(Long id) {
        return this.totalDao.findTotalCountById(id);
    }

    @Override
    public Page<totalCount> findTrue(Pageable pageable) {
        return this.totalDao.findByQuery(pageable);
    }

    @Override
    public Page<RepairMan> findTrue2(Pageable pageable) {
        return this.repairDao.findByQuery(pageable);
    }

    public Admin loginAdmin(Admin admin) {
        return this.adminDao.findByAdminNameAndPassword(admin.getAdminName(), MD5Utils.md5(admin.getPassword()));
    }

    @Override
    public RepairMan update(Long id,RepairMan repairMan) {
        repairMan.setId(id);
        repairMan.setValid(1);
        return this.repairDao.save(repairMan);
    }

    @Override
    public totalCount update(Long id,totalCount totalCount) {
        totalCount.setId(id);
        totalCount.setValid(1);
        return this.totalDao.save(totalCount);
    }

    @Override
    public Admin RegisterAdmin(Admin admin) {
        return this.adminDao.save(admin);
    }
}
