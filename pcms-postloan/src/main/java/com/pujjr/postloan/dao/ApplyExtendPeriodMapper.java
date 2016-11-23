package com.pujjr.postloan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.ApplyExtendPeriod;
import com.pujjr.postloan.vo.ExtendPeriodTaskVo;
import com.pujjr.postloan.vo.PublicRepayTaskVo;
import com.pujjr.postloan.vo.SettleTaskVo;

public interface ApplyExtendPeriodMapper {
    int deleteByPrimaryKey(String id);

	int insert(ApplyExtendPeriod record);

	int insertSelective(ApplyExtendPeriod record);

	ApplyExtendPeriod selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyExtendPeriod record);

	int updateByPrimaryKey(ApplyExtendPeriod record);
	
	ApplyExtendPeriod selectByProcInstId(String procInstId);
	
	/**查询展期申请任务
	 * 
	 * @param createId
	 * @param applyStatus
	 * @return
	 */
	List<ExtendPeriodTaskVo> selectApplyExtendPeriodTaskList(@Param("createId")String createId,@Param("applyStatus")List<String> applyStatus);
	
}