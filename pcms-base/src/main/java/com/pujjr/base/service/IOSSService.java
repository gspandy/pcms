package com.pujjr.base.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;

@Service
public interface IOSSService {
	
	public void putObject(String ossKey,InputStream inputStream); 
	
	public void deleteObject(String ossKey);

}
