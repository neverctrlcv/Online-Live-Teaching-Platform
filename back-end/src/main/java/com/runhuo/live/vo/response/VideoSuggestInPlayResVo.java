package com.runhuo.live.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/20 16:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoSuggestInPlayResVo {
    private Long videoId;
    private Long playCount;
    private String uploadTime;
    private String cover;
    private String description;
    private String videoLength;
    private String nickname;
}
