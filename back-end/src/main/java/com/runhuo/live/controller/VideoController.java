package com.runhuo.live.controller;

import com.runhuo.live.service.VideoService;
import com.runhuo.live.vo.R;
import com.runhuo.live.vo.request.UploadVideoReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/18 15:16
 */
@RestController
@RequestMapping("/video")
@SuppressWarnings("all")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("/uploadVideo")
    public R uploadVideo(@RequestParam("cover") MultipartFile cover,
                         @RequestParam("video") MultipartFile video,
                         @RequestHeader HttpHeaders httpHeaders,
                         UploadVideoReqVo uploadVideoReqVo) throws IOException {
        List<String> authorization = httpHeaders.get("Authorization");
        return videoService.uploadVideo(cover, video, authorization.get(0), uploadVideoReqVo);
    }

    @PutMapping("/shareVideo/{videoId}")
    public R shareVideo(@PathVariable("videoId") Long videoId) {
        return videoService.shareVideo(videoId);
    }

    @PutMapping("/hiddenVideo/{videoId}")
    public R hiddenVideo(@PathVariable("videoId") Long videoId) {
        return videoService.hiddenVideo(videoId);
    }

    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") Long videoId) {
        return videoService.deleteVideo(videoId);
    }

    @GetMapping("/getVideoByFounder")
    public R getVideoByFounder(@RequestHeader HttpHeaders httpHeaders) {
        List<String> authorization = httpHeaders.get("Authorization");
        return videoService.getVideoByFounder(authorization.get(0));
    }

    @GetMapping("/getVideoLink/{videoId}")
    public R getVideoLink(@PathVariable("videoId") Long videoId) {
        return videoService.getVideoLink(videoId);
    }

}
