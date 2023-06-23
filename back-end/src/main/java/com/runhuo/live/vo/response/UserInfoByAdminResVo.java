package com.runhuo.live.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/19 16:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoByAdminResVo {
    private Long userId;
    private String nickname;
    private String signature;
    private String avatar;
    private String registerTime;
    private String identity;
}
