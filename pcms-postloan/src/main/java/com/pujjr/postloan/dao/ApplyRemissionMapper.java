package com.pujjr.postloan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.ApplyRemission;
import com.pujjr.postloan.vo.RefundTaskVo;
import com.pujjr.postloan.vo.RemissionTaskVo;

public interface ApplyRemissionMapper {
    int deleteByPrimaryKey(String id);

	int insert(ApplyRemission record);

	int insertSelective(ApplyRemission record);

	ApplyRemission selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyRemission record);

	int updateByPrimaryKey(ApplyRemission record);
	
	/**查询减免申请任务
	 * 
	 * @param createId
	 * @param applyStatus
	 * @return
	 */
	List<RemissionTaskVo> selectApplyRemissionTaskList(@Param("createId")String createId,@Param("applyStatus")List<String> applyStatus);
}