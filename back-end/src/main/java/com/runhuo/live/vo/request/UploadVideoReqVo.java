package com.runhuo.live.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/18 15:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadVideoReqVo {
    private String desc;
    private String videoLength;
}
