package com.pujjr.file.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.file.domain.DirectoryFile;
import com.pujjr.file.po.CategoryDirectoryPo;
import com.pujjr.file.service.IFileService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/file")
public class FileController extends BaseController
{
	@Autowired
	private IFileService fileService;
	@Value("${endpoint}")
	private String endpoint;
	@Value("${accessKeyId}")
	private String accessKeyId;
	@Value("${accessKeySecret}")
	private String accessKeySecret;
	@Value("${bucketName}")
	private String bucketName;
	
	@RequestMapping(value="/getApplyFormCategoryDirectoryList/{appId}/{categoryKey}",method=RequestMethod.GET)
	public List<CategoryDirectoryPo>  getApplyFormCategoryDirectoryList(@PathVariable String appId,@PathVariable String categoryKey)
	{
		String dirTplId = fileService.getApplyProductTemplateId(appId);
		return fileService.getTemplateCategoryDirectoryList(dirTplId,categoryKey, appId); 
	}
	
	@RequestMapping(value="/uploadFile/{businessId}/{dirId}")
	public void uploadFile(@Param("file")MultipartFile file,@PathVariable String businessId,@PathVariable String dirId,HttpServletRequest request) throws IOException{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		fileService.saveFile(file, businessId, dirId, sysAccount.getAccountId());
	}
	@RequestMapping(value="/listFile/{businessId}/{dirId}")
	public List<DirectoryFile> getDirectoryFileList(@PathVariable String businessId,@PathVariable String dirId)
	{
		return fileService.getBusinessDirFileList(businessId, dirId);
	}
	
	@RequestMapping(value="/img/{fileId}", method=RequestMethod.GET)
	public void getFile(@PathVariable String fileId,
								HttpServletRequest request,
								HttpServletResponse response) throws IOException
	{
		DirectoryFile file = fileService.getFileInfo(fileId);
		String ossKey = file.getOssKey();
		
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		OSSObject ossObject = ossClient.getObject(bucketName, ossKey);
		
		InputStream imgStream = ossObject.getObjectContent();
		OutputStream stream = response.getOutputStream();
		int len = 0;
	    byte[] b = new byte[1024];
	    while ((len = imgStream.read(b, 0, 1024)) != -1) 
	    {
	        stream.write(b, 0, len);
	    }
	    imgStream.close();
	    stream.flush();
        stream.close();
     // 关闭client
        ossClient.shutdown();
	}
	@RequestMapping(value="/batchMoveFileToDir/{dirId}",method=RequestMethod.POST)
	public void batchMoveFileToDir(@PathVariable String dirId,@RequestBody List<DirectoryFile> listFile)
	{
		List<String> fileIds = new ArrayList<String>();
		for(DirectoryFile file : listFile)
		{
			fileIds.add(file.getId());
		}
		fileService.batchMoveFileToDir(dirId, fileIds);
	}
	@RequestMapping(value="/{fileId}",method=RequestMethod.DELETE)
	public void deleteFile(@PathVariable String fileId) throws Exception
	{
		fileService.deleteFile(fileId);
	}
	
}
