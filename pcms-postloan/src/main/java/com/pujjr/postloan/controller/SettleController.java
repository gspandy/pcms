package com.pujjr.postloan.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.enumeration.EInterestMode;
import com.pujjr.postloan.service.IPlanService;
import com.pujjr.postloan.service.ISettleService;
import com.pujjr.postloan.vo.RepayFeeItemVo;
import com.pujjr.utils.Utils;

/**
 * @author tom
 *
 */
@Controller
@RequestMapping("/settle")
public class SettleController {
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
	@ResponseBody
	@RequestMapping(value="/refreshRepayPlan/{appId}/{fianceAmt}/{monthRate}/{period}/{valueDate}/{eInterestMode}/{currPeriod}",method=RequestMethod.POST)
	public void refreshRepayPlan(@PathVariable("appId") String appId,@PathVariable("fianceAmt") double fianceAmt
			,@PathVariable("monthRate") double monthRate,@PathVariable("period") int period
			,@PathVariable("valueDate") String valueDateStr,@PathVariable("currPeriod") int currPeriod){
		System.out.println(appId+"|"+fianceAmt+"|"+monthRate+"|"+period+"|"+valueDateStr);
		List<RepayPlan> repayPlanList = new ArrayList<RepayPlan>();
		Date valueDate = Utils.formateString2Date(valueDateStr, "yyyy-MM-dd");
		planServiceImpl.refreshRepayPlan(appId, fianceAmt, monthRate, period, valueDate, currPeriod);
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
	@ResponseBody
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
	
	@RequestMapping(value="/getAllSettleFeeItem/{appId}/{settleEffectDate}",method=RequestMethod.GET)
	public RepayFeeItemVo getAllSettleFeeItem(@PathVariable String appId,@PathVariable Date settleEffectDate)
	{
		return settleService.getAllSettleFeeItem(appId, settleEffectDate);
	}
}
