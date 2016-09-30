package com.pujjr.carcredit.aspect;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.pujjr.carcredit.dao.HisBeanMapMapper;
import com.pujjr.carcredit.dao.HisOperMapper;
import com.pujjr.carcredit.domain.HisBeanMap;
import com.pujjr.carcredit.domain.HisOper;
import com.pujjr.utils.Utils;

@Aspect 
public class ApplyOperHisAspect {
	@Autowired
	private HisOperMapper hisOperMapper;
	@Autowired
	private HisBeanMapMapper hisBeanMapMapper;
//	@Before("execution(* com.pujjr.carcredit.service.impl.ApplyServiceImpl.insertApply(..)) && args(object,accountId)")
//	@Before("execution(* com.pujjr.carcredit.service.impl..*(..)) && args(object,accountId)")
//	@Before("@annotation(com.pujjr.carcredit.annotion.ApplyOperHisAnnotation) && args(object,accountId)")
	@Before("@annotation(com.pujjr.carcredit.annotion.ApplyOperHisAnnotation) && args(object,accountId)")
	public void target(Object object,String accountId) throws IllegalArgumentException, IllegalAccessException, ParseException{
		System.out.println("*********记录操作历史记录开始***********");
		JSONObject afterJson = (JSONObject) JSONObject.toJSON(object);//修改后数据
		String appId = afterJson.getString("appId");
		String id = afterJson.getString("id");
		String className = "";
		String tableName = "";
		Field[] fields = object.getClass().getFields();
		System.out.println("fields.length"+fields.length);
//		System.out.println("PreGreetingAspect->target");
		System.out.println("对象类名 ："+object.getClass().getName());
		className = object.getClass().getName();
		HisBeanMap hisBeanMap = hisBeanMapMapper.selectByClassName(className);
		if(hisBeanMap == null){
			System.out.println("错误：bean映射关系暂未初始化");
			return;
		}
		tableName = hisBeanMap.getTableName();
//		System.out.println("tableName:"+tableName);
		HashMap<String,Object> condition = new HashMap<String,Object>();
		System.out.println(afterJson.getString("id"));
		if(id == null){
			return;
		}
		condition.put("tableName", tableName);
		condition.put("id","'"+id+"'");
		HashMap<String,Object> preRecord = hisOperMapper.selectCommon(condition);//当前操作tableName表对应主键ID记录 preRecord:修改前数据
		System.out.println("preRecord:"+preRecord);
		if(preRecord != null){
			Iterator<String> keyIt = preRecord.keySet().iterator();
//			存在object对应记录,执行修改操作
			while(keyIt.hasNext()){
				String key = keyIt.next();
				Object preValue = preRecord.get(key);
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					String fieldName = Utils.field2Col(field.getName());//属性名转数据库列
					Object feildValue = null;
					if(field.get(object) != null){
						feildValue = field.get(object);
					}
					if(key.toUpperCase().equals(fieldName)){
						if(feildValue == null && preValue != null){
							continue;//空值不做修改操作，故无操作历史
						}else if(!feildValue.equals(preValue) && !feildValue.equals("")){//插入操作历史表：1、修改字段和原表字段值不一致 
							System.out.println("fieldName:"+fieldName);
							System.out.println("feildValue:"+feildValue);
							System.out.println("preValue:"+preValue);
							HisOper hisOper = new HisOper();
							hisOper.setId(Utils.get16UUID());
							hisOper.setAppId(appId);
							hisOper.setTableName(tableName);
							hisOper.setFieldName(fieldName);
							hisOper.setClassName(className);
							hisOper.setUpdMode("update");
							hisOper.setPreValue(preValue+"");
							hisOper.setAfterValue(feildValue+"");
							hisOper.setOperTime(Calendar.getInstance().getTime());
							hisOper.setAccounId(accountId);
							hisOper.setRecordId(id);
							hisOperMapper.insert(hisOper);
						}
					}
				}
			}
		}else{
//			不存在记录，执行新增操作
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = Utils.field2Col(field.getName());//属性名转数据库列
				System.out.println("fieldName:"+fieldName);
				String fieldValue = "";
				if(field.get(object) != null){
					fieldValue = field.get(object).toString();
				}
				System.out.println("|fieldValue:"+fieldValue);
				if(!"".equals(fieldValue)){
					HisOper hisOper = new HisOper();
					hisOper.setId(Utils.get16UUID());
					hisOper.setAppId(appId);
					hisOper.setTableName(tableName);
					hisOper.setFieldName(fieldName);
					hisOper.setClassName(className);
					hisOper.setUpdMode("insert");
					hisOper.setPreValue("");
					hisOper.setAfterValue(fieldValue+"");
					hisOper.setOperTime(Calendar.getInstance().getTime());
					hisOper.setAccounId(accountId);
					hisOper.setRecordId(id);
					hisOperMapper.insert(hisOper);
				}
			}
		}
		System.out.println("*********记录操作历史记录结束***********");
	}
}
