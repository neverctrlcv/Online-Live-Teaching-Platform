package com.runhuo.live.service.impl;

import com.runhuo.live.dao.LogTMapper;
import com.runhuo.live.dao.SysResMapper;
import com.runhuo.live.dao.UserMapper;
import com.runhuo.live.entity.LogT;
import com.runhuo.live.entity.User;
import com.runhuo.live.enums.ResultCode;
import com.runhuo.live.service.RedisService;
import com.runhuo.live.service.SysUserService;
import com.runhuo.live.utils.JwtUtils;
import com.runhuo.live.utils.SendSmsUtils;
import com.runhuo.live.vo.R;
import com.runhuo.live.vo.request.RegisterReqVo;
import com.runhuo.live.vo.response.LoginResVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:16
 */
@Service
@SuppressWarnings("all")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LogTMapper logTMapper;
    @Autowired
    private SysResMapper sysResMapper;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public R getCaptchaByRegister(String phone) {
        User user = userMapper.selectUserInRegister(phone);
        if (user != null&&user.getStatus().equals("Y")) {
            return R.fail(ResultCode.USER_EXIST);
        }
        String captcha = SendSmsUtils.send(phone);
        if (captcha != null && captcha.length() == 6) {
            LogT logT = new LogT();
            logT.setTime(new Timestamp(new Date().getTime()));
            logT.setLogInfo("用户" + phone + "使用了一条短信验证码!");
            logTMapper.insertLog(logT);
            sysResMapper.smsCountSelfDecrease();
            redisService.set(phone + "register-captcha", captcha, 60 * 5);
            return R.success(ResultCode.SEND_SUCCESS, null);
        }
        return R.fail(ResultCode.SEND_ERROR);
    }

    @Override
    public R getCaptchaByLogin(String phone) {
        User user = userMapper.selectUserInLogin(phone);
        if (user == null) {
            return R.fail(ResultCode.USER_NOT_EXIST);
        }
        String captcha = SendSmsUtils.send(phone);
        if (captcha != null && captcha.length() == 6) {
            LogT logT = new LogT();
            logT.setTime(new Timestamp(new Date().getTime()));
            logT.setLogInfo("用户" + phone + "使用了一条短信验证码!");
            logTMapper.insertLog(logT);
            sysResMapper.smsCountSelfDecrease();
            redisService.set(phone + "login-captcha", captcha, 60 * 5);
            redisService.set(phone + "userInfo", user, 60 * 5);
            return R.success(ResultCode.SEND_SUCCESS, null);
        }
        return R.fail(ResultCode.SEND_ERROR);
    }

    @Override
    public R loginByCaptcha(String phone, String captcha) {
        if (!redisService.hasKey(phone + "login-captcha")) {
            return R.fail(ResultCode.CAPTCHA_ERROR);
        }
        String captchaInRedis = (String) redisService.get(phone + "login-captcha");
        User user = (User) redisService.get(phone + "userInfo");
        if (!captchaInRedis.equals(captcha)) {
            return R.fail(ResultCode.CAPTCHA_ERROR);
        }
        String jwtToken = JwtUtils.getJwtToken(user.getUserId());
        LoginResVo loginResVo = new LoginResVo(user.getIdentity(), user.getUserId(), user.getAge(), user.getSex(),
                user.getNickname(), user.getSignature(), user.getPhone(), user.getAvatar(), user.getJobId(),
                sdf.format(user.getRegisterTime()), user.getName(), jwtToken);
        redisService.set(phone, jwtToken, 60 * 60 * 24);
        return R.success(ResultCode.LOGIN_SUCCESS, loginResVo);
    }

    @Override
    public R loginByPassword(String phone, String password) {
        User user = userMapper.selectUserInLogin(phone);
        if (user == null) {
            return R.fail(ResultCode.USER_NOT_EXIST);
        }
        String password1 = user.getPassword();
        if (!DigestUtils.md5Hex(password).equals(password1)) {
            return R.fail(ResultCode.PASSWORD_ERROR);
        }
        String jwtToken = JwtUtils.getJwtToken(user.getUserId());
        LoginResVo loginResVo = new LoginResVo(user.getIdentity(), user.getUserId(), user.getAge(), user.getSex(),
                user.getNickname(), user.getSignature(), user.getPhone(), user.getAvatar(), user.getJobId(),
                sdf.format(user.getRegisterTime()), user.getName(), jwtToken);
        redisService.set(phone, jwtToken, 60 * 60 * 24);
        return R.success(ResultCode.LOGIN_SUCCESS, loginResVo);
    }

    @Override
    public R userRegister(RegisterReqVo registerReqVo) {
        String phone = registerReqVo.getPhone();
        String captchaInRedis = (String) redisService.get(phone + "register-captcha");
        if (captchaInRedis == null || !captchaInRedis.equals(registerReqVo.getCaptcha())) {
            return R.fail(ResultCode.CAPTCHA_ERROR);
        }
        User user = userMapper.selectUserInRegister(phone);
        int result = 0;
        //之前没有注册过
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setNickname(phone);
            user.setPassword(DigestUtils.md5Hex(registerReqVo.getPassword()));
            user.setAvatar("http://localhost:8080/avatar/default.png");
            user.setRegisterTime(new Timestamp(new Date().getTime()));
            result = userMapper.userRegister(user);
        }
        //之前注册过
        else {
            user.setAge(null);
            user.setSex(null);
            user.setNickname(null);
            user.setSignature(null);
            user.setPassword(DigestUtils.md5Hex(registerReqVo.getPassword()));
            user.setAvatar("http://localhost:8080/avatar/default.png");
            user.setRegisterTime(new Timestamp(new Date().getTime()));
            user.setJobId(null);
            user.setStatus("Y");
            user.setName(null);
            user.setIdentity(1);
            result = userMapper.userReRegister(user);
        }
        if (result != 0) {
            return R.success(ResultCode.USER_REGISTER_SUCCESS, null);
        }
        return R.fail(ResultCode.USER_REGISTER_FAIL);
    }

    @Override
    public R logout(String phone) {
        if (redisService.hasKey(phone)) {
            redisService.del(phone);
        }
        return R.success(ResultCode.LOGOUT_SUCCESS, null);
    }
}
