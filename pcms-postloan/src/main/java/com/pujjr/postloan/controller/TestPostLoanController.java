package com.pujjr.postloan.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pujjr.postloan.enumeration.EInterestMode;
import com.pujjr.postloan.service.IPlanService;
import com.pujjr.utils.Utils;

/**
 * @author tom
 *
 */
@Controller
@RequestMapping("/postloan")
public class TestPostLoanController {
	@Autowired
	private IPlanService playServiceImpl;
	
	/**生成还款计划测试
	 * tom 2016年11月11日
	 */
	@ResponseBody
	@RequestMapping(value="/repayplan/generate",method=RequestMethod.POST)
	public void generateRepayPlay(){
		String settleDateStr = "2015-01-31";//结账日
		String valueDateStr = "2015-05-02";//起息日/放款完成日
		int dayCount = 360;
		double yearRate = 0.0325;//年利率
		double monthRate = yearRate/12;//月利率
		double darRate = yearRate/dayCount;//日利率
		double fianceAmt = 14000;//融资总金额
		double interestAmt = 14000;//计息总本金
		int period = 2;//融资总期数
		String appId = "WZXK161020N10136";
		Date valueDate = Utils.formateString2Date(valueDateStr, "yyyy-MM-dd");
		playServiceImpl.generateRepayPlan(appId,fianceAmt, monthRate, period, valueDate, EInterestMode.CPM);
	}
}
