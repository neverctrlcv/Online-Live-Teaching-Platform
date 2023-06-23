package com.runhuo.live.enums;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:07
 */
public enum ResultCode {
    SEND_ERROR(801, "发送失败，请重试"),
    SEND_SUCCESS(200, "发送成功"),
    PASSWORD_ERROR(803, "密码错误"),
    LOGIN_SUCCESS(200, "登录成功"),
    CAPTCHA_ERROR(804, "验证码错误"),
    USER_NOT_EXIST(802, "用户未注册"),
    USER_EXIST(806, "该账号已注册"),
    USER_REGISTER_SUCCESS(200, "注册成功"),
    LOGOUT_SUCCESS(200, "退出成功"),
    UPDATE_SUCCESS(200, "更新成功！"),
    UPDATE_FAIL(807, "更新失败，请重试"),
    SEARCH_SUCCESS(200, "查询成功!"),
    USER_REGISTER_FAIL(805, "注册失败，请重试"),
    JOIN_CLASS_SUCCESS(200,"加入成功！"),
    JOIN_CLASS_FAIL(808,"加入失败，请重试！"),
    CREATE_CLASS_SUCCESS(200,"创建成功！"),
    CREATE_CLASS_FAIL(810,"创建失败，请重试！"),


    EXIT_CLASS_SUCCESS(200,"退出成功！"),
    EXIT_CLASS_FAIL(810,"退出失败，请重试！"),
    DISSOLVE_CLASS_SUCCESS(200,"解散成功！"),
    DISSOLVE_CLASS_FAIL(810,"解散失败，请重试！"),



    CLASS_NO_EXIST(809,"该班级不存在！");


    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
