package com.runhuo.live.dao;

import com.runhuo.live.entity.StatusT;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/23 16:05
 */
@Mapper
public interface StatusTMapper {
    public int insertStatusLog(StatusT statusT);
    public List<StatusT> getStatusTById(Long id);
}
