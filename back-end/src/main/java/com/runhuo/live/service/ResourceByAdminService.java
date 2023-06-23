package com.runhuo.live.service;

import com.runhuo.live.vo.R;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/19 13:37
 */
public interface ResourceByAdminService {
    public R getSmsCountAndRtcTime();

    public R getRTCUsage(boolean is7Day);
    public R getLogInfo(boolean is7Day);
}
