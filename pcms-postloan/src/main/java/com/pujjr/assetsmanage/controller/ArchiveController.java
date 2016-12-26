package com.pujjr.assetsmanage.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.assetsmanage.po.ArchiveDetailPo;
import com.pujjr.assetsmanage.po.ArchiveTaskPo;
import com.pujjr.assetsmanage.service.IArchiveService;
import com.pujjr.assetsmanage.vo.ArchiveDelayVo;
import com.pujjr.assetsmanage.vo.ArchiveLogVo;
import com.pujjr.assetsmanage.vo.ArchivePostVo;
import com.pujjr.assetsmanage.vo.ArchiveSupplyVo;
import com.pujjr.assetsmanage.vo.ReApplyArchiveSupplyVo;
import com.pujjr.assetsmanage.vo.ApplyArchiveBorrowVo;
import com.pujjr.assetsmanage.vo.ApplyArchiveSupplyVo;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.ArchiveStore;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.vo.PageVo;
import com.pujjr.base.vo.QueryParamPageVo;
import com.pujjr.postloan.vo.ApproveResultVo;

@RestController
@RequestMapping(value="/archive")
public class ArchiveController extends BaseController 
{
	@Autowired
	private IArchiveService archiveService;
	
	@RequestMapping(value="/getArchiveToDoTaskList",method=RequestMethod.GET)
	public PageVo getArchiveToDoTaskList(QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = archiveService.getArchiveToDoTaskList(account.getAccountId());
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@RequestMapping(value="/getArchiveTaskDetail/{archiveTaskId}/{archiveTaskType}",method=RequestMethod.GET)
	public List<ArchiveDetailPo> getArchiveTaskDetail(@PathVariable String archiveTaskId,@PathVariable String archiveTaskType)
	{
		return archiveService.getArchiveTaskDetail(archiveTaskId,archiveTaskType);
	}
	
	@RequestMapping(value="/saveArchiveTaskDetail/{archiveTaskId}",method=RequestMethod.POST)
	public void saveArchiveTaskDetail(@PathVariable String archiveTaskId,@RequestBody List<ArchiveDetailPo> records,HttpServletRequest request) 
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		archiveService.saveArchiveTaskDetail(archiveTaskId, records, account.getAccountId());
	}
	
	@RequestMapping(value="/archivePost",method=RequestMethod.POST)
	public void archivePost(@RequestBody ArchivePostVo vo,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		archiveService.archivePost(vo, account.getAccountId());
	}
	
	@RequestMapping(value="/getArchiveList",method=RequestMethod.GET)
	public PageVo getArchiveList(QueryParamPageVo param) 
	{
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = archiveService.getArchiveList();
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@RequestMapping(value="/archiveDelay",method=RequestMethod.POST)
	public void archiveDelay(@RequestBody ArchiveDelayVo vo) throws Exception
	{
		archiveService.archiveDelay(vo);
	}
	
	@RequestMapping(value="/applyArchiveLog",method=RequestMethod.POST)
	public void applyArchiveLog(@RequestBody List<ArchiveLogVo> records,HttpServletRequest request) throws Exception 
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		archiveService.applyArchiveLog(records, account.getAccountId());
	}
	
	@RequestMapping(value="/getArchiveApplyInfo/{archiveTaskId}",method=RequestMethod.GET)
	public ArchiveTaskPo getArchiveApplyInfo(@PathVariable String archiveTaskId) throws IllegalAccessException, InvocationTargetException
	{
		return archiveService.getArchiveApplyInfo(archiveTaskId);
	}
	
	@RequestMapping(value="/commitArchiveLog/{taskId}",method=RequestMethod.POST)
	public void commitArchiveLog(@RequestBody ArchiveTaskPo po,@PathVariable String taskId,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		archiveService.commitArchiveLog(po, taskId,account.getAccountId());
	}
	
	@RequestMapping(value="/createCollectionArchiveTask/{appId}/{archiveType}",method=RequestMethod.POST)
	public void createCollectionArchiveTask(@PathVariable String appId,@PathVariable String archiveType, @RequestBody List<ArchiveDetailPo> records,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		archiveService.createCollectionArchiveTask(appId, archiveType, records, account.getAccountId());
	}
	
	@RequestMapping(value="/applyArchiveSupply/{taskId}",method=RequestMethod.POST)
	public void applyArchiveSupply(@RequestBody ApplyArchiveSupplyVo vo, @PathVariable String taskId,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		archiveService.applyArchiveSupply(vo, taskId, account.getAccountId());
	}
	
	@RequestMapping(value="/getArchiveSupplyInfo/{taskId}",method=RequestMethod.GET)
	public ArchiveSupplyVo getArchiveSupplyInfo(@PathVariable String taskId) throws Exception
	{
		return archiveService.getArchiveSupplyInfo(taskId);
	}
	
	@RequestMapping(value="/commitArchiveSupplyTask/{taskId}",method=RequestMethod.POST)
	public void commitArchiveSupplyTask(@RequestBody ArchiveSupplyVo vo, @PathVariable String taskId,HttpServletRequest request) throws Exception 
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		archiveService.commitArchiveSupplyTask(vo, taskId, account.getAccountId());
	}
	
	@RequestMapping(value="/commitArchiveLogSupplyTask/{taskId}",method=RequestMethod.POST)
	public void commitArchiveLogSupplyTask(@RequestBody ArchiveSupplyVo vo ,@PathVariable String taskId,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		archiveService.commitArchiveLogSupplyTask(vo, taskId, account.getAccountId());
	}
	
	@RequestMapping(value="/reApplyArchiveSupply/{taskId}",method=RequestMethod.POST)
	public void reApplyArchiveSupply(@RequestBody ReApplyArchiveSupplyVo vo, @PathVariable String taskId,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		archiveService.reApplyArchiveSupply(vo, taskId, account.getAccountId());
	}
	
	@RequestMapping(value="/getArchiveStoreList/{appId}",method=RequestMethod.GET)
	public List<ArchiveStore> getArchiveStoreList(@PathVariable String appId)
	{
		return archiveService.getArchiveStoreList(appId);
	}
	
	@RequestMapping(value="/applyArchiveBorrow/{appId}",method=RequestMethod.POST)
	public void applyArchiveBorrow(@RequestBody ApplyArchiveBorrowVo vo,@PathVariable String appId,HttpServletRequest request) 
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		archiveService.applyArchiveBorrow(vo, appId, account.getAccountId());
	}
	
	@RequestMapping(value="/getArchiveBorrowInfo/{borrowId}",method=RequestMethod.GET)
	public ApplyArchiveBorrowVo getArchiveBorrowInfo(@PathVariable String borrowId)
	{
		return archiveService.getArchiveBorrowInfo(borrowId);
	}
	
	@RequestMapping(value="/commitApproveArchiveBorrowTask/{taskId}",method=RequestMethod.POST)
	public void commitApproveArchiveBorrowTask(@PathVariable String taskId, @RequestBody ApproveResultVo vo) throws Exception
	{
		archiveService.commitApproveArchiveBorrowTask(taskId, vo);
	}
	
	@RequestMapping(value="/commitArchiveBorrowReturnTask/{taskId}",method=RequestMethod.POST)
	public void commitArchiveBorrowReturnTask(@RequestBody ApplyArchiveBorrowVo vo, @PathVariable String taskId) throws Exception 
	{
		archiveService.commitArchiveBorrowReturnTask(vo, taskId);
	}
	
	@RequestMapping(value="/backArchiveBorroswTask/{taskId}",method=RequestMethod.POST)
	public void backArchiveBorrowTask(@PathVariable String taskId,@RequestBody String message) throws Exception
	{
		archiveService.backArchiveBorrowTask(taskId, message);
	}
	
	@RequestMapping(value="/reApplyArchiveBorrow/{taskId}",method=RequestMethod.POST)
	public void reApplyArchiveBorrow(@PathVariable String taskId, @RequestBody ApplyArchiveBorrowVo vo) throws Exception 
	{
		archiveService.reApplyArchiveBorrow(taskId, vo);
	}
}