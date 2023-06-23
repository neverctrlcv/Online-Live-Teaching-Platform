package com.runhuo.live.service;

import com.runhuo.live.vo.R;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/18 21:37
 */
public interface AdminService {
    public R adminLogin(String username, String password);
    public R adminLogout();

}
