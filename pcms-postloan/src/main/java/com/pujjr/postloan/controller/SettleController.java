package com.pujjr.postloan.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.vo.PageVo;
import com.pujjr.base.vo.QueryParamPageVo;
import com.pujjr.postloan.domain.ApplyPublicRepay;
import com.pujjr.postloan.domain.ApplySettle;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.enumeration.EInterestMode;
import com.pujjr.postloan.service.IPlanService;
import com.pujjr.postloan.service.ISettleService;
import com.pujjr.postloan.vo.ApplyPublicRepayVo;
import com.pujjr.postloan.vo.ApplySettleVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.PublicRepayTaskVo;
import com.pujjr.postloan.vo.RemissionFeeItemVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;
import com.pujjr.postloan.vo.SettleFeeItemVo;
import com.pujjr.postloan.vo.SettleTaskVo;
import com.pujjr.utils.Utils;

/**
 * @author tom
 *
 */
@RestController
@RequestMapping("/settle")
public class SettleController extends BaseController {
	@Autowired
	private IPlanService planServiceImpl;
	@Autowired
	private ISettleService settleService;
	/**刷新还款计划表
	 * tom 2016年11月17日
	 * @param appId 申请单号
	 * @param fianceAmt 本金
	 * @param monthRate 月利率
	 * @param period 计息期数
	 * @param valueDate 起息日
	 * @param startPeriod 当期还款周期
	 * @return
	 */
	@RequestMapping(value="/refreshRepayPlan/{appId}/{fianceAmt}/{monthRate}/{period}/{valueDate}/{currPeriod}",method=RequestMethod.POST)
	public void refreshRepayPlan(@PathVariable("appId") String appId,@PathVariable("fianceAmt") double fianceAmt
			,@PathVariable("monthRate") double monthRate,@PathVariable("period") int period
			,@PathVariable("valueDate") String valueDateStr,@PathVariable("currPeriod") int currPeriod){
		System.out.println(appId+"|"+fianceAmt+"|"+monthRate+"|"+period+"|"+valueDateStr);
		List<RepayPlan> repayPlanList = new ArrayList<RepayPlan>();
		Date valueDate = Utils.formateString2Date(valueDateStr, "yyyy-MM-dd");
		planServiceImpl.refreshRepayPlan(appId, fianceAmt, monthRate, period, valueDate,EInterestMode.CPM,currPeriod);
	}
	
	/**获取提前结清后新的还款计划（还款计划表未刷新）
	 * tom 2016年11月17日
	 * @param appId 申请单号
	 * @param fianceAmt 本金
	 * @param monthRate 月利率
	 * @param period 计息期数
	 * @param valueDate 起息日
	 * @param startPeriod 当期还款周期
	 * @return
	 */
	@RequestMapping(value="/refreshRepayPlan/select/{appId}/{fianceAmt}/{monthRate}/{period}/{valueDate}/{eInterestMode}/{currPeriod}",method=RequestMethod.GET)
	public List<RepayPlan> getRefreshRepayPlan(@PathVariable("appId") String appId,@PathVariable("fianceAmt") double fianceAmt
			,@PathVariable("monthRate") double monthRate,@PathVariable("period") int period
			,@PathVariable("valueDate") String valueDateStr,@PathVariable("currPeriod") int currPeriod){
		System.out.println(appId+"|"+fianceAmt+"|"+monthRate+"|"+period+"|"+valueDateStr);
		List<RepayPlan> repayPlanList = new ArrayList<RepayPlan>();
		Date valueDate = Utils.formateString2Date(valueDateStr, "yyyy-MM-dd");
		repayPlanList = planServiceImpl.selectRefreshRepayPlanList(appId, fianceAmt, monthRate, period, valueDate, currPeriod);
		return repayPlanList;
	}
	
	@RequestMapping(value="/getAllSettleFeeItem/{appId}",method=RequestMethod.GET)
	public RepayFeeItemVo getAllSettleFeeItem(@PathVariable String appId,String settleEffectDate) throws Exception
	{
		if(settleEffectDate !="")
		{
			return settleService.getAllSettleFeeItem(appId,Utils.htmlTime2Date(settleEffectDate, "yyyyMMdd"));
		}
		else
		{
			throw new Exception("有效截止日期不能为空");
		}
	}
	
		
	/**
	 *功能：查询指定客户部分提前结清应还项
	 * @param appId 申请单号
	 * @param beginPeriod 开始期数
	 * @param endPeriod 截止期数
	 * @param settleEeffectDate 有效截止日期
	 * @param settleCapital 结清本金
	 * @return
	 */
	@RequestMapping(value="/getPartSettleFeeItem/{appId}/{beginPeriod}/{endPeriod}/{settleEeffectDateStr}",method=RequestMethod.GET)
	public SettleFeeItemVo getPartSettleFeeItem(@PathVariable String appId,@PathVariable int beginPeriod
			, @PathVariable int endPeriod,@PathVariable String settleEeffectDateStr){
		Date settleEeffectDate = Utils.formateString2Date(settleEeffectDateStr, "yyyy-MM-dd");
		return settleService.getPartSettleFeeItem(appId, beginPeriod, endPeriod, settleEeffectDate);
	}
	/**
	 * 功能：提交提前结清申请
	 * 参数：
	 * 	appId-提前结清申请单编号
	 * 	vo-提前结清申请要素
	 * 返回：无
	 * @throws Exception 
	 * **/
	@RequestMapping(value="/commitApplySettleTask/{appId}/{settleType}",method=RequestMethod.POST)
	public void commitApplySettleTask(@PathVariable String appId,@PathVariable String settleType,@RequestBody ApplySettleVo vo,HttpServletRequest request) throws Exception{
		SysAccount account = (SysAccount)request.getAttribute("account");
		settleService.commitApplySettleTask(account.getAccountId(), appId, settleType,vo);
	}
	
	/**
	 * 功能：提交审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * @throws Exception 
	 * **/
	@RequestMapping(value="/commitApproveSettleTask/{taskId}",method=RequestMethod.POST)
	public void commitApproveSettleTask(@PathVariable String taskId,@RequestBody ApproveResultVo vo) throws Exception{
		settleService.commitApproveSettleTask(taskId, vo);
	}
	
	/**
	 * 功能：提交减免审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * @throws Exception 
	 * **/
	@RequestMapping(value="/commitApproveRemissionTask/{taskId}",method=RequestMethod.POST)
	public void commitApproveRemissionTask(@PathVariable String taskId,@RequestBody ApproveResultVo vo) throws Exception{
		settleService.commitApproveRemissionTask(taskId, vo);
	}
	
	/**
	 * 功能：提交确认结清申请
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-减免费项
	 * @throws Exception 
	 * **/
	@RequestMapping(value="/commitApplyConfirmSettleTask/{taskId}",method=RequestMethod.POST)
	public void commitApplyConfirmSettleTask(@PathVariable String taskId,@RequestBody RemissionFeeItemVo vo,HttpServletRequest request) throws Exception{
		SysAccount account = (SysAccount)request.getAttribute("account");
		settleService.commitApplyConfirmSettleTask(account.getAccountId(), taskId, vo);
	}
	
	/**
	 * 功能：提交确认入账
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * @throws Exception 
	 * **/
	@RequestMapping(value="/commitConfirmSettleTask/{taskId}",method=RequestMethod.POST)
	public void commitConfirmSettleTask(@PathVariable String taskId,@RequestBody ApproveResultVo vo) throws Exception{
		settleService.commitConfirmSettleTask(taskId, vo);
	}
	
	/**
	 * 功能：取消结清任务
	 * 参数：
	 * 	taskId-任务ID
	 * 	operId-取消操作者
	 *  cancelComment-取消备注
	 * @throws Exception 
	 * **/
	@RequestMapping(value="/cancelSettleTask/{taskId}/{operId}/{cancelComment}",method=RequestMethod.POST)
	public void cancelSettleTask(@PathVariable String taskId,@PathVariable String operId,@PathVariable String cancelComment) throws Exception{
		settleService.cancelSettleTask(taskId, operId, cancelComment);
	}
	
	@RequestMapping(value="/getApplySettleInfo/{applyId}",method=RequestMethod.GET)
	public ApplySettleVo getApplySettleInfo(@PathVariable String applyId)
	{
		ApplySettleVo vo = new ApplySettleVo();
		SettleFeeItemVo feeItemVo = new SettleFeeItemVo();
		ApplySettle po = settleService.getApplySettleTaskById(applyId);
		
		feeItemVo.setRepayCapital(po.getRepayCapital());
		feeItemVo.setRepayInterest(po.getRepayInterest());
		feeItemVo.setRepayOverdueAmount(po.getRepayOverdueAmount());
		feeItemVo.setOtherAmount(po.getOtherFee());
		feeItemVo.setOtherOverdueAmount(po.getOtherOverdueAmount());
		feeItemVo.setSettleCapital(po.getSettleCapital());
		feeItemVo.setSettleTotalAmount(po.getSettleTotalAmount());
		feeItemVo.setLateFee(po.getLateFee());
		feeItemVo.setSettleAfterAmount(po.getSettleAfterCapital());
		vo.setFeeItem(feeItemVo);
		
		vo.setApplyComment(po.getApplyComment());
		vo.setApplyEffectDate(po.getApplyEndDate());
		vo.setBeginPeriod(po.getSettleStartPeriod());
		vo.setEndPeriod(po.getSettleEndPeriod());
		
		RemissionFeeItemVo remissionFeeItemVo = new RemissionFeeItemVo();
		remissionFeeItemVo.setCapital(po.getRemissionItem().getCapital());
		remissionFeeItemVo.setInterest(po.getRemissionItem().getInterest());
		remissionFeeItemVo.setOverdueAmount(po.getRemissionItem().getOverdueAmount());
		remissionFeeItemVo.setOtherFee(po.getRemissionItem().getOtherFee());
		remissionFeeItemVo.setOtherOverdueAmount(po.getRemissionItem().getOtherOverdueAmount());
		remissionFeeItemVo.setLateFee(po.getRemissionItem().getLateFee());
		
		vo.setRemissionFeeItemVo(remissionFeeItemVo);
		return vo;
	}
	
	@RequestMapping(value="/getApplySettleTaskList",method=RequestMethod.GET)
	public PageVo getApplySettleTaskList(String settleType,QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<SettleTaskVo> list = settleService.getApplySettleTaskList(account.getAccountId(),settleType, null);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
}
