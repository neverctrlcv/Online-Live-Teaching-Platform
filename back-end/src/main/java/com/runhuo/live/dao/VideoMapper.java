package com.runhuo.live.dao;

import com.runhuo.live.entity.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:42
 */
@Mapper
public interface VideoMapper {
    public int insertVideo(Video video);
    public int shareVideo(Long videoId);
    public int hiddenVideo(Long videoId);
    public int deleteVideo(Long videoId);
    public List<Video> selectVideoByUserId(Long userId);
    public List<Video> selectVideoByKeyword(String keyword,Integer index,Integer limit);

    public List<Video> selectVideoInfoByAdmin(Integer index);

    public Long selectAllVideoCount();

    public Video selectVideoByVideoId(Long videoId);

    public int updatePlayCountByVideoId(Long videoId);

}
