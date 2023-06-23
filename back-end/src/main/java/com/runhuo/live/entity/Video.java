package com.runhuo.live.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private Long videoId;
    private String link;
    private Long playCount;
    private String videoLength;
    private Timestamp uploadTime;
    private String description;
    private Long userId;
    private String cover;
    private String status;
    private String isDelete;

}
