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
public class LogT {
    private String logInfo;
    private Timestamp time;
}
