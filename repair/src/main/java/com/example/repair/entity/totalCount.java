package com.example.repair.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "wcy_total")
public class totalCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //编号

    private String date;  //日期

    private Integer totalSuccess;  //总成功单数

    private Integer totalError;  //总失效单数

    private Integer totalChargeBack;  //总退单数

    private Float totalMoney;  //总收钱数

    private Integer totalOrder;  //总订单数

    private Integer valid;   //失效标签


}
