package com.runhuo.live.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/18 16:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoInfoResVo {
    private Long videoId;
    private String link;
    private Long playCount;
    private String uploadTime;
    private String description;
    private Long userId;
    private String cover;
    private String status;
    private String videoLength;
}
