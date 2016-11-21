package com.pujjr.postloan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.ApplyAlterRepayDate;
import com.pujjr.postloan.vo.AlterRepayDateTaskVo;
import com.pujjr.postloan.vo.SettleTaskVo;

public interface ApplyAlterRepayDateMapper {
    int deleteByPrimaryKey(String id);

	int insert(ApplyAlterRepayDate record);

	int insertSelective(ApplyAlterRepayDate record);

	ApplyAlterRepayDate selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyAlterRepayDate record);

	int updateByPrimaryKey(ApplyAlterRepayDate record);
	/**
	 * 查询变更还款日历史任务信息
	 * @param createId
	 * @param applyStatus
	 * @return
	 */
	List<AlterRepayDateTaskVo> selectApplyAlterRepayDateTaskList(@Param("createId")String createId,@Param("applyStatus")List<String> applyStatus);
		
}