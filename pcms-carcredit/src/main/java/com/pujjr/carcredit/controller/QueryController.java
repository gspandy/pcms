package com.pujjr.carcredit.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.vo.PageVo;
import com.pujjr.carcredit.service.IQueryService;
import com.pujjr.carcredit.vo.QueryParamApplyVo;

@RestController
@RequestMapping(value="/query")
public class QueryController extends BaseController 
{
	@Autowired
	private IQueryService queryService;
	
	@RequestMapping(value="/applyList",method=RequestMethod.GET)
	public PageVo queryApplyList(QueryParamApplyVo param)
	{
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = queryService.queryApplyList(param);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
}
