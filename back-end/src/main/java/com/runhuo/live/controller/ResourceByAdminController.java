package com.runhuo.live.controller;

import com.runhuo.live.service.ResourceByAdminService;
import com.runhuo.live.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/19 13:35
 */
@RestController
@RequestMapping("/resource-admin")
@SuppressWarnings("all")
public class ResourceByAdminController {
    @Autowired
    private ResourceByAdminService resourceByAdminService;

    @GetMapping("/getSmsCountAndRtcTime")
    public R getSmsCountAndRtcTime() {
        return resourceByAdminService.getSmsCountAndRtcTime();
    }

    @GetMapping("/getRTCUsage/{day}")
    public R getRTCUsage(@PathVariable("day") Integer day) {
        boolean is7Day = day == 7 ? true : false;
        return resourceByAdminService.getRTCUsage(is7Day);
    }

    @GetMapping("/getLogInfo/{day}")
    public R getLogInfo(@PathVariable("day") Integer day) {
        boolean is7Day = day == 7 ? true : false;
        return resourceByAdminService.getLogInfo(is7Day);
    }

}
