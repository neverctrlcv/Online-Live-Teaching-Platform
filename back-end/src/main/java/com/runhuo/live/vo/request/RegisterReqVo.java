package com.runhuo.live.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/17 15:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReqVo {
    private String phone;
    private String captcha;
    private String password;
}
