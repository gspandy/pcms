package com.pujjr.postloan.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.OtherFee;
import com.pujjr.postloan.vo.OtherFeeDetailVo;
import com.pujjr.postloan.vo.OtherFeeTaskVo;
import com.pujjr.postloan.vo.RefundTaskVo;

public interface OtherFeeMapper {
    int deleteByPrimaryKey(String id);

    int insert(OtherFee record);

    int insertSelective(OtherFee record);

    OtherFee selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OtherFee record);

    int updateByPrimaryKey(OtherFee record);
    
    List<OtherFee> selectListByAppId(String appId);
    
    List<OtherFeeDetailVo> selectFeeDetailList(@Param("otherFeeId")String otherFeeId);
    /**查询其他费用申请任务
	 * 
	 * @param createId
	 * @param applyStatus
	 * @return
	 */
	List<OtherFeeTaskVo> selectApplyOtherFeeTaskList(@Param("createId")String createId,@Param("applyStatus")List<String> applyStatus);
}