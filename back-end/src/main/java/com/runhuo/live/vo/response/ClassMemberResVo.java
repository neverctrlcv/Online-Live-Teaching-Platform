package com.runhuo.live.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/21 15:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassMemberResVo {
    private String name;
    private String jobId;
    private String identity;
    private String joinTime;
}
