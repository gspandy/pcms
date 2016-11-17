package com.pujjr.postloan.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.xmlbeans.impl.jam.annotation.LineDelimitedTagParser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.enumeration.RepayStatus;
import com.pujjr.postloan.po.RepaySchedulePo;
import com.pujjr.postloan.service.IInterestAlgorithm;
import com.pujjr.utils.Utils;


/**
 * @author tom
 *
 */
@Service
public class InterestAlgorithmImpl implements IInterestAlgorithm {
	/**
	 * 获取首期还款日期
	 * tom 2016年11月2日
	 * @param loadDate
	 * @return
	 */
	public static Date getFirstRepayDate(Date valueDate){
		Date firstRepayDate = null;
		Calendar firstRepayCl = Calendar.getInstance();
		firstRepayCl.setTime(valueDate);
		firstRepayCl.add(Calendar.MONTH, 1);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		return firstRepayCl.getTime();
	}
	
	@Override
	public RepaySchedulePo cpmInterest(double fianceAmt, double monthRate, int period, Date valueDate) {
		RepaySchedulePo rsp = new RepaySchedulePo();
		List<RepayPlan> repayPlanList = new LinkedList<RepayPlan>();
		Date firstClosingDate = this.getFirstRepayDate(valueDate);
		Calendar firstClosingCl = Calendar.getInstance();
		firstClosingCl.setTime(firstClosingDate);
		
		BigDecimal remainCapital = new BigDecimal(fianceAmt);
//		BigDecimal fenzi = new BigDecimal(fianceAmt).multiply(new BigDecimal(monthRate)).multiply(new BigDecimal(Math.pow(1+monthRate, period)));
		BigDecimal fenzi = new BigDecimal(fianceAmt*monthRate*Math.pow(1+monthRate, period));
		BigDecimal fenmu = new BigDecimal(Math.pow(1+monthRate, period)-1);
		System.out.println("分子："+fenzi);
		System.out.println("分母："+fenmu);
		BigDecimal monthRepay = fenzi.divide(fenmu,2);
		BigDecimal repayAmt = monthRepay.multiply(new BigDecimal(period));//总还款金额
		BigDecimal interestAmt = new BigDecimal(0);//总利息
		for (int i = 0; i < period; i++) {
			RepayPlan repayPlan = new RepayPlan();
			Calendar valueCl = Calendar.getInstance();//起息日日历
			Calendar closingCl = Calendar.getInstance();//到期还款日日历
			if(i == 0){
				valueCl.setTime(valueDate);
				closingCl.setTime(firstClosingDate);
				repayPlan.setValueDate(Utils.formateDate(valueCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setClosingDate(Utils.formateDate(closingCl.getTime(), "yyyy-MM-dd"));
//				System.out.println("结账日:"+Utils.formateDate2String(firstClosingDate, "yyyy-MM-dd"));
			}else{
				closingCl.setTime(firstClosingDate);
				closingCl.add(Calendar.MONTH, i);
				BeanUtils.copyProperties(closingCl, valueCl);
				valueCl.add(Calendar.MONTH, -1);
				valueCl.add(Calendar.DAY_OF_MONTH, 1);
				repayPlan.setValueDate(Utils.formateDate(valueCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setClosingDate(Utils.formateDate(closingCl.getTime(), "yyyy-MM-dd"));
//				System.out.println(Utils.formateDate2String(valueCl.getTime(), "yyyy-MM-dd")+"|"+Utils.formateDate2String(closingCl.getTime(), "yyyy-MM-dd"));
			}
			BigDecimal currInterest = remainCapital.multiply(new BigDecimal(monthRate));//当月利息
			BigDecimal currCapital = monthRepay.subtract(currInterest);//当月本金
			remainCapital = remainCapital.subtract(currCapital);
			interestAmt = interestAmt.add(currInterest);
			repayPlan.setPeriod(i+1);
		
			repayPlan.setRepayTotalAmount(Utils.formateDouble2Double(monthRepay, 2));
			repayPlan.setRepayInterest(Utils.formateDouble2Double(currInterest, 2));
			repayPlan.setRepayCapital(Utils.formateDouble2Double(currCapital, 2));
			repayPlan.setRemainCapital(Utils.formateDouble2Double(remainCapital, 2));
			repayPlanList.add(repayPlan);
			rsp.setRepayPlanList(repayPlanList);
			
			System.out.println(Utils.formateDate2String(repayPlan.getValueDate(), "yyyy-MM-dd")
					+"|"+Utils.formateDate2String(repayPlan.getClosingDate(), "yyyy-MM-dd")
					+"|第"+repayPlan.getPeriod()+"期"
					+"|归还本金："+repayPlan.getRepayCapital()
					+"|归还利息："+repayPlan.getRepayInterest()
					+"|月供："+repayPlan.getRepayTotalAmount()
					+"|剩余本金："+repayPlan.getRemainCapital());
		}
		rsp.setInterestAmt(Utils.formateDouble2Double(interestAmt, 2));
		rsp.setMonthRepay(Utils.formateDouble2Double(monthRepay, 2));
		rsp.setMortgageAmt(fianceAmt);
		rsp.setPeriod(period);
		rsp.setRepayAmt(Utils.formateDouble2Double(repayAmt, 2));
		System.out.println("总利息："+interestAmt.setScale(2, RoundingMode.HALF_EVEN));
		return rsp;
	}

	/* (non-Javadoc)
	 * @see com.pujjr.postloan.service.IInterestAlgorithm#monthlyIntetrest(double, double, int, java.util.Date)
	 */
	@Override
	public RepaySchedulePo monthlyIntetrest(double fianceAmt, double monthRate, int period, Date valueDate) {
		RepaySchedulePo rsp = new RepaySchedulePo();
		List<RepayPlan> rspList = new LinkedList<RepayPlan>();
		Date firstClosingDate = this.getFirstRepayDate(valueDate);
//		Calendar dueCalendar = Calendar.getInstance();
//		dueCalendar.setTime(firstClosingDate);
		BigDecimal monthIntetrest = new BigDecimal(fianceAmt * monthRate);//月利息
		BigDecimal interestAmt = new BigDecimal(0);//贷款总利息
		BigDecimal mortgageAmt = new BigDecimal(fianceAmt);//贷款总金额
		BigDecimal repayAmt = new BigDecimal(0);
		BigDecimal remainCaptital = new BigDecimal(fianceAmt);
		for (int i = 0; i < period; i++) {
			RepayPlan repayPlan = new RepayPlan();
			Calendar valueCl = Calendar.getInstance();
			Calendar closingCl = Calendar.getInstance();
			if(i == 0){
				valueCl.setTime(valueDate);
				closingCl.setTime(firstClosingDate);
				repayPlan.setPeriod(i+1);
				repayPlan.setValueDate(Utils.formateDate(valueCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setClosingDate(Utils.formateDate(closingCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setRepayCapital(0.0);
				repayPlan.setRepayInterest(Utils.formateDouble2Double(monthIntetrest, 2));
				repayPlan.setRepayTotalAmount(Utils.formateDouble2Double(monthIntetrest, 2));
				repayPlan.setRemainCapital(Utils.formateDouble2Double(remainCaptital, 2));
				interestAmt = interestAmt.add(monthIntetrest);
			}else if(i == period-1){
				closingCl.setTime(firstClosingDate);
				closingCl.add(Calendar.MONTH, i);
				BeanUtils.copyProperties(closingCl, valueCl);
				valueCl.add(Calendar.MONTH, -1);
				valueCl.add(Calendar.DAY_OF_MONTH, 1);
				repayPlan.setPeriod(i+1);
				repayPlan.setValueDate(Utils.formateDate(valueCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setClosingDate(Utils.formateDate(closingCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setRepayCapital(0.0);
				repayPlan.setRepayInterest(Utils.formateDouble2Double(monthIntetrest, 2));
				repayPlan.setRepayTotalAmount(Utils.formateDouble2Double(remainCaptital.add(monthIntetrest), 2));
				repayPlan.setRemainCapital(0.0);
				interestAmt = interestAmt.add(monthIntetrest);
			}else{
				closingCl.setTime(firstClosingDate);
				closingCl.add(Calendar.MONTH, i);
				BeanUtils.copyProperties(closingCl, valueCl);
				valueCl.add(Calendar.MONTH, -1);
				valueCl.add(Calendar.DAY_OF_MONTH, 1);
				repayPlan.setValueDate(Utils.formateDate(valueCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setClosingDate(Utils.formateDate(closingCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setRepayCapital(0.0);
				repayPlan.setRepayInterest(Utils.formateDouble2Double(monthIntetrest, 2));
				repayPlan.setRepayTotalAmount(Utils.formateDouble2Double(monthIntetrest, 2));
				repayPlan.setPeriod(i+1);
				repayPlan.setRemainCapital(Utils.formateDouble2Double(repayAmt, 2));
				interestAmt = interestAmt.add(monthIntetrest);
			}
			repayAmt = repayAmt.add(new BigDecimal(repayPlan.getRepayTotalAmount()));//
			rspList.add(repayPlan);
			System.out.println(Utils.formateDate2String(repayPlan.getValueDate(), "yyyy-MM-dd")
					+"|"+Utils.formateDate2String(repayPlan.getClosingDate(), "yyyy-MM-dd")
					+"|第"+repayPlan.getPeriod()+"期"
					+"|归还本金："+repayPlan.getRepayCapital()
					+"|归还利息："+repayPlan.getRepayInterest()
					+"|月供："+repayPlan.getRepayTotalAmount()
					+"|剩余本金："+repayPlan.getRemainCapital());
//			System.out.println("^^^^^^^^^^^^^^"+JSONObject.toJSONString(rspList));
		}
//		repayAmt.add(interestAmt);
		rsp.setPeriod(period);
		rsp.setInterestAmt(Utils.formateDouble2Double(interestAmt, 2));
		rsp.setMonthRepay(Utils.formateDouble2Double(monthIntetrest, 2));
		rsp.setMortgageAmt(Utils.formateDouble2Double(mortgageAmt, 2));
		rsp.setRepayAmt(Utils.formateDouble2Double(repayAmt, 2));
		rsp.setRepayPlanList(rspList);
//		System.out.println("^^^^^^^^^^^^^^"+JSONObject.toJSONString(rsp));
		return rsp;
	}

	/* (non-Javadoc)
	 * @see com.pujjr.postloan.service.IInterestAlgorithm#onetimeInterest(double, double, int, java.util.Date)
	 */
	@Override
	public RepaySchedulePo onetimeInterest(double fianceAmt, double monthRate, int period, Date valueDate) {
		RepaySchedulePo rsp = new RepaySchedulePo();
		List<RepayPlan> repayPlanList = new LinkedList<RepayPlan>();
		Date firstClosingDate = this.getFirstRepayDate(valueDate);
		BigDecimal mortgageAmt = new BigDecimal(fianceAmt);
		BigDecimal repayAmt = mortgageAmt;
		BigDecimal montRepay = new BigDecimal(fianceAmt/period);
		BigDecimal remainCapital = new BigDecimal(fianceAmt);
		rsp.setPeriod(period);
		rsp.setMonthRepay(Utils.formateDouble2Double(montRepay, 2));
		rsp.setMortgageAmt(Utils.formateDouble2Double(mortgageAmt, 2));
		rsp.setInterestAmt(0);
		rsp.setRepayAmt(Utils.formateDouble2Double(repayAmt, 2));
		for (int i = 0; i < period; i++) {
			RepayPlan repayPlan = new RepayPlan();
			remainCapital = remainCapital.subtract(montRepay);
//			System.out.println("repayAmt:"+Utils.formateDouble2Double(repayAmt, 2));
			Calendar valueCl = Calendar.getInstance();
			Calendar closingCl = Calendar.getInstance();
			if(i == 0){
				valueCl.setTime(valueDate);
				closingCl.setTime(firstClosingDate);
				repayPlan.setValueDate(Utils.formateDate(valueCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setClosingDate(Utils.formateDate(closingCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setRepayInterest(0.00);
				repayPlan.setRepayCapital(Utils.formateDouble2Double(montRepay, 2));
				repayPlan.setRepayTotalAmount(Utils.formateDouble2Double(montRepay, 2));
				repayPlan.setPeriod(i+1);
				repayPlan.setRemainCapital(Utils.formateDouble2Double(remainCapital, 2));
			}else{
				closingCl.setTime(firstClosingDate);
				closingCl.add(Calendar.MONTH, i);
				BeanUtils.copyProperties(closingCl, valueCl);
				valueCl.add(Calendar.MONTH, -1);
				valueCl.add(Calendar.DAY_OF_MONTH, 1);
				repayPlan.setValueDate(Utils.formateDate(valueCl.getTime(), "yyyy-MM--dd"));
				repayPlan.setClosingDate(Utils.formateDate(closingCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setRepayInterest(0.00);
				repayPlan.setRepayCapital(Utils.formateDouble2Double(montRepay, 2));
				repayPlan.setRepayTotalAmount(Utils.formateDouble2Double(montRepay, 2));
				repayPlan.setPeriod(i+1);
				repayPlan.setRemainCapital(Utils.formateDouble2Double(remainCapital, 2));
			}
			repayPlanList.add(repayPlan);
			System.out.println(Utils.formateDate2String(repayPlan.getValueDate(), "yyyy-MM-dd")
					+"|"+Utils.formateDate2String(repayPlan.getClosingDate(), "yyyy-MM-dd")
					+"|第"+repayPlan.getPeriod()+"期"
					+"|归还本金："+repayPlan.getRepayCapital()
					+"|归还利息："+repayPlan.getRepayInterest()
					+"|月供："+repayPlan.getRepayTotalAmount()
					+"|剩余本金："+repayPlan.getRemainCapital());
		}
		rsp.setRepayPlanList(repayPlanList);
		return rsp;
	}

	/* (non-Javadoc)
	 * @see com.pujjr.postloan.service.IInterestAlgorithm#constInterest(double, double, int, java.util.Date)
	 */
	@Override
	public RepaySchedulePo constInterest(double fianceAmt, double monthRate, int period, Date valueDate) {
		RepaySchedulePo rsp = new RepaySchedulePo();
		List<RepayPlan> repayPlanList = new LinkedList<RepayPlan>();
		Date firstClosingDate = this.getFirstRepayDate(valueDate);
		Calendar firstDueCl = Calendar.getInstance();
		firstDueCl.setTime(firstClosingDate);
		BigDecimal monthInterest = new BigDecimal(fianceAmt * monthRate);
		BigDecimal interestAmt = new BigDecimal(fianceAmt * monthRate * period);
		BigDecimal mortgageAmt = new BigDecimal(fianceAmt);
		BigDecimal monthCaptital = new BigDecimal(fianceAmt / period);
		BigDecimal monthRepay = monthInterest.add(monthCaptital);
		BigDecimal repayAmt = interestAmt.add(mortgageAmt);
		BigDecimal remainCapital = new BigDecimal(fianceAmt);
		for (int i = 0; i < period; i++) {
			RepayPlan repayPlan = new RepayPlan();
			remainCapital = remainCapital.subtract(monthCaptital);
			Calendar valueCl = Calendar.getInstance();
			Calendar closingCl = Calendar.getInstance();
			if(i == 0){
				valueCl.setTime(valueDate);
				closingCl.setTime(firstClosingDate);
				repayPlan.setValueDate(Utils.formateDate(valueCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setClosingDate(Utils.formateDate(closingCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setRepayInterest(Utils.formateDouble2Double(monthInterest, 2));
				repayPlan.setRepayCapital(Utils.formateDouble2Double(monthCaptital, 2));
				repayPlan.setRepayTotalAmount(Utils.formateDouble2Double(monthRepay, 2));
				repayPlan.setPeriod(i+1);
				repayPlan.setRemainCapital(Utils.formateDouble2Double(remainCapital, 2));
			}else{
//				firstDueCl.add(Calendar.MONTH, 1);
				closingCl.setTime(firstClosingDate);
				closingCl.add(Calendar.MONTH, i);
				BeanUtils.copyProperties(closingCl, valueCl);
				valueCl.add(Calendar.MONTH, -1);
				valueCl.add(Calendar.DAY_OF_MONTH, 1);
//				System.out.println(Utils.formateDate2String(firstDueCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setValueDate(Utils.formateDate(valueCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setClosingDate(Utils.formateDate(closingCl.getTime(), "yyyy-MM-dd"));
				repayPlan.setRepayInterest(Utils.formateDouble2Double(monthInterest, 2));
				repayPlan.setRepayCapital(Utils.formateDouble2Double(monthCaptital, 2));
				repayPlan.setRepayTotalAmount(Utils.formateDouble2Double(monthRepay, 2));
				repayPlan.setPeriod(i+1);
				repayPlan.setRemainCapital(Utils.formateDouble2Double(remainCapital, 2));
			}
//			System.out.println(JSONObject.toJSONString(repayPlan));
//			repayPlan.setValueDate(valueDate);
			repayPlanList.add(repayPlan);
			System.out.println(Utils.formateDate2String(repayPlan.getValueDate(), "yyyy-MM-dd")
					+"|"+Utils.formateDate2String(repayPlan.getClosingDate(), "yyyy-MM-dd")
					+"|第"+repayPlan.getPeriod()+"期"
					+"|归还本金："+repayPlan.getRepayCapital()
					+"|归还利息："+repayPlan.getRepayInterest()
					+"|月供："+repayPlan.getRepayTotalAmount()
					+"|剩余本金："+repayPlan.getRemainCapital());
		}
		rsp.setPeriod(period);
		rsp.setMonthRepay(Utils.formateDouble2Double(monthRepay, 2));
		rsp.setMortgageAmt(Utils.formateDouble2Double(mortgageAmt, 2));
		rsp.setInterestAmt(Utils.formateDouble2Double(interestAmt, 2));
		rsp.setRepayAmt(Utils.formateDouble2Double(repayAmt, 2));
		rsp.setRepayPlanList(repayPlanList);
		return rsp;
	}

}
