package com.runhuo.live.service.impl;

import com.runhuo.live.dao.UserMapper;
import com.runhuo.live.dao.VideoMapper;
import com.runhuo.live.entity.User;
import com.runhuo.live.entity.Video;
import com.runhuo.live.enums.ResultCode;
import com.runhuo.live.service.VideoService;
import com.runhuo.live.utils.JwtUtils;
import com.runhuo.live.vo.R;
import com.runhuo.live.vo.request.UploadVideoReqVo;
import com.runhuo.live.vo.response.*;
import lombok.extern.slf4j.Slf4j;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/18 15:24
 */
@Service
@SuppressWarnings("all")
@Slf4j
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private UserMapper userMapper;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public R uploadVideo(MultipartFile cover, MultipartFile video, String token, UploadVideoReqVo uploadVideoReqVo) throws IOException {
        Long userId = JwtUtils.getIdByToken(token);
        String coverExtension = cover.getOriginalFilename().substring(cover.getOriginalFilename().lastIndexOf("."));
        String videoExtension = video.getOriginalFilename().substring(video.getOriginalFilename().lastIndexOf("."));
        String coverPath = ClassUtils.getDefaultClassLoader().getResource("static/cover").getPath();
        String videoPath = ClassUtils.getDefaultClassLoader().getResource("static/video").getPath();
        String coverName = UUID.randomUUID().toString() + coverExtension;
        String videoName = UUID.randomUUID().toString() + videoExtension;
        cover.transferTo(new File(coverPath + "/" + coverName));
        video.transferTo(new File(videoPath + "/" + videoName));
        Video videoEntity = new Video();
        videoEntity.setLink("http://localhost:8080/video/" + videoName);
        videoEntity.setPlayCount(0L);
        videoEntity.setVideoLength(uploadVideoReqVo.getVideoLength());
        videoEntity.setUploadTime(new Timestamp(new Date().getTime()));
        videoEntity.setDescription(uploadVideoReqVo.getDesc());
        videoEntity.setUserId(userId);
        videoEntity.setCover("http://localhost:8080/cover/" + coverName);
        int result = videoMapper.insertVideo(videoEntity);
        if (result != 0) {
            return R.success(ResultCode.UPDATE_SUCCESS, null);
        }
        return R.fail(ResultCode.UPDATE_FAIL);
    }

    @Override
    public R shareVideo(Long videoId) {
        int result = videoMapper.shareVideo(videoId);
        if (result != 0) {
            return R.success(ResultCode.UPDATE_SUCCESS, null);
        }
        return R.fail(ResultCode.UPDATE_FAIL);
    }

    @Override
    public R hiddenVideo(Long videoId) {
        int result = videoMapper.hiddenVideo(videoId);
        if (result != 0) {
            return R.success(ResultCode.UPDATE_SUCCESS, null);
        }
        return R.fail(ResultCode.UPDATE_FAIL);
    }

    @Override
    public R deleteVideo(Long videoId) {
        int result = videoMapper.deleteVideo(videoId);
        if (result != 0) {
            return R.success(ResultCode.UPDATE_SUCCESS, null);
        }
        return R.fail(ResultCode.UPDATE_FAIL);
    }

    @Override
    public R getVideoByFounder(String token) {
        Long userId = JwtUtils.getIdByToken(token);
        List<Video> videos = videoMapper.selectVideoByUserId(userId);
        List<VideoInfoResVo> videoInfoResVos = new ArrayList<>();
        for (Video video : videos) {
            VideoInfoResVo videoInfoResVo = new VideoInfoResVo(video.getVideoId(), video.getLink(), video.getPlayCount(),
                    sdf.format(video.getUploadTime()), video.getDescription(), video.getUserId(), video.getCover(),
                    video.getStatus(), video.getVideoLength());
            videoInfoResVos.add(videoInfoResVo);
        }
        return R.success(ResultCode.SEARCH_SUCCESS, videoInfoResVos);
    }

    @Override
    public R getVideoLink(Long videoId) {
        HashMap<String, Object> result = new HashMap<>();
        Video video = videoMapper.selectVideoByVideoId(videoId);
        VideoInfoInPlayResVo videoInfoInPlayResVo = new VideoInfoInPlayResVo(video.getVideoId(), video.getLink(),
                video.getPlayCount(), sdf1.format(video.getUploadTime()), video.getCover(), video.getDescription(),
                video.getVideoLength());
        result.put("videoInfo", videoInfoInPlayResVo);

        Long userId = video.getUserId();
        User user = userMapper.selectUserInPlayByUserId(userId);
        UserInfoInPlayResVo userInfoInPlayResVo = new UserInfoInPlayResVo(user.getAvatar(), user.getNickname(), user.getSignature());
        result.put("userInfo", userInfoInPlayResVo);


        KeyWordComputer kwc = new KeyWordComputer(5);
        List<Keyword> keywordList = kwc.computeArticleTfidf(video.getDescription());
        List<VideoSuggestInPlayResVo> videoSuggestInPlayResVos = new ArrayList<>();
        HashMap<Long, Long> judge = new HashMap<>();
        for (int i = 0; i < keywordList.size(); i++) {
            String content = keywordList.get(i).getName();
            //根据关键词的重要程度按比例查询
            List<Video> videos = videoMapper.selectVideoByKeyword(content, Math.toIntExact(videoId), 15 - i * 2);
            for (Video video1 : videos) {
                if (videoSuggestInPlayResVos.size() == 15)
                    break;
                if (!judge.containsKey(video1.getVideoId())) {
                    judge.put(video.getVideoId(), video.getVideoId());
                    String nickname = userMapper.selectNicknameByUserId(video.getUserId());
                    VideoSuggestInPlayResVo videoSuggestInPlayResVo = new VideoSuggestInPlayResVo(video1.getVideoId(),
                            video1.getPlayCount(), sdf.format(video1.getUploadTime()), video1.getCover(), video1.getDescription(),
                            video1.getVideoLength(), nickname);
                    videoSuggestInPlayResVos.add(videoSuggestInPlayResVo);
                }
            }
        }
        result.put("suggest", videoSuggestInPlayResVos);
        videoMapper.updatePlayCountByVideoId(videoId);
        return R.success(ResultCode.SEARCH_SUCCESS, result);
    }
}
