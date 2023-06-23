package com.runhuo.live.vo;

import com.runhuo.live.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R {
    private Integer code;
    private String message;
    private Object data;

    public static R success(ResultCode resultCode,Object data) {
        return new R(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static R fail(ResultCode resultCode) {
        return new R(resultCode.getCode(), resultCode.getMessage(), null);
    }
}
