package com.example.repair.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "wcy_admin")
public class Admin {

    @Id
    @GeneratedValue
    private Integer id;  //编号

    @Column(length = 50)
    private String adminName;  //管理员用户名

    @Column(length = 50)
    private String password;  //密码

    @Column(length = 50)
    private Integer level;  //权限等级

}
