package com.pujjr.carcredit.vo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

public class FeflectMain {

	
	public void reflectFields(Class obj,ArrayList<Field> fieldArray){
		
		Field[] fields = obj.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fieldArray.add(fields[i]);
		}
		/*
		
		Class classTemp =obj.getSuperclass();
		System.out.println("classTemp.getTypeName()"+classTemp.getTypeName()+classTemp.getTypeName().equals(Object.class.getTypeName()));
		if(classTemp.getTypeName().equals(Object.class.getTypeName())){
			Field[] fields = obj.getFields();
			for (int i = 0; i < fields.length; i++) {
				fieldArray.add(fields[i]);
			}
			return;
		}else{
			Field[] fields = obj.getFields();
			for (int i = 0; i < fields.length; i++) {
				fieldArray.add(fields[i]);
			}
			return;
//			reflectFields(classTemp,fieldArray);
		}*/
	}
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		ArrayList<Field> fieldArray = new ArrayList<Field>();
		ApplyVo applyVo = new ApplyVo();
		Field[] fields = applyVo.getClass().getFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			System.out.println(field.getName()+"|"+field.get(applyVo));
		}
		System.out.println(fields.length);
		String str = (String)null;
//		System.out.println(applyVo.getClass().getSuperclass());
		/*
//		System.out.println(applyVo.getClass().getSuperclass().getTypeName());
		FeflectMain fm = new FeflectMain();
		fm.reflectFields(applyVo.getClass(),fieldArray);
		System.out.println(fieldArray.size());
		for (int i = 0; i < fieldArray.size(); i++) {
			Field field = fieldArray.get(i);
			System.out.println(field.getName());
		}*/
	}
}
