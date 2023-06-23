package com.runhuo.live.service.impl;

import com.runhuo.live.dao.*;
import com.runhuo.live.entity.*;
import com.runhuo.live.enums.ResultCode;
import com.runhuo.live.service.ClassService;
import com.runhuo.live.service.RedisService;
import com.runhuo.live.utils.JwtUtils;
import com.runhuo.live.utils.ShareCodeUtil;
import com.runhuo.live.vo.R;
import com.runhuo.live.vo.response.ClassIdAndNameResVo;
import com.runhuo.live.vo.response.ClassInfoResVo;
import com.runhuo.live.vo.response.ClassMemberResVo;
import com.runhuo.live.vo.response.ClassTResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/20 21:39
 */
@Service
@SuppressWarnings("all")
@Transactional
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassesMapper classesMapper;
    @Autowired
    private ClassMemberMapper classMemberMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClassTMapper classTMapper;
    @Autowired
    private StatusTMapper statusTMapper;
    @Autowired
    private RedisService redisService;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");

    @Override
    public R createClass(String token, String className, String slogan) {
        Long userId = JwtUtils.getIdByToken(token);
        Long currentTime = new Date().getTime();
        String inviteCode = ShareCodeUtil.toSerialCode(currentTime);
        Classes classes = new Classes();
        classes.setClassName(className);
        classes.setSlogan(slogan);
        classes.setStatus("N");
        classes.setCreateTime(new Timestamp(currentTime));
        classes.setFounder(userId);
        classes.setAvatar("http://localhost:8080/avatar/default.png");
        classes.setInviteCode(inviteCode);
        int result1 = classesMapper.createClass(classes);
        ClassMember classMember = new ClassMember(classes.getClassId(), userId, new Timestamp(currentTime));
        int result2 = classMemberMapper.insertMember(classMember);
        if (result1 != 0 && result2 != 0) {
            return R.success(ResultCode.CREATE_CLASS_SUCCESS, null);
        }
        return R.fail(ResultCode.CREATE_CLASS_FAIL);
    }

    @Override
    public R joinClass(String inviteCode, String token) {
        Long userId = JwtUtils.getIdByToken(token);
        Long classId = classesMapper.selectClassIdByInviteCode(inviteCode);
        if (classId == null) {
            return R.fail(ResultCode.CLASS_NO_EXIST);
        }
        ClassMember classMember = new ClassMember(classId, userId, new Timestamp(new Date().getTime()));
        int result = classMemberMapper.insertMember(classMember);
        if (result != 0) {
            return R.success(ResultCode.JOIN_CLASS_SUCCESS, null);
        }
        return R.fail(ResultCode.JOIN_CLASS_FAIL);
    }

    @Override
    public R exitClass(Long classId, String token) {
        Long userId = JwtUtils.getIdByToken(token);
        int result = classMemberMapper.deleteMember(userId, classId);
        if (result != 0) {
            return R.success(ResultCode.EXIT_CLASS_SUCCESS, null);
        }
        return R.fail(ResultCode.EXIT_CLASS_FAIL);
    }

    @Override
    public R dissolveClass(Long classId, String token) {
        Long userId = JwtUtils.getIdByToken(token);
        int result1 = classesMapper.dissolveClass(classId, userId);
        int result2 = classMemberMapper.deleteAllMember(classId);
        if (result1 != 0 && result2 != 0) {
            return R.success(ResultCode.DISSOLVE_CLASS_SUCCESS, null);
        }
        return R.fail(ResultCode.DISSOLVE_CLASS_FAIL);
    }

    @Override
    public R getClassIdByUser(String token) {
        Long userId = JwtUtils.getIdByToken(token);
        List<Long> classIdList = classMemberMapper.selectClassIdByUserId(userId);
        if (classIdList == null || classIdList.size() == 0) {
            return R.success(ResultCode.SEARCH_SUCCESS, null);
        }
        List<ClassIdAndNameResVo> classIdAndNameResVos = new ArrayList<>();
        for (Long classId : classIdList) {
            String className = classesMapper.selectClassNameByClassId(classId);
            ClassIdAndNameResVo classIdAndNameResVo = new ClassIdAndNameResVo(classId, className);
            classIdAndNameResVos.add(classIdAndNameResVo);
        }
        return R.success(ResultCode.SEARCH_SUCCESS, classIdAndNameResVos);
    }

    @Override
    public R getClassAllInfo(String token, Long classId) {
        Long userId = JwtUtils.getIdByToken(token);
        HashMap<String, Object> result = new HashMap<>();
        //首先查询班级的基本信息
        Classes classes = classesMapper.selectClassInfoByClassId(classId);
        Long founder = classes.getFounder();
        String nickname = userMapper.selectNicknameByUserId(founder);
        ClassInfoResVo classInfoResVo = new ClassInfoResVo(classes.getClassName(), classes.getSlogan(),
                sdf.format(classes.getCreateTime()), nickname,founder, classes.getAvatar(), classes.getInviteCode());
        result.put("classInfo", classInfoResVo);
        //获取班级成员的信息
        List<ClassMember> classMembers = classMemberMapper.selectUserIdByClassId(classId);
        List<ClassMemberResVo> classMemberResVos = new ArrayList<>();
        for (ClassMember classMember : classMembers) {
            Long userId1 = classMember.getUserId();
            User user = userMapper.selectUserCareerInfo(userId1);
            ClassMemberResVo classMemberResVo = new ClassMemberResVo();
            classMemberResVo.setName(user.getName());
            classMemberResVo.setJobId(user.getJobId());
            classMemberResVo.setJoinTime(sdf.format(classMember.getJoinTime()));
            switch (user.getIdentity()) {
                case 2:
                    classMemberResVo.setIdentity("学生");
                    break;
                case 3:
                    classMemberResVo.setIdentity("教师");
                    break;
            }
            classMemberResVos.add(classMemberResVo);
        }
        result.put("memberInfo", classMemberResVos);
        //获取每次上次信息的状态表,前提该用户是班主任
        if (userId == founder) {
            List<ClassT> classTS = classTMapper.selectClassTByClassId(classId);
            List<ClassTResVo> classTResVos = new ArrayList<>();
            for (ClassT classT : classTS) {
                ClassTResVo classTResVo = new ClassTResVo();
                classTResVo.setId(classT.getId());
                classTResVo.setDate(sdf.format(classT.getStartTime()));
                classTResVo.setStartTime(sdf1.format(classT.getStartTime()));
                classTResVo.setEndTime(sdf1.format(classT.getStartTime()));
                classTResVos.add(classTResVo);
            }
            result.put("classT", classTResVos);
        }
        return R.success(ResultCode.SEARCH_SUCCESS, result);
    }

    @Override
    public R getClassroomStatus(Long classId) {
        System.out.println(classId);
        if (redisService.hasKey(classId+"classroom")){
            return R.success(ResultCode.SEARCH_SUCCESS,true);
        }
        return R.success(ResultCode.SEARCH_SUCCESS,false);
    }

    @Override
    public R getStudentStatus(Long id) {
        List<StatusT> statusTById = statusTMapper.getStatusTById(id);
        return R.success(ResultCode.SEARCH_SUCCESS,statusTById);
    }
}
