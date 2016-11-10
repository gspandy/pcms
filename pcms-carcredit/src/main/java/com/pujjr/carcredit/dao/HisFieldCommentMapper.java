package com.pujjr.carcredit.dao;

import java.util.List;

import com.pujjr.carcredit.domain.HisFieldComment;

public interface HisFieldCommentMapper {
    int deleteByPrimaryKey(String id);

    int insert(HisFieldComment record);

    int insertSelective(HisFieldComment record);

    HisFieldComment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HisFieldComment record);

    int updateByPrimaryKey(HisFieldComment record);
    
    List<HisFieldComment> selectList();
}