package com.pujjr.postloan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.ApplySettle;
import com.pujjr.postloan.vo.PublicRepayTaskVo;
import com.pujjr.postloan.vo.SettleTaskVo;

public interface ApplySettleMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplySettle record);

    int insertSelective(ApplySettle record);

    ApplySettle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplySettle record);

    int updateByPrimaryKey(ApplySettle record);
    
    ApplySettle selectByProcInstId(String procInstId);
    
    /**查询对公还款申请任务
	 * 
	 * @param createId
	 * @param applyStatus
	 * @return
	 */
	List<SettleTaskVo> selectApplySettleTaskList(@Param("createId")String createId,@Param("settleType")String settleType,@Param("applyStatus")List<String> applyStatus);
	
}