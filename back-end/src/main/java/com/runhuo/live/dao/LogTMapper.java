package com.runhuo.live.dao;

import com.runhuo.live.entity.LogT;
import com.runhuo.live.entity.RtcUsage;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:41
 */
@Mapper
public interface LogTMapper {

    public int insertLog(LogT logT);

    public List<LogT> getLog(Timestamp startTime, Timestamp endTime);
}
