package com.pujjr.postloan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.ApplyPublicRepay;
import com.pujjr.postloan.vo.PublicRepayTaskVo;

public interface ApplyPublicRepayMapper {
    int deleteByPrimaryKey(String id);

	int insert(ApplyPublicRepay record);

	int insertSelective(ApplyPublicRepay record);

	ApplyPublicRepay selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyPublicRepay record);

	int updateByPrimaryKey(ApplyPublicRepay record);
	/**查询对公还款申请任务
	 * 
	 * @param createId
	 * @param applyStatus
	 * @return
	 */
	List<PublicRepayTaskVo> selectApplyPublicRepayTaskList(@Param("createId")String createId,@Param("applyStatus")List<String> applyStatus);

}