package com.example.repair.controller;


import com.example.repair.Service.UserService;
import com.example.repair.entity.RepairMan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/repair")
public class RepairController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public List<RepairMan> test(){
        return userService.findAll();
    }

}
