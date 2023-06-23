package com.runhuo.live.dao;

import com.runhuo.live.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:40
 */
@Mapper
public interface AdminMapper {
    public Admin selectAdminByUsername(String username);
}
