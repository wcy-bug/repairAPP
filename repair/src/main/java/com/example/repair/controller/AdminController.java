package com.example.repair.controller;


import com.example.repair.Utils.MD5Utils;
import com.example.repair.entity.Admin;
import com.example.repair.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;


    /**管理员注册*/
    @RequestMapping(value = "/Register", method = RequestMethod.POST)
    public boolean Register(@RequestBody Admin admin) {
        admin.setLevel(1);
        admin.setPassword(MD5Utils.md5(admin.getPassword()));
        Admin current = userService.RegisterAdmin(admin);
        if (current != null) {
            return true;
        } else {
            return false;
        }

    }

    /**管理员登陆*/
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login(@RequestBody Admin admin) {
        Admin current = userService.loginAdmin(admin);
        if (current != null) {
            return true;
        } else {
            return false;
        }


    }

}
