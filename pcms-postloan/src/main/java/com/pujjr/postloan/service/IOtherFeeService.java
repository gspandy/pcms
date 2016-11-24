package com.pujjr.postloan.service;

import org.springframework.stereotype.Service;

import com.pujjr.postloan.vo.ApplyOtherFeeVo;
import com.pujjr.postloan.vo.ApproveResultVo;
/**
 * 其他费用处理服务
 * @author 150032
 *
 */
@Service
public interface IOtherFeeService 
{
	public void commitApplyOtherFeeTask(String appId,ApplyOtherFeeVo vo ,String operId);
	
	public void commitApproveOtherFeeTask(String taskId,ApproveResultVo vo) throws Exception;
	
	public ApplyOtherFeeVo getApplyOtherFeeTaskById(String id);
}
