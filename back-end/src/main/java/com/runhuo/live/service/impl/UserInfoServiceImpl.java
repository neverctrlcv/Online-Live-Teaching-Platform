package com.runhuo.live.service.impl;


import com.runhuo.live.dao.UserMapper;
import com.runhuo.live.entity.User;
import com.runhuo.live.enums.ResultCode;
import com.runhuo.live.service.UserInfoService;
import com.runhuo.live.utils.JwtUtils;
import com.runhuo.live.vo.R;
import com.runhuo.live.vo.request.IdentityInfoReqVo;
import com.runhuo.live.vo.request.UserInfoReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/17 17:51
 */
@Service
@SuppressWarnings("all")
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public R updateUserAvatar(String token, MultipartFile file) throws IOException {
        Long userId = JwtUtils.getIdByToken(token);
        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String avatarPath = ClassUtils.getDefaultClassLoader().getResource("static/avatar").getPath();
        String fileName = UUID.randomUUID().toString() + fileExtension;
        file.transferTo(new File(avatarPath + "/" + fileName));
        String url = "http://localhost:8080/avatar/" + fileName;
        int result = userMapper.updateUserAvatar(url, userId);
        if (result != 0) {
            return R.success(ResultCode.UPDATE_SUCCESS, url);
        }
        return R.fail(ResultCode.UPDATE_FAIL);
    }

    @Override
    public R userInfoModify(String token, UserInfoReqVo userInfoReqVo) {
        Long userId = JwtUtils.getIdByToken(token);
        User user = new User();
        user.setUserId(userId);
        user.setNickname(userInfoReqVo.getNickname());
        user.setAge(userInfoReqVo.getAge());
        user.setSex(userInfoReqVo.getSex());
        user.setSignature(userInfoReqVo.getSignature());
        int result = userMapper.updateUserBasicInfo(user);
        if (result != 0) {
            return R.success(ResultCode.UPDATE_SUCCESS, null);
        }
        return R.fail(ResultCode.UPDATE_FAIL);
    }

    @Override
    public R identityVerify(String token, IdentityInfoReqVo identityInfoReqVo) {
        Long userId = JwtUtils.getIdByToken(token);
        User user = new User();
        user.setUserId(userId);
        user.setIdentity(identityInfoReqVo.getIdentity());
        user.setName(identityInfoReqVo.getName());
        user.setJobId(identityInfoReqVo.getJobId());
        int result = userMapper.updateUserIdentityInfo(user);
        if (result != 0) {
            return R.success(ResultCode.UPDATE_SUCCESS, null);
        }
        return R.fail(ResultCode.UPDATE_FAIL);
    }
}
