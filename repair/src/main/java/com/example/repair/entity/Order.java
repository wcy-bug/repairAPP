package com.example.repair.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "wcy_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;  //编号

    private String orderNumber;  //订单编号

    private String user;  //用户名字

    private String phoneNumber;  //电话号码

    private String address;  //地址

    private String orderCreate;  //下单时间

    private String repairType;  //维修类型

    private String text;  //问题文字描述


    private String pic;  //问题图片上传(URL)

    private Float probablyPrice;  //大概价格

    private String serverTime;  //服务时间

    private Float finalPrice;  //最终价格

    private String code;  //状态

    private String evaluation;  //评价

    private String undoOder;  //撤单

    private String chargeBack;  //退单

    @JsonIgnoreProperties("orders")
    @ManyToOne
    @JoinColumn(name = "master_id")
    private RepairMan repairMan;  //外键，关联修理工



}
