package com.runhuo.live.service.impl;

import com.runhuo.live.dao.UserMapper;
import com.runhuo.live.dao.VideoMapper;
import com.runhuo.live.entity.Video;
import com.runhuo.live.enums.ResultCode;
import com.runhuo.live.service.VideoByAdminService;
import com.runhuo.live.vo.R;
import com.runhuo.live.vo.response.VideoInfoByAdminResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/19 19:47
 */
@Service
@SuppressWarnings("all")
public class VideoByAdminServiceImpl implements VideoByAdminService {

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private UserMapper userMapper;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public R getVideoInfoByAdmin(Integer index) {
        List<Video> videos = videoMapper.selectVideoInfoByAdmin(index);
        List<VideoInfoByAdminResVo> videoInfoByAdminResVos = new ArrayList<>();
        for (Video video : videos) {
            String nickname = userMapper.selectNicknameByUserId(video.getUserId());
            VideoInfoByAdminResVo videoInfoByAdminResVo = new VideoInfoByAdminResVo();
            videoInfoByAdminResVo.setVideoId(video.getVideoId());
            videoInfoByAdminResVo.setPlayCount(video.getPlayCount());
            videoInfoByAdminResVo.setVideoLength(video.getVideoLength());
            videoInfoByAdminResVo.setUploadTime(sdf.format(video.getUploadTime()));
            videoInfoByAdminResVo.setDescription(video.getDescription());
            videoInfoByAdminResVo.setNickname(nickname);
            videoInfoByAdminResVo.setCover(video.getCover());
            videoInfoByAdminResVos.add(videoInfoByAdminResVo);
        }
        return R.success(ResultCode.SEARCH_SUCCESS, videoInfoByAdminResVos);
    }

    @Override
    public R getAllVideoCount() {
        Long count = videoMapper.selectAllVideoCount();
        return R.success(ResultCode.SEARCH_SUCCESS, count);
    }

    @Override
    public R deleteVideoByAdmin(Long videoId) {
        int result = videoMapper.deleteVideo(videoId);
        if (result != 0) {
            return R.success(ResultCode.UPDATE_SUCCESS, null);
        }
        return R.fail(ResultCode.UPDATE_FAIL);
    }
}
