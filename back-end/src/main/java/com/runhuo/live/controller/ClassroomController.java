package com.runhuo.live.controller;

import com.runhuo.live.service.ClassroomService;
import com.runhuo.live.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/22 19:55
 */
@RestController
@RequestMapping("/classroom")
@SuppressWarnings("all")
public class ClassroomController {
    @Autowired
    private ClassroomService classroomService;

    @GetMapping("/getClassMember/{classId}")
    public R getClassMember(@PathVariable("classId") Long classId) {
        return classroomService.getClassMember(classId);
    }

    @PostMapping("/recordStatus/{userId}/{classTId}")
    public R recordStatus(@PathVariable("userId") String userId, @PathVariable("classTId") Long classTId) {
        return classroomService.recordStatus(userId, classTId);
    }

    @PostMapping("/recordClassStatus/{classId}/{identity}")
    public R recordClassStatus(@PathVariable("classId") Long classId,
                               @PathVariable("identity")Integer identity) {
        return classroomService.recordClassStatus(classId,identity);
    }
}
