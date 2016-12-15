package com.pujjr.carcredit.exthandle;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysParam;
import com.pujjr.base.service.ISysParamService;
import com.pujjr.carcredit.dao.FraudInnerResultMapper;
import com.pujjr.carcredit.domain.ApplyFinance;
import com.pujjr.carcredit.domain.ApplyTenant;
import com.pujjr.carcredit.domain.ApplyTenantCar;
import com.pujjr.carcredit.domain.ApplyTenantHouse;
import com.pujjr.carcredit.domain.FraudInnerResult;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.jbpm.core.handle.ITaskCompleteHandle;
import com.pujjr.utils.Utils;
/**
 * 申请单数据反欺诈
 * @author 150032
 *
 */
@Service("InnerAntiFraud")
public class InnerAntiFraud implements ITaskCompleteHandle {

	@Autowired
	private ITaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private FraudInnerResultMapper fraudInnerResultDao;
	@Autowired
	private ISysParamService sysParamService;
	@Override
	public void handle(TaskEntity taskEntity) {
		// TODO Auto-generated method stub
		
		//获取申请单主键
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).singleResult().getBusinessKey();
		//清楚原反欺诈数据
		fraudInnerResultDao.deleteByAppId(businessKey);
		//获取承租人信息
		ApplyTenant tenant = applyService.getApplyTenant(businessKey);
		//获取融资信息
		List<ApplyFinance> financeList = applyService.getApplyFinanceList(businessKey);
		//总的裸车价
		double totalSalePrice=0.00;
		//是否轻卡或皮卡
		boolean isQkOrPk = false;
		for(ApplyFinance item : financeList)
		{
			totalSalePrice += item.getSalePrice();
			if(item.getCarType().equals("cllx07")||item.getCarType().equals("cllx04"))
			{
				isQkOrPk = true;
			}
		}
		//反欺诈判断1：年收入/裸车价>X	X参数可设置	初设2.5	提示内容：年收入/裸车价=？
		//年收入
		double yearIncome = Utils.formateDouble2Double(tenant.getYearIncome(),2);
		//反欺诈参数
		double antiFraud1 = 2.5;
		SysParam param1 = sysParamService.getSysParamByParamName("antiFraud1");
		if(param1!=null)
		{
			antiFraud1 = Double.valueOf(param1.getParamValue());
		}
		double result1 = yearIncome/totalSalePrice;
		if(Double.compare(result1, antiFraud1)>0)
		{
			FraudInnerResult result = new FraudInnerResult();
			result.setId(Utils.get16UUID());
			result.setAppId(businessKey);
			result.setResult("年收入/裸车价="+result1);
			fraudInnerResultDao.insert(result);
		}
		//反欺诈判断2：房产、车产都为空且裸车价>X	X参数可设置	初设30万	提示内容：无资产购买车辆裸车价超过?万
		//反欺诈参数
		double antiFraud2 = 300000.00;
		SysParam param2 = sysParamService.getSysParamByParamName("antiFraud2");
		if(param2!=null)
		{
			antiFraud2 = Double.valueOf(param2.getParamValue());
		}
		List<ApplyTenantHouse> listHouse = applyService.getApplyTenantHouseList(businessKey);
		List<ApplyTenantCar> listCar = applyService.getApplyTenantCarList(businessKey);
		if(listHouse.size()==0 && listCar.size()==0 && Double.compare(totalSalePrice, antiFraud2)>0)
		{
			FraudInnerResult result = new FraudInnerResult();
			result.setId(Utils.get16UUID());
			result.setAppId(businessKey);
			result.setResult("无资产购买车辆裸车价超过"+antiFraud2+"万");
			fraudInnerResultDao.insert(result);
		}
		//反欺诈判断3：女性购买轻卡或自卸车以上货车			提示内容：女性购买轻卡或自卸车以上货车
		if(tenant.getSex().equals("xb02")&&isQkOrPk == true)
		{
			FraudInnerResult result = new FraudInnerResult();
			result.setId(Utils.get16UUID());
			result.setAppId(businessKey);
			result.setResult("女性购买轻卡或自卸车以上货车");
			fraudInnerResultDao.insert(result);
		}
		//反欺诈判断4：承租人年龄<=X岁购买裸车价>=Y的车辆	X,Y参数可设置	初设X=27，Y=30万	提示内容：年龄<=X岁购买超过Y金额的车辆
		int antiFraud3 = 27;
		SysParam param3 = sysParamService.getSysParamByParamName("antiFraud3");
		if(param3!=null)
		{
			antiFraud3 = Integer.valueOf(param3.getParamValue());
		}
		double antiFraud4 = 300000.00;
		SysParam param4 = sysParamService.getSysParamByParamName("antiFraud4");
		if(param4 !=null)
		{
			antiFraud4 = Double.valueOf(param4.getParamValue());
		}
		if(tenant.getAge()<=antiFraud3 && Double.compare(totalSalePrice,antiFraud4)>0)
		{
			FraudInnerResult result = new FraudInnerResult();
			result.setId(Utils.get16UUID());
			result.setAppId(businessKey);
			result.setResult("年龄<="+antiFraud3+"岁购买超过"+antiFraud4+"金额的车辆");
			fraudInnerResultDao.insert(result);
		}
		//反欺诈判断5：承租人年龄<=X岁月收入>=Y	X,Y参数可设置	初设X=25，Y=2万	提示内容：年龄<=X岁月收入超Y
		int antiFraud5 = 25;
		SysParam param5 = sysParamService.getSysParamByParamName("antiFraud5");
		if(param5!=null)
		{
			antiFraud5 = Integer.valueOf(param5.getParamValue());
		}
		double antiFraud6 = 20000.00;
		SysParam param6 = sysParamService.getSysParamByParamName("antiFraud6");
		if(param6 !=null)
		{
			antiFraud6 = Double.valueOf(param6.getParamValue());
		}
		if(tenant.getAge()<=antiFraud5 && Double.compare(tenant.getMontyIncome(), antiFraud6)>=0)
		{
			FraudInnerResult result = new FraudInnerResult();
			result.setId(Utils.get16UUID());
			result.setAppId(businessKey);
			result.setResult("年龄<="+antiFraud5+"岁月收入超"+antiFraud6);
			fraudInnerResultDao.insert(result);
		}
		
	}

}
