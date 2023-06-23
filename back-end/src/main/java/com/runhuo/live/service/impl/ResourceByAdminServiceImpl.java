package com.runhuo.live.service.impl;

import com.runhuo.live.dao.LogTMapper;
import com.runhuo.live.dao.RtcUsageMapper;
import com.runhuo.live.dao.SysResMapper;
import com.runhuo.live.entity.LogT;
import com.runhuo.live.entity.RtcUsage;
import com.runhuo.live.entity.SysRes;
import com.runhuo.live.enums.ResultCode;
import com.runhuo.live.service.ResourceByAdminService;
import com.runhuo.live.vo.R;
import com.runhuo.live.vo.response.LogTResVo;
import com.runhuo.live.vo.response.RtcUsageResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/19 13:37
 */
@Service
@SuppressWarnings("all")
public class ResourceByAdminServiceImpl implements ResourceByAdminService {
    @Autowired
    private SysResMapper sysResMapper;
    @Autowired
    private RtcUsageMapper rtcUsageMapper;
    @Autowired
    private LogTMapper logTMapper;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public R getSmsCountAndRtcTime() {
        SysRes smsCountAndRtcTime = sysResMapper.getSmsCountAndRtcTime();
        return R.success(ResultCode.SEARCH_SUCCESS, smsCountAndRtcTime);
    }

    @Override
    public R getRTCUsage(boolean is7Day) {
        int day = is7Day ? 7 : 15;
        long endtime = new Date().getTime();
        long startTime = endtime - day* 24 * 60 * 60 * 1000;
        List<RtcUsage> rtcUsage = rtcUsageMapper.getRtcUsage(new Timestamp(startTime), new Timestamp(endtime));
        List<RtcUsageResVo> rtcUsageResVos = new ArrayList<>();
        for (RtcUsage rtcUsage1 : rtcUsage) {
            RtcUsageResVo rtcUsageResVo = new RtcUsageResVo(rtcUsage1.getRtcUsage(), sdf.format(rtcUsage1.getUseDate()));
            rtcUsageResVos.add(rtcUsageResVo);
        }
        return R.success(ResultCode.SEARCH_SUCCESS, rtcUsageResVos);
    }

    @Override
    public R getLogInfo(boolean is7Day) {
        int day = is7Day ? 7 : 15;
        long endtime = new Date().getTime();
        long startTime = endtime - day * 24 * 60 * 60 * 1000;
        List<LogT> log = logTMapper.getLog(new Timestamp(startTime), new Timestamp(endtime));
        List<LogTResVo> logTResVos = new ArrayList<>();
        for (LogT logT : log) {
            LogTResVo logTResVo = new LogTResVo(logT.getLogInfo(), sdf.format(logT.getTime()));
            logTResVos.add(logTResVo);
        }
        return R.success(ResultCode.SEARCH_SUCCESS, logTResVos);
    }
}
