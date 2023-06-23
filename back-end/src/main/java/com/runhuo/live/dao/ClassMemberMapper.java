package com.runhuo.live.dao;

import com.runhuo.live.entity.ClassMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:40
 */
@Mapper
public interface ClassMemberMapper {
    public int insertMember(ClassMember classMember);
    public int deleteMember(Long userId,Long classId);
    public int deleteAllMember(Long classId);
    public List<Long> selectClassIdByUserId(Long userId);
    public List<ClassMember> selectUserIdByClassId(Long classId);
}
