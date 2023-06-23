package com.runhuo.live.dao;

import com.runhuo.live.entity.ClassT;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/21 15:21
 */
@Mapper
public interface ClassTMapper {
    public List<ClassT> selectClassTByClassId(Long classId);
    public int insertClassT(ClassT classT);
}
