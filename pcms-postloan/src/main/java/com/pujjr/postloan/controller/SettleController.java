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

import com.pujjr.base.controller.BaseController;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.enumeration.EInterestMode;
import com.pujjr.postloan.service.IPlanService;
import com.pujjr.postloan.service.ISettleService;
import com.pujjr.postloan.vo.ApplySettleVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;
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
	 * @param eInterestMode 计息方式
	 * @param startPeriod 当期还款周期
	 * @return
	 */
	@RequestMapping(value="/refreshRepayPlan/{appId}/{fianceAmt}/{monthRate}/{period}/{valueDate}/{eInterestMode}/{currPeriod}",method=RequestMethod.POST)
	public void refreshRepayPlan(@PathVariable("appId") String appId,@PathVariable("fianceAmt") double fianceAmt
			,@PathVariable("monthRate") double monthRate,@PathVariable("period") int period
			,@PathVariable("valueDate") String valueDateStr,@PathVariable("eInterestMode") String intrestMode
			,@PathVariable("currPeriod") int currPeriod){
		System.out.println(appId+"|"+fianceAmt+"|"+monthRate+"|"+period+"|"+valueDateStr+"|"+intrestMode+"|"+currPeriod);
		List<RepayPlan> repayPlanList = new ArrayList<RepayPlan>();
		Date valueDate = Utils.formateString2Date(valueDateStr, "yyyy-MM-dd");
		EInterestMode eInterestMode = null;
		switch(intrestMode){
		case "hkfs01":
			eInterestMode = EInterestMode.CPM;
			break;
		case "hkfs02":
			eInterestMode = EInterestMode.CONST;
			break;
		case "hkfs03":
			eInterestMode = EInterestMode.ONETIME;
			break;
		case "hkfs04":
			eInterestMode = EInterestMode.MONTLY;
			break;
		}
		planServiceImpl.refreshRepayPlan(appId, fianceAmt, monthRate, period, valueDate, eInterestMode, currPeriod);
	}
	
	/**获取提前结清后新的还款计划（还款计划表未刷新）
	 * tom 2016年11月17日
	 * @param appId 申请单号
	 * @param fianceAmt 本金
	 * @param monthRate 月利率
	 * @param period 计息期数
	 * @param valueDate 起息日
	 * @param eInterestMode 计息方式
	 * @param startPeriod 当期还款周期
	 * @return
	 */
	@RequestMapping(value="/refreshRepayPlan/select/{appId}/{fianceAmt}/{monthRate}/{period}/{valueDate}/{eInterestMode}/{currPeriod}",method=RequestMethod.GET)
	public List<RepayPlan> getRefreshRepayPlan(@PathVariable("appId") String appId,@PathVariable("fianceAmt") double fianceAmt
			,@PathVariable("monthRate") double monthRate,@PathVariable("period") int period
			,@PathVariable("valueDate") String valueDateStr,@PathVariable("eInterestMode") String intrestMode
			,@PathVariable("currPeriod") int currPeriod){
		System.out.println(appId+"|"+fianceAmt+"|"+monthRate+"|"+period+"|"+valueDateStr+"|"+intrestMode+"|"+currPeriod);
		List<RepayPlan> repayPlanList = new ArrayList<RepayPlan>();
		Date valueDate = Utils.formateString2Date(valueDateStr, "yyyy-MM-dd");
		EInterestMode eInterestMode = null;
		switch(intrestMode){
		case "hkfs01":
			eInterestMode = EInterestMode.CPM;
			break;
		case "hkfs02":
			eInterestMode = EInterestMode.CONST;
			break;
		case "hkfs03":
			eInterestMode = EInterestMode.ONETIME;
			break;
		case "hkfs04":
			eInterestMode = EInterestMode.MONTLY;
			break;
		}
		repayPlanList = planServiceImpl.selectRefreshRepayPlanList(appId, fianceAmt, monthRate, period, valueDate, eInterestMode, currPeriod);
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
	
	public void commitApplySettleTask(String appId,ApplySettleVo vo,HttpServletRequest request)
	{
		
	}
}
