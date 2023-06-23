package com.runhuo.live.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/21 15:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassTResVo {
    private Long id;
    private String date;
    private String startTime;
    private String endTime;
}
