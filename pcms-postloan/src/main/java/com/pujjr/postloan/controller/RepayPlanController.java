package com.pujjr.postloan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pujjr.postloan.service.IPlanService;
import com.pujjr.postloan.vo.RepayPlanVo;

/**
 * @author tom
 *
 */
@Controller
@RequestMapping("/repay")
public class RepayPlanController {
	@Autowired
	private IPlanService planServiceImpl;
	@ResponseBody
	@RequestMapping(value="/select/list/{appId}/{period}",method=RequestMethod.GET)
	public List<RepayPlanVo> selectRepayPlanList(@PathVariable("appId") String appId,@PathVariable("period") int period){
		System.out.println("appId:"+appId+"|period:"+period);
		return planServiceImpl.selectRepayPlanList(appId, period);
	}
	
	@ResponseBody
	@RequestMapping(value="/select/{appId}/{period}",method=RequestMethod.GET)
	public RepayPlanVo selectRepayPlan(@PathVariable("appId") String appId,@PathVariable("period") int period){
		System.out.println("appId:"+appId+"|period:"+period);
		return planServiceImpl.selectRepayPlay(appId, period);
	}
}
