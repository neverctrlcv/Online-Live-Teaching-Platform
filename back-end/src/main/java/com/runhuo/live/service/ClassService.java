package com.runhuo.live.service;

import com.runhuo.live.vo.R;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 *
 * @Date: 2023/5/20 21:39
 */
public interface ClassService {
    public R createClass(String token,String className,String slogan);

    public R joinClass(String inviteCode, String token);

    public R exitClass(Long classId, String token);

    public R dissolveClass(Long classId, String token);
    public R getClassIdByUser(String token);

    public R getClassAllInfo(String token,Long classId);

    public R getClassroomStatus(Long classId);


    public R getStudentStatus(Long id);
}
