package com.pujjr.file.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.file.dao.DirectoryMapper;
import com.pujjr.file.domain.Directory;
import com.pujjr.file.service.IDirectoryService;
@Service
public class DirectoryServiceImpl implements IDirectoryService 
{
	@Autowired
	private DirectoryMapper directoryDao;
	
	@Override
	public List<Directory> getDirectoryList(boolean enabled) {
		// TODO Auto-generated method stub
		return directoryDao.selectAll(enabled);
	}

	@Override
	public void addDirectory(Directory record) {
		 directoryDao.insert(record);
	}

	@Override
	public void modifyDirectory(Directory record) {
		// TODO Auto-generated method stub
		directoryDao.updateByPrimaryKey(record);
	}

	@Override
	public void delelteDirectoryById(String id) throws Exception {
		// TODO Auto-generated method stub
		if(this.getSubDirectoryList(id).size()>0){
			throw new Exception("存在子目录，请先删除子目录");
		}
		directoryDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<Directory> getSubDirectoryList(String parentId) {
		// TODO Auto-generated method stub
		return directoryDao.selectSubDirectory(parentId);
	}

}
