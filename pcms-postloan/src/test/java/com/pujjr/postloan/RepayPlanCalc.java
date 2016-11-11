package com.pujjr.postloan;
import java.util.Date;

import com.pujjr.postloan.enumeration.EInterestMode;
import com.pujjr.postloan.service.impl.InterestAlgorithmImpl;
import com.pujjr.postloan.service.impl.PlanServiceImpl;
import com.pujjr.utils.Utils;

/**测试：还款计划计算
 * @author tom
 */
public class RepayPlanCalc {

	/**
	 * tom 2016年11月11日
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String settleDateStr = "2015-01-31";//结账日
		String valueDateStr = "2015-05-02";//起息日/放款完成日
		int dayCount = 360;
		double yearRate = 0.138;//年利率
		double monthRate = yearRate/12;//月利率
		double darRate = yearRate/dayCount;//日利率
		double fianceAmt = 10000;//融资总金额
		double interestAmt = 10000;//计息总本金
		int period = 2;//融资总期数
		String appId = "WZXK161020N10136";
		Date valueDate = Utils.formateString2Date(valueDateStr, "yyyy-MM-dd");
		new PlanServiceImpl().generateRepayPlan(appId,fianceAmt, monthRate, period, valueDate, EInterestMode.MONTLY);

	}
}