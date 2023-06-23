package com.runhuo.live.dao;

import com.runhuo.live.entity.Classes;
import com.runhuo.live.vo.R;
import org.apache.ibatis.annotations.Mapper;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:40
 */
@Mapper
public interface ClassesMapper {
    public int createClass(Classes classes);
    public Long selectClassIdByInviteCode(String inviteCode);
    public int dissolveClass(Long classId,Long founder);

    public String selectClassNameByClassId(Long classId);
    public Classes selectClassInfoByClassId(Long classId);
}
