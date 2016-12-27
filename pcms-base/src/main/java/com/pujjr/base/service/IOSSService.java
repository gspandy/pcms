package com.pujjr.base.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.oss.model.ObjectMetadata;

@Service
@Transactional(rollbackFor=Exception.class)
public interface IOSSService {
	
	public void putObject(String ossKey,InputStream inputStream); 
	
	public void putObject(String ossKey,InputStream inputStream,ObjectMetadata meta);
	
	public void deleteObject(String ossKey);
	
	public InputStream getObject(String ossKey);

}
