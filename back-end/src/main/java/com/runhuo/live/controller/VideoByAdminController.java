package com.runhuo.live.controller;

import com.runhuo.live.service.VideoByAdminService;
import com.runhuo.live.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/19 19:46
 */
@RestController
@RequestMapping("/video-admin")
@SuppressWarnings("all")
public class VideoByAdminController {
    @Autowired
    private VideoByAdminService videoByAdminService;

    @GetMapping("/getVideoInfoByAdmin/{index}")
    public R getVideoInfoByAdmin(@PathVariable("index") Integer index) {
        return videoByAdminService.getVideoInfoByAdmin(index);
    }

    @GetMapping("/getAllVideoCount")
    public R getAllVideoCount() {
        return videoByAdminService.getAllVideoCount();
    }

    @DeleteMapping("/deleteVideoByAdmin/{videoId}")
    public R deleteVideoByAdmin(@PathVariable("videoId") Long videoId) {
        return videoByAdminService.deleteVideoByAdmin(videoId);
    }
}
