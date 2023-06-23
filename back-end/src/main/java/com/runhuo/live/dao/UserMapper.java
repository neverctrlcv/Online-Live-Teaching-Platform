package com.runhuo.live.dao;

import com.runhuo.live.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:41
 */
@Mapper
public interface UserMapper {

    public User selectUserInLogin(String phone);
    public User selectUserInRegister(String phone);
    public int userRegister(User user);
    public int userReRegister(User user);
    public int updateUserBasicInfo(User user);
    public int updateUserIdentityInfo(User user);
    public int updateUserAvatar(String url,Long userId);

    public String selectNicknameByUserId(Long userId);

    public List<User> selectUserInfoByAdmin(Integer index);
    public int deleteUserByAdmin(Long userId);

    public int selectAllUserCount();
    public User selectUserInPlayByUserId(Long userId);
    public User selectUserCareerInfo(Long userId);

}
