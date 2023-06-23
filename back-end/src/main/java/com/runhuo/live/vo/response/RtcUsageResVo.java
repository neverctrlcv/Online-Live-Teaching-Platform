package com.runhuo.live.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/19 15:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RtcUsageResVo {
    private Integer rtcUsage;
    private String useDate;
}
