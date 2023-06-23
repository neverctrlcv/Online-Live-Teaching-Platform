package com.runhuo.live.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/14 15:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusT {
    private Long id;
    private Timestamp logTime;
    private String logInfo;
}
