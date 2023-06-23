package com.runhuo.live.service;

import com.runhuo.live.vo.R;
import com.runhuo.live.vo.request.UploadVideoReqVo;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/18 15:24
 */
public interface VideoService {
    public R uploadVideo(MultipartFile cover, MultipartFile video, String token, UploadVideoReqVo uploadVideoReqVo) throws IOException;
    public R shareVideo(Long videoId);
    public R hiddenVideo(Long videoId);
    public R deleteVideo(Long videoId);
    public R getVideoByFounder(String token);

    public R getVideoLink(Long video);
}
