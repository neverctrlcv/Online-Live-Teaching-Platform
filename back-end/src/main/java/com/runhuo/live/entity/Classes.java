package com.runhuo.live.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classes {
    private Long classId;
    private String className;
    private String slogan;
    private Timestamp createTime;
    private Long founder;
    private String status;
    private String avatar;
    private String inviteCode;

}
