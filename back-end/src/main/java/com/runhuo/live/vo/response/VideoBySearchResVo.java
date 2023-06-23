package com.runhuo.live.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/18 17:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoBySearchResVo {
    private Long videoId;
    private Long playCount;
    private String videoLength;
    private String uploadTime;
    private String description;
    private String nickname;
    private String cover;
}
