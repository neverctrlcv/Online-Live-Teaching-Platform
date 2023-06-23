package com.runhuo.live.service;

import com.runhuo.live.vo.R;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/19 19:47
 */
public interface VideoByAdminService {
    public R getVideoInfoByAdmin(Integer index);

    public R getAllVideoCount();

    public R deleteVideoByAdmin(Long videoId);
}
