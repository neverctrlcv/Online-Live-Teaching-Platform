package com.runhuo.live.controller;

import com.runhuo.live.service.ClassService;
import com.runhuo.live.vo.R;
import com.runhuo.live.vo.request.ClassInfoReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/20 21:40
 */
@RestController
@RequestMapping("/class")
@SuppressWarnings("all")
public class ClassController {

    @Autowired
    private ClassService classService;

    @PostMapping("/create")
    public R createClass(@RequestHeader HttpHeaders httpHeaders,
                         @RequestBody ClassInfoReqVo classInfoReqVo) {
        List<String> authorization = httpHeaders.get("Authorization");
        return classService.createClass(authorization.get(0),
                classInfoReqVo.getClassName(), classInfoReqVo.getSlogan());
    }

    @PostMapping("/join/{inviteCode}")
    public R joinClass(@PathVariable("inviteCode") String inviteCode,
                       @RequestHeader HttpHeaders httpHeaders) {
        List<String> authorization = httpHeaders.get("Authorization");
        return classService.joinClass(inviteCode, authorization.get(0));
    }

    @DeleteMapping("/exit/{classId}")
    public R exitClass(@PathVariable("classId") Long classId,
                       @RequestHeader HttpHeaders httpHeaders) {
        List<String> authorization = httpHeaders.get("Authorization");
        return classService.exitClass(classId, authorization.get(0));
    }

    @DeleteMapping("/dissolve/{classId}")
    public R dissolveClass(@PathVariable("classId") Long classId,
                           @RequestHeader HttpHeaders httpHeaders) {
        List<String> authorization = httpHeaders.get("Authorization");
        return classService.dissolveClass(classId, authorization.get(0));
    }


    @GetMapping("/getClassIdByUser")
    public R getClassIdByUser(@RequestHeader HttpHeaders httpHeaders) {
        List<String> authorization = httpHeaders.get("Authorization");
        return classService.getClassIdByUser(authorization.get(0));
    }

    @GetMapping("getClassAllInfo/{classId}")
    public R getClassAllInfo(@RequestHeader HttpHeaders httpHeaders,
                             @PathVariable("classId") Long classId) {
        List<String> authorization = httpHeaders.get("Authorization");
        return classService.getClassAllInfo(authorization.get(0), classId);

    }

    @GetMapping("/getClassroomStatus/{classId}")
    public R getClassroomStatus(@PathVariable("classId") Long classId){
        return classService.getClassroomStatus(classId);
    }

    @GetMapping("/downloadStatus/{id}")
    public R downloadStatus(@PathVariable("id") Long id){
        return classService.getStudentStatus(id);
    }

}
