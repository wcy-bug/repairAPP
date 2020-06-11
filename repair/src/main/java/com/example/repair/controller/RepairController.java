package com.example.repair.controller;


import com.example.repair.Result.CodeMsg;
import com.example.repair.Result.Result;
import com.example.repair.Service.UserService;
import com.example.repair.Socket.WebSocketConfig;
import com.example.repair.Socket.WebSocketServer;
import com.example.repair.Utils.JedisUtil;
import com.example.repair.Utils.MD5Utils;
import com.example.repair.annotation.AuthToken;
import com.example.repair.entity.RepairMan;
import com.example.repair.entity.totalCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/repair")
public class RepairController {

    @Autowired
    private UserService userService;
    @Autowired
    private WebSocketServer webSocketServer;

    @GetMapping("/test")
    public List<RepairMan> test(){
        return userService.findAll();
    }

    /**修理工注册*/
    @AuthToken
    @PostMapping("/Register")
    public Result<RepairMan> Register(@RequestBody RepairMan repairMan){
        repairMan.setPassword(MD5Utils.md5(repairMan.getPassword()));
        repairMan.setValid(1);
        repairMan.setBalance(0f);
        RepairMan repairMan1=userService.findByPhoneNumber(repairMan.getPhoneNumber());
        if(repairMan1==null){  //判断用户是否唯一
            userService.Register(repairMan);
            return Result.success(CodeMsg.SUCCESS);
        }else{
            return Result.success(CodeMsg.ERROR);
        }
    }

    /**修理工登陆*/
    @AuthToken
    @PostMapping("/Login")
    public Result<RepairMan> Login(@RequestBody RepairMan repairMan) throws IOException {
       RepairMan repairMan1= userService.Login(repairMan);
        Jedis jedis=JedisUtil.getJedis();
        String token = UUID.randomUUID()+"";
        if(repairMan1!=null){
            System.out.println(token);
            jedis.setex(token,30*3600,repairMan1.getPhoneNumber());
            jedis.close();
            repairMan1.setToken(token);
            userService.Register(repairMan1);
          //  webSocketServer.sendMessage("dd");
            return Result.success(repairMan1);
        }else{
            return Result.success(CodeMsg.ERROR);
        }
    }


    /**修理工上传位置*/
    @PostMapping("/upload")
    public Result<RepairMan> upload(@RequestParam Long id,@RequestParam String longitude,@RequestParam String latitude){
        try{
        RepairMan repairMan=userService.findRepairById(id);
        repairMan.setLongitude(longitude);
        repairMan.setLatitude(latitude);
        userService.Register(repairMan);
        return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            return Result.success(CodeMsg.ERROR);
        }
    }

    /**查询所有有效修理工*/
    @GetMapping("/find/{page}/{size}")
    public Result<Page<RepairMan>> find(@PathVariable Integer page, @PathVariable Integer size){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<RepairMan> repairMans=userService.findTrue2(pageable);
        if(repairMans!=null){
            return Result.success(repairMans);
        }else {
            return  Result.error(CodeMsg.ERROR);
        }
    }

    /**失效某条记录*/
    @PostMapping("/lose")
    public Result<RepairMan> lose(@RequestParam Long id){
        try{
            RepairMan repairMan=userService.findRepairById(id);
            repairMan.setValid(0);
            userService.Register(repairMan);
            return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            return  Result.error(CodeMsg.ERROR);
        }
    }


    /**修改某条记录*/
    @PostMapping("/update/{id}")
    public Result<RepairMan> update(@PathVariable Long id,@RequestBody RepairMan repairMan){
        try{
            repairMan.setPassword(MD5Utils.md5(repairMan.getPassword()));
            userService.update(id,repairMan);
            return Result.success(CodeMsg.SUCCESS);
        }catch (Exception e){
            return  Result.error(CodeMsg.ERROR);
        }
    }

   /* @PostMapping("/tuisong")
    public void tuisong(@RequestParam String message) throws IOException {
        webSocketServer.sendMessage(message);
    }*/
}
