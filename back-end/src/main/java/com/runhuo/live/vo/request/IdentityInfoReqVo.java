package com.runhuo.live.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/18 13:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentityInfoReqVo {
    private String jobId;
    private String name;
    private Integer identity;
}
