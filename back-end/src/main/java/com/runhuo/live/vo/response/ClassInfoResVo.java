package com.runhuo.live.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/21 15:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassInfoResVo {
    private String className;
    private String slogan;
    private String createTime;
    private String founder;
    private Long founderId;
    private String avatar;
    private String inviteCode;
}
