package com.runhuo.live.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/20 16:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoInPlayResVo {
    private String avatar;
    private String nickname;
    private String signature;
}
