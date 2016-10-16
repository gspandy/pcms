package com.pujjr.file.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.file.dao.DirectoryFileMapper;
import com.pujjr.file.dao.FileMapper;
import com.pujjr.file.domain.DirectoryFile;
import com.pujjr.file.po.CategoryDirectoryPo;
import com.pujjr.file.service.IFileService;
@Service
public class FileServiceImpl implements IFileService 
{
	@Autowired
	private FileMapper fileDao;
	@Autowired
	private DirectoryFileMapper directoryFileDao;
	@Override
	public List<CategoryDirectoryPo> getTemplateCategoryDirectoryList(String templateId,String categoryKey,String businessId) {
		// TODO Auto-generated method stub
		return fileDao.selectTemplateCategoryDirectoryList(templateId,categoryKey, businessId);
	}

	@Override
	public void saveFile(DirectoryFile file) {
		// TODO Auto-generated method stub
		directoryFileDao.insert(file);
	}

	@Override
	public List<DirectoryFile> getBusinessDirFileList(String businessId, String dirId) {
		// TODO Auto-generated method stub
		return directoryFileDao.selectAll(businessId, dirId);
	}

	@Override
	public void batchMoveFileToDir(String dirId, List<String> fileIds) {
		// TODO Auto-generated method stub
		for(String fileId : fileIds)
		{
			DirectoryFile file = directoryFileDao.selectByPrimaryKey(fileId);
			file.setDirId(dirId);
			directoryFileDao.updateByPrimaryKeySelective(file);
		}
	}
	

}
