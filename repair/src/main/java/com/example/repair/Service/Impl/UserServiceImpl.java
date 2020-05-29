package com.example.repair.Service.Impl;


import com.example.repair.Service.UserService;
import com.example.repair.dao.orderDao;
import com.example.repair.dao.repairDao;
import com.example.repair.entity.RepairMan;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    public List<RepairMan> findAll() {
        return this.repairDao.findAll();
    }
}
