package com.pujjr.assetsmanage.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.pujjr.assetsmanage.dao.InsuranceClaimsMapper;
import com.pujjr.assetsmanage.domain.InsuranceClaims;
import com.pujjr.assetsmanage.enumeration.CollectionTaskType;
import com.pujjr.assetsmanage.service.IInsuranceManageService;
import com.pujjr.assetsmanage.vo.ApplyInsuranceVo;
import com.pujjr.base.dao.InsuranceHisMapper;
import com.pujjr.base.domain.InsuranceHis;
import com.pujjr.base.domain.SysParam;
import com.pujjr.base.po.InsuranceHisPo;
import com.pujjr.base.service.ISysParamService;
import com.pujjr.carcredit.constant.InsuranceType;
import com.pujjr.carcredit.domain.SignFinanceDetail;
import com.pujjr.carcredit.service.ISignContractService;
import com.pujjr.file.domain.Directory;
import com.pujjr.file.service.IDirectoryService;
import com.pujjr.file.service.IFileService;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.utils.Utils;
@Service
@Transactional(rollbackFor=Exception.class)
public class InsuranceManageServiceImpl implements IInsuranceManageService {

	@Autowired
	private ISignContractService signContractService;
	@Autowired
	private InsuranceHisMapper insuranceHisDao;
	@Autowired
	private IRunWorkflowService runWorkflowService;
	@Autowired
	private IFileService fileService;
	@Autowired
	private IDirectoryService directoryService;
	@Autowired
	private ISysParamService sysParamService;
	@Autowired
	private InsuranceClaimsMapper insuranceClaimsDao;
	@Override
	public List<HashMap<String, Object>> getInsuranceHisList(String appId) {
		// TODO Auto-generated method stub
		List<SignFinanceDetail> signListPo = signContractService.getSignFinanceDetailByAppId(appId);
		List<HashMap<String,Object>> vo = new ArrayList<HashMap<String,Object>>();
		for(SignFinanceDetail po : signListPo)
		{
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("carVin", po.getCarVin());
			item.put("signId", po.getId());
			//历史交强险购买记录
			List<InsuranceHisPo> jqxHisList = insuranceHisDao.selectBySignId(po.getId(), InsuranceType.JQX.getName());
			item.put("jqxHisList", jqxHisList);
			//历史商业购买记录
			List<InsuranceHisPo> syxHisList = insuranceHisDao.selectBySignId(po.getId(), InsuranceType.SYX.getName());
			item.put("syxHisList", syxHisList);
			//历史履约险购买记录
			List<InsuranceHisPo> lyxHisList = insuranceHisDao.selectBySignId(po.getId(), InsuranceType.LYX.getName());
			item.put("lyxHisList", lyxHisList);
			vo.add(item);
		}
		return vo;
	}

	@Override
	public void addInsurance(String appId, String signId,String insType, ApplyInsuranceVo vo,MultipartFile[] files,String operId) throws Exception {
		// TODO Auto-generated method stub
		//保存附件
		String dirId="";
		if(InsuranceType.JQX.getName().equals(insType))
		{
			SysParam param = sysParamService.getSysParamByParamName("jqxDirName");
			if(param == null)
			{
				throw new Exception("未设置交强险目录系统参数jqxDirName");
			}
			Directory dir = directoryService.getDirectoryByDirName(param.getParamValue());
			dirId = dir.getId();
			
		}
		if(InsuranceType.SYX.getName().equals(insType))
		{
			SysParam param = sysParamService.getSysParamByParamName("syxDirName");
			if(param == null)
			{
				throw new Exception("未设置商业险目录系统参数syxDirName");
			}
			Directory dir = directoryService.getDirectoryByDirName(param.getParamValue());
			dirId = dir.getId();
			
		}
		for(MultipartFile file : files)
		{
			fileService.saveFile(file, appId, dirId, operId);
		}
		//保存新增保险信息
		InsuranceHis po = new InsuranceHis();
		po.setId(Utils.get16UUID());
		po.setSignId(signId);
		po.setAppId(appId);
		po.setInsType(insType);
		po.setInsCompanyId(vo.getInsCompanyId());
		po.setInsPolicyNo(vo.getInsPolicyNo());
		po.setInsAmount(vo.getInsAmount());
		po.setInsStartDate(vo.getInsStartDate());
		po.setInsEndDate(vo.getInsEndDate());
		po.setIsDamage(vo.getIsDamage());
		po.setDamageAmount(vo.getDamageAmount());
		po.setIsThird(vo.getIsThird());
		po.setThirdAmount(vo.getThirdAmount());
		po.setIsTheft(vo.getIsTheft());
		po.setTheftAmount(vo.getTheftAmount());
		po.setIsAvoid(vo.getIsAvoid());
		po.setAvoidAmount(vo.getAvoidAmount());
		po.setCreateId(operId);
		po.setCreateTime(new Date());
		insuranceHisDao.insert(po);
	}

	@Override
	public void createInsuranceContinueTask(String appId,String operId) {
		// TODO Auto-generated method stub
		//启动流程
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		ProcessInstance instance = runWorkflowService.startProcess("BXXB", "", vars);
	}

	@Override
	public void commitInsuranceContinue(String taskId) {
		// TODO Auto-generated method stub
		runWorkflowService.completeTask(taskId, "",null, CommandType.COMMIT);
	}

	@Override
	public InsuranceHisPo getInsuranceHisById(String id) {
		// TODO Auto-generated method stub
		return insuranceHisDao.selectByInsuranceId(id);
	}

	@Override
	public void addInsuranceClaims(InsuranceClaims po) {
		// TODO Auto-generated method stub
		insuranceClaimsDao.insert(po);
	}

}
