package com.runhuo.live.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/20 16:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoInfoInPlayResVo {
    private Long videoId;
    private String link;
    private Long playCount;
    private String uploadTime;
    private String cover;
    private String description;
    private String videoLength;
}
