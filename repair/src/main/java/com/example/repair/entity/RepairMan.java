package com.example.repair.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "wcy_repairMan")
public class RepairMan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;   //编号

    private String userName;   //姓名

    private String password;   //密码

    private String phoneNumber;   //电话号码

    private String homeAppliance;   //维修类型

    private Integer successNumber;   //成功单数

    private Integer valid;   //失效标签

    private String latitude;  //纬度

    private String longitude;  //经度

    @JsonIgnoreProperties("repairMan")
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "repairMan")
    private Set<Order> orders=new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHomeAppliance() {
        return homeAppliance;
    }

    public void setHomeAppliance(String homeAppliance) {
        this.homeAppliance = homeAppliance;
    }

    public Integer getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(Integer successNumber) {
        this.successNumber = successNumber;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
