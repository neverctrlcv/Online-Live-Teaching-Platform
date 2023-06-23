package com.runhuo.live.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/17 15:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResVo {
    private Integer identity;
    private Long userId;
    private Integer age;
    private String sex;
    private String nickname;
    private String signature;
    private String phone;
    private String avatar;
    private String jobId;
    private String registerTime;
    private String name;
    private String token;
}
