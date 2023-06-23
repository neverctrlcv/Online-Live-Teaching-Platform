package com.runhuo.live.service;

import com.runhuo.live.vo.R;
import com.runhuo.live.vo.request.RegisterReqVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:16
 */
public interface SysUserService {
    public R getCaptchaByRegister(String phone);

    public R getCaptchaByLogin(String phone);

    public R loginByCaptcha(String phone, String captcha);

    public R loginByPassword(String phone, String password);

    public R userRegister(RegisterReqVo registerReqVo);

    public R logout(String phone);
}
