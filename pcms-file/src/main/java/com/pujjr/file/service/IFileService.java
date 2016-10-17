package com.pujjr.file.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.file.domain.DirectoryFile;
import com.pujjr.file.po.CategoryDirectoryPo;
@Service
public interface IFileService 
{
	List<CategoryDirectoryPo> getTemplateCategoryDirectoryList(String templateId,String categoryKey,String businessId);
	
	void saveFile(DirectoryFile file);
	
	List<DirectoryFile> getBusinessDirFileList(String businessId,String dirId);
	
	void batchMoveFileToDir(String dirId,List<String> fileIds);
}
