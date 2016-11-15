package com.pujjr.base.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.aliyun.oss.model.ObjectMetadata;

@Service
public interface IOSSService {
	
	public void putObject(String ossKey,InputStream inputStream); 
	
	public void putObject(String ossKey,InputStream inputStream,ObjectMetadata meta);
	
	public void deleteObject(String ossKey);
	
	public InputStream getObject(String ossKey);

}
