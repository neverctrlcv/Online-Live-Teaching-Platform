package com.runhuo.live.service.impl;

import com.runhuo.live.dao.AdminMapper;
import com.runhuo.live.dao.SysResMapper;
import com.runhuo.live.entity.Admin;
import com.runhuo.live.entity.SysRes;
import com.runhuo.live.enums.ResultCode;
import com.runhuo.live.service.AdminService;
import com.runhuo.live.service.RedisService;
import com.runhuo.live.utils.JwtUtils;
import com.runhuo.live.vo.R;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/18 21:37
 */
@Service
@SuppressWarnings("all")
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public R adminLogin(String username, String password) {
        Admin admin = adminMapper.selectAdminByUsername(username);
        if (admin == null) {
            return R.fail(ResultCode.USER_NOT_EXIST);
        }
        if (!DigestUtils.md5Hex(password).equals(admin.getPassword())) {
            return R.fail(ResultCode.PASSWORD_ERROR);
        }
        String jwtToken = JwtUtils.getJwtToken(0L);
        redisService.set("admin", jwtToken, 60 * 60 * 24);
        return R.success(ResultCode.LOGIN_SUCCESS, jwtToken);
    }

    @Override
    public R adminLogout() {
        redisService.del("admin");
        return R.success(ResultCode.LOGOUT_SUCCESS, null);
    }



}
