package com.runhuo.live.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/17 17:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoReqVo {
    private String nickname;
    private Integer age;
    private String sex;
    private String signature;
}
