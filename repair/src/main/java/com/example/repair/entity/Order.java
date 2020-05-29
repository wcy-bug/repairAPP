package com.example.repair.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "wcy_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String user;

    private String phoneNumber;

    private String address;

    private String repairType;

    private String text;

    @Lob
    private byte[] pic;

    private Float probablyPrice;

    private String serverTime;

    private Float finalPrice;

    private String code;

    @JsonIgnoreProperties("orders")
    @ManyToOne
    @JoinColumn(name = "master_id")
    private RepairMan repairMan;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public Float getProbablyPrice() {
        return probablyPrice;
    }

    public void setProbablyPrice(Float probablyPrice) {
        this.probablyPrice = probablyPrice;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public Float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Float finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RepairMan getRepairMan() {
        return repairMan;
    }

    public void setRepairMan(RepairMan repairMan) {
        this.repairMan = repairMan;
    }
}
