package com.runhuo.live.controller;

import com.runhuo.live.service.UserByAdminService;
import com.runhuo.live.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/19 15:52
 */
@RestController
@RequestMapping("/user-admin")
@SuppressWarnings("all")
public class UserByAdminController {
    @Autowired
    private UserByAdminService userByAdminService;

    @GetMapping("/getUserInfoByAdmin/{index}")
    private R getUserInfoByAdmin(@PathVariable("index") Integer index) {
        return userByAdminService.getUserInfoByAdmin(index);
    }

    @DeleteMapping("/logoutUser/{userId}")
    private R logoutUser(@PathVariable("userId") Long userId) {
        return userByAdminService.logoutUserByAdmin(userId);
    }

}
