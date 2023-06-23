package com.runhuo.live.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userId;
    private Integer age;
    private String sex;
    private String nickname;
    private String signature;
    private String phone;
    private String password;
    private String avatar;
    private String jobId;
    private Timestamp registerTime;
    private String status;
    private String name;
    private Integer identity;

}
