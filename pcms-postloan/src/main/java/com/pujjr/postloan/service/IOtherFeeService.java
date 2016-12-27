package com.pujjr.postloan.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.postloan.vo.ApplyOtherFeeVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.OtherFeeTaskVo;
/**
 * 其他费用处理服务
 * @author 150032
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public interface IOtherFeeService 
{
	/**
	 * 提交其他费用申请任务
	 * @param appId
	 * @param vo
	 * @param operId
	 * @throws Exception 
	 */
	public void commitApplyOtherFeeTask(String appId,ApplyOtherFeeVo vo ,String operId) throws Exception;
	/**
	 * 提交审批其他费用申请
	 * @param taskId
	 * @param vo
	 * @throws Exception
	 */
	public void commitApproveOtherFeeTask(String taskId,ApproveResultVo vo) throws Exception;
	/**、
	 * 根据ID查询申请信息
	 * @param id
	 * @return
	 */
	public ApplyOtherFeeVo getApplyOtherFeeTaskById(String id);
	/**
	 * 查询已申请其他费用任务列表
	 * @param createId
	 * @param applyStatus
	 * @return
	 */
	public List<OtherFeeTaskVo> getApplyOtherFeeTaskList(String createId,List<String> applyStatus);
	
}
