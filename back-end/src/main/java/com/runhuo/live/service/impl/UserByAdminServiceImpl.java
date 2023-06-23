package com.runhuo.live.service.impl;

import com.runhuo.live.dao.UserMapper;
import com.runhuo.live.entity.User;
import com.runhuo.live.enums.ResultCode;
import com.runhuo.live.service.UserByAdminService;
import com.runhuo.live.vo.R;
import com.runhuo.live.vo.response.UserInfoByAdminResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/19 15:52
 */
@Service
@SuppressWarnings("all")
public class UserByAdminServiceImpl implements UserByAdminService {
    @Autowired
    private UserMapper userMapper;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public R getUserInfoByAdmin(Integer index) {
        List<User> users = userMapper.selectUserInfoByAdmin(index);
        List<UserInfoByAdminResVo> userInfoByAdminResVos = new ArrayList<>();
        for (User user : users) {
            UserInfoByAdminResVo userInfoByAdminResVo = new UserInfoByAdminResVo();
            userInfoByAdminResVo.setUserId(user.getUserId());
            userInfoByAdminResVo.setAvatar(user.getAvatar());
            userInfoByAdminResVo.setNickname(user.getNickname());
            userInfoByAdminResVo.setSignature(user.getSignature());
            userInfoByAdminResVo.setRegisterTime(sdf.format(user.getRegisterTime()));
            switch (user.getIdentity()) {
                case 1:
                    userInfoByAdminResVo.setIdentity("访客");
                    break;
                case 2:
                    userInfoByAdminResVo.setIdentity("学生");
                    break;
                case 3:
                    userInfoByAdminResVo.setIdentity("教师");
                    break;
            }
            userInfoByAdminResVos.add(userInfoByAdminResVo);
        }
        HashMap<String, Object> result = new HashMap<>();
        if (index ==0) {
            result.put("count", userMapper.selectAllUserCount());
        }
        result.put("userInfo", userInfoByAdminResVos);
        return R.success(ResultCode.SEARCH_SUCCESS, result);
    }

    @Override
    public R logoutUserByAdmin(Long userId) {
        int result = userMapper.deleteUserByAdmin(userId);
        if (result != 0) {
            return R.success(ResultCode.UPDATE_SUCCESS, null);
        }
        return R.fail(ResultCode.UPDATE_FAIL);
    }
}
