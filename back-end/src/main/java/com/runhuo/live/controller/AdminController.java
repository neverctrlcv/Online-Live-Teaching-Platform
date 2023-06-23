package com.runhuo.live.controller;

import com.runhuo.live.entity.Admin;
import com.runhuo.live.service.AdminService;
import com.runhuo.live.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/18 21:36
 */
@RestController
@RequestMapping("/admin")
@SuppressWarnings("all")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login/{username}/{password}")
    public R adminLogin(@PathVariable("username") String username,
                        @PathVariable("password") String password) {
        return adminService.adminLogin(username, password);
    }

    @DeleteMapping("/logout")
    public R logout() {
        return adminService.adminLogout();
    }


}
