package com.runhuo.live.service.impl;

import com.runhuo.live.controller.WebSocketController;
import com.runhuo.live.dao.ClassTMapper;
import com.runhuo.live.dao.StatusTMapper;
import com.runhuo.live.entity.ClassT;
import com.runhuo.live.entity.StatusT;
import com.runhuo.live.enums.ResultCode;
import com.runhuo.live.service.ClassroomService;
import com.runhuo.live.service.RedisService;
import com.runhuo.live.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/22 21:35
 */
@Service
@SuppressWarnings("all")
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired
    private WebSocketController webSocketController;
    @Autowired
    private RedisService redisService;

    @Autowired
    private ClassTMapper classTMapper;
    @Autowired
    private StatusTMapper statusTMapper;
    @Override
    public R getClassMember(Long classId) {
        Object[] member = webSocketController.getMember(classId);
        return R.success(ResultCode.SEARCH_SUCCESS,member);
    }

    @Override
        public R recordStatus(String userId, Long classTId) {
        StatusT statusT=new StatusT();
        statusT.setId(classTId);
        statusT.setLogTime(new Timestamp(new Date().getTime()));
        statusT.setLogInfo(userId+"切换了页面");
        statusTMapper.insertStatusLog(statusT);
        return R.success(ResultCode.CREATE_CLASS_SUCCESS,null);
    }

    @Override
    public R recordClassStatus(Long classId,Integer identity) {
        if (identity==3){
            ClassT classT=new ClassT();
            classT.setStartTime(new Timestamp(new Date().getTime()));
            classT.setClassId(classId);
            int i = classTMapper.insertClassT(classT);
            Long id=classT.getId();
            redisService.set(classId+"classT",id);
            return R.success(ResultCode.CREATE_CLASS_SUCCESS,id);
        }
        else {
            Long id =((Integer) redisService.get(classId + "classT")).longValue();
            return R.success(ResultCode.CREATE_CLASS_SUCCESS,id);
        }
    }
}
