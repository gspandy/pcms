package com.pujjr.file.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pujjr.file.domain.DirectoryFile;
import com.pujjr.file.po.CategoryDirectoryPo;
@Service
public interface IFileService 
{
	List<CategoryDirectoryPo> getTemplateCategoryDirectoryList(String templateId,String categoryKey,String businessId);
	
	void saveFile(MultipartFile file,String businessId,String dirId,String operId);
	
	List<DirectoryFile> getBusinessDirFileList(String businessId,String dirId);
	
	void batchMoveFileToDir(String dirId,List<String> fileIds);
	
	DirectoryFile getFileInfo(String fileId);
	
	void deleteFile(String fileId) throws Exception;
	
	String getApplyProductTemplateId(String appId);
	/**
	 * 上传待打印文件到oos
	 * @param bucketName
	 * @param ossKey
	 * @param inputStream
	 */
	void savePrintFile(File file, String businessId, String operId);
}
