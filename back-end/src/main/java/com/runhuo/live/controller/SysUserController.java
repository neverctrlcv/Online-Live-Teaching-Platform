package com.runhuo.live.controller;

import com.runhuo.live.service.SysUserService;
import com.runhuo.live.vo.R;
import com.runhuo.live.vo.request.RegisterReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:14
 */
@RestController
@RequestMapping("/user")
@SuppressWarnings("all")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/getCaptcha/register/{phone}")
    public R getCaptchaByRegister(@PathVariable("phone") String phone) {
        return sysUserService.getCaptchaByRegister(phone);
    }

    @GetMapping("/getCaptcha/login/{phone}")
    public R getCaptchaByLogin(@PathVariable("phone") String phone) {
        return sysUserService.getCaptchaByLogin(phone);
    }


    @PostMapping("/userLogin/captcha/{phone}/{captcha}")
    public R loginByCaptcha(@PathVariable("phone") String phone,
                            @PathVariable("captcha") String captcha) {
        return sysUserService.loginByCaptcha(phone, captcha);
    }

    @PostMapping("/userLogin/password/{phone}/{password}")
    public R loginByPassword(@PathVariable("phone") String phone,
                             @PathVariable("password") String password) {
        return sysUserService.loginByPassword(phone, password);
    }

    @PostMapping("/userRegister")
    public R userRegister(@RequestBody RegisterReqVo registerReqVo) {
        return sysUserService.userRegister(registerReqVo);
    }

    @PostMapping("/userLogout/{phone}")
    public R logout(@PathVariable("phone") String phone) {
        return sysUserService.logout(phone);
    }
}
