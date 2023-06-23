package com.runhuo.live.service;

import com.runhuo.live.vo.R;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/19 15:52
 */
public interface UserByAdminService {
    public R getUserInfoByAdmin(Integer index);
    public R logoutUserByAdmin(Long userId);
}
