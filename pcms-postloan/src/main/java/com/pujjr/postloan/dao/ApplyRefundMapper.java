package com.pujjr.postloan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.ApplyRefund;
import com.pujjr.postloan.vo.PublicRepayTaskVo;
import com.pujjr.postloan.vo.RefundTaskVo;

public interface ApplyRefundMapper {
    int deleteByPrimaryKey(String id);

	int insert(ApplyRefund record);

	int insertSelective(ApplyRefund record);

	ApplyRefund selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyRefund record);

	int updateByPrimaryKey(ApplyRefund record);
	
	/**查询退款申请任务
	 * 
	 * @param createId
	 * @param applyStatus
	 * @return
	 */
	List<RefundTaskVo> selectApplyRefundTaskList(@Param("createId")String createId,@Param("applyStatus")List<String> applyStatus);
}