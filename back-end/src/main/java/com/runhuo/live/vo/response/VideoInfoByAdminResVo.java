package com.runhuo.live.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/19 19:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoInfoByAdminResVo {
    private Long videoId;
    private Long playCount;
    private String videoLength;
    private String uploadTime;
    private String description;
    private String nickname;
    private String cover;
}
