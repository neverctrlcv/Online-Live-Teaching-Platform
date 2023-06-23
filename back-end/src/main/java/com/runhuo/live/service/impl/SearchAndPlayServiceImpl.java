package com.runhuo.live.service.impl;

import com.runhuo.live.dao.UserMapper;
import com.runhuo.live.dao.VideoMapper;
import com.runhuo.live.entity.Video;
import com.runhuo.live.enums.ResultCode;
import com.runhuo.live.service.SearchAndPlayService;
import com.runhuo.live.vo.R;
import com.runhuo.live.vo.response.VideoBySearchResVo;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/18 17:05
 */
@Service
@SuppressWarnings("all")
public class SearchAndPlayServiceImpl implements SearchAndPlayService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private UserMapper userMapper;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public R searchVideoByKeyword(Integer index, String keywords) {
        //使用ansj提取出关键词
        KeyWordComputer kwc = new KeyWordComputer(5);
        List<Keyword> keywordList = kwc.computeArticleTfidf(keywords);
        HashMap<Long, Long> judge = new HashMap<>();
        //标记下次查询数据库时从哪里开始查
        Long maxId = 0L;
        List<VideoBySearchResVo> list = new ArrayList<>();
        for (int i = 0; i < keywordList.size(); i++) {
            String content = keywordList.get(i).getName();
            //根据关键词的重要程度按比例查询
            List<Video> videos = videoMapper.selectVideoByKeyword(content, index, 15 - i * 2);
            for (Video video : videos) {
                System.out.println("视频的id："+video.getVideoId());
                if (list.size() == 15)
                    break;
                if (!judge.containsKey(video.getVideoId())) {
                    judge.put(video.getVideoId(), video.getVideoId());
                    maxId = video.getVideoId() > maxId ? video.getVideoId() : maxId;
                    String nickname = userMapper.selectNicknameByUserId(video.getUserId());
                    VideoBySearchResVo videoBySearchResVo = new VideoBySearchResVo(video.getVideoId(),
                            video.getPlayCount(), video.getVideoLength(), sdf.format(video.getUploadTime()),
                            video.getDescription(), nickname, video.getCover());
                    list.add(videoBySearchResVo);
                }
            }
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("index", maxId + 1);
        result.put("result", list);
        return R.success(ResultCode.SEARCH_SUCCESS, result);
    }
}
