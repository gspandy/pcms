package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.CarBrand;
import com.pujjr.base.domain.CarSerial;
import com.pujjr.base.domain.CarStyle;
import com.pujjr.base.domain.CarTemplate;
import com.pujjr.base.domain.CarTemplateChoice;
import com.pujjr.base.vo.QueryParamCarBrandVo;
import com.pujjr.base.vo.QueryParamCarSerialVo;
import com.pujjr.base.vo.QueryParamCarStyleVo;
/**
 * 车辆信息管理服务
 * @author 150032
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public interface ICarService 
{
	/**
	 * 查询车辆品牌列表
	 * @param params
	 * @return
	 */
	public List<CarBrand> getCarBrandList(QueryParamCarBrandVo params);
	/**
	 * 增加车辆品牌
	 * @param record
	 */
	public void addCarBrand(CarBrand record);
	/**
	 * 修改车辆品牌信息
	 * @param record
	 */
	public void modifyCarBrand(CarBrand record);
	/**
	 * 删除车辆品牌信息
	 * @param id
	 */
	public void deleteCarBrand(String id);
	/**
	 * 获取车系列表
	 * @param param
	 * @return
	 */
	public List<CarSerial> getCarSerialList(QueryParamCarSerialVo param);
	/**
	 * 增加车系信息
	 * @param record
	 */
	public void addCarSerial(CarSerial record);
	/**
	 * 修改车系信息
	 * @param record
	 */
	public void modifyCarSerial(CarSerial record);
	/**
	 * 删除车系信息
	 * @param id
	 */
	public void deleteCarSerial(String id);
	/**
	 * 查询车辆型号列表
	 * @param param
	 * @return
	 */
	public List<CarStyle> getCarStyleList(QueryParamCarStyleVo param);
	/**
	 * 增加车辆型号
	 * @param record
	 */
	public void addCarStyle(CarStyle record);
	/**
	 * 修改车辆型号信息
	 * @param record
	 */
	public void modifyCarStyle(CarStyle record);
	/**
	 * 删除车辆型号
	 * @param id
	 */
	public void deleteCarStyle(String id);
	/**
	 * 根据ID查询车辆型号信息
	 * @param id
	 * @return
	 */
	public CarStyle getCarStyleById(String id);
	/**
	 * 查询经销商可选车辆模板配置列表
	 * @return
	 */
	public List<CarTemplate>  getCarTemplateList();
	/**
	 * 增加经销商可选车辆模板
	 * @param record
	 */
	public void addCarTemplate(CarTemplate record);
	/**
	 * 修改经销商可选车辆模板
	 * @param record
	 */
	public void modifyCarTemplate(CarTemplate record);
	/**
	 * 删除经销商可选车辆模板
	 * @param id
	 */
	public void deleteCarTemplateById(String id);
	/**
	 * 获取模板已选车辆信息
	 * @param templateId
	 * @return
	 */
	public List<CarTemplateChoice> getCarTemplateChoiceList(String templateId);
	/**
	 * 保存模板可选车辆信息
	 * @param templateId
	 * @param list
	 */
	public void saveCarTemplateChoice(String templateId,List<CarTemplateChoice> list);
	/**
	 * 根据模板查询可选车辆品牌
	 * @param templateId
	 * @return
	 */
	public List<CarBrand> getCarBrandListByTemplateId(String templateId);
	/**
	 * 根据模板ID+车辆品牌ID查询可选车系列表
	 * @param templateId
	 * @param carBrandId
	 * @return
	 */
	public List<CarSerial> getCarSerialListByTemplateIdAndCarBrandId(String templateId,String carBrandId);
	
	
	
	
	
}
