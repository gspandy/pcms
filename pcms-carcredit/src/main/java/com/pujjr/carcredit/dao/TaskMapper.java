package com.pujjr.carcredit.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.po.ApplyInfoPo;
import com.pujjr.carcredit.po.ToDoTaskPo;
import com.pujjr.carcredit.vo.ApplyVo;

public interface TaskMapper 
{
	public List<ToDoTaskPo> selectToDoTaskListByAccountId(@Param("accountId")String accountId);
}