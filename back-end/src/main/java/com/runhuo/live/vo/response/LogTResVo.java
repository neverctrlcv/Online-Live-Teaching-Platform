package com.runhuo.live.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/19 15:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogTResVo {
    private String logInfo;
    private String time;
}
