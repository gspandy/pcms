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
	 * ��������ģ��
	 * @param modelId ģ��ID
	 * @param values ģ��JSON����
	 * **/
	public void saveModel(String modelId , MultiValueMap<String, String> values) throws JsonProcessingException, IOException, TranscoderException;
	
	/**
	 * ����ģ��
	 * @param modelId ģ��ID
	 * **/
	public String deployModel(String modelId) throws JsonProcessingException, IOException;
	
	/**
	 * ����ģ��Ϊ�°汾
	 * @param modelId ģ��ID
	 * @param values ģ��JSON����
	 * **/
	
	public HashMap<String,String>  deployAsNewModel(String  modelId, MultiValueMap<String, String> values) throws JsonProcessingException, IOException, TranscoderException;
	/**
	 * ����ģ��ID��ȡ����ģ������
	 * @param modelIdģ��ID
	 * **/
	public HashMap<String,Object> getModelByModelId(String modelId);
	/**
	 * ����ģ��ID��ȡ�ѷ�������ͼ���ö���������
	 *@param deploymentId ���̷���ID
	 * **/
	public ActGeBytearray getBnpmByteByDeploymentId(String deploymentId);
	/**
	 * ����ģ��ID��ȡ�ѷ�������ͼͼƬ����������
	 *@param deploymentId ���̷���ID
	 * **/
	public ActGeBytearray getPngByteByDeploymentId(String deploymentId);
	/**
	 * ����������ȡ����ͼ���ö�������Ϣ
	 *@param id ����
	 * **/
	public ActGeBytearray getActGeBytearrayById(String id);
	/**
	 * ����������������ͼ���ö�������Ϣ
	 *@param record ����ʵ��
	 * **/
	public void updateActGeBytearray(ActGeBytearray record);
}

