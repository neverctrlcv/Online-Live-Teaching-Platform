package com.runhuo.live.service;

import com.runhuo.live.vo.R;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/22 21:35
 */
public interface ClassroomService {
    public R getClassMember(Long classId);

    public R recordStatus(String userId,Long classTId);

    public R recordClassStatus(Long classId,Integer identity);
}
