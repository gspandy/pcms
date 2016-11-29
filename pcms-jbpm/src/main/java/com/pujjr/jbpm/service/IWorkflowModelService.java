package com.pujjr.jbpm.service;

import java.io.IOException;
import java.util.HashMap;

import org.apache.batik.transcoder.TranscoderException;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pujjr.jbpm.domain.ActGeBytearray;
@Service
public interface IWorkflowModelService 
{
	/**
	 * 保存流程模型
	 * @param modelId 模型ID
	 * @param values 模型JSON数据
	 * **/
	public void saveModel(String modelId , MultiValueMap<String, String> values) throws JsonProcessingException, IOException, TranscoderException;
	
	/**
	 * 发布模型
	 * @param modelId 模型ID
	 * **/
	public String deployModel(String modelId) throws JsonProcessingException, IOException;
	
	/**
	 * 发布模型为新版本
	 * @param modelId 模型ID
	 * @param values 模型JSON数据
	 * **/
	
	public HashMap<String,String>  deployAsNewModel(String  modelId, MultiValueMap<String, String> values) throws JsonProcessingException, IOException, TranscoderException;
	/**
	 * 根据模型ID获取流程模型数据
	 * @param modelId模型ID
	 * **/
	public HashMap<String,Object> getModelByModelId(String modelId);
	/**
	 * 根据模型ID获取已发布流程图配置二进制数据
	 *@param deploymentId 流程发布ID
	 * **/
	public ActGeBytearray getBnpmByteByDeploymentId(String deploymentId);
	/**
	 * 根据模型ID获取已发布流程图图片二进制数据
	 *@param deploymentId 流程发布ID
	 * **/
	public ActGeBytearray getPngByteByDeploymentId(String deploymentId);
	/**
	 * 根据主键获取流程图配置二进制信息
	 *@param id 主键
	 * **/
	public ActGeBytearray getActGeBytearrayById(String id);
	/**
	 * 根据主键更新流程图配置二进制信息
	 *@param record 参数实体
	 * **/
	public void updateActGeBytearray(ActGeBytearray record);
}

