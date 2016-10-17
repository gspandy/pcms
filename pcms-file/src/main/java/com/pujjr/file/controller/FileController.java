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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.vo.ApplyVo;
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
	@Autowired
	private IApplyService applyService;
	@RequestMapping(value="/getApplyFormCategoryDirectoryList/{appId}/{categoryKey}",method=RequestMethod.GET)
	public List<CategoryDirectoryPo>  getApplyFormCategoryDirectoryList(@PathVariable String appId,@PathVariable String categoryKey)
	{
		ApplyVo vo = applyService.getApplyDetail(appId);
		return fileService.getTemplateCategoryDirectoryList(vo.getProduct().getDirectoryTemplateId(),categoryKey, appId); 
	}
	
	@RequestMapping(value="/uploadFile/{businessId}/{dirId}")
	public void uploadFile(@Param("file")MultipartFile file,@PathVariable String businessId,@PathVariable String dirId,HttpServletRequest request) throws IOException{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		String fileId = Utils.get16UUID();
		String fileSuffix = Utils.getFileSuffix(file.getOriginalFilename());
		String filePath="d:\\file\\"+fileId+fileSuffix;
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath));
		DirectoryFile fileMetaInfo = new DirectoryFile();
		fileMetaInfo.setId(fileId);
		fileMetaInfo.setBusinessId(businessId);
		fileMetaInfo.setDirId(dirId);
		fileMetaInfo.setFileName(file.getOriginalFilename());
		fileMetaInfo.setFileSize(Integer.parseInt(String.valueOf(file.getSize()/1024)));
		fileMetaInfo.setFileType(fileSuffix);
		fileMetaInfo.setCreateId(sysAccount.getAccountId());
		fileMetaInfo.setCreateTime(new Date());
		fileService.saveFile(fileMetaInfo);
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
		String filePath="d:\\file\\"+Utils.convertStr2Utf8(fileId+".jpg");
		byte[] imgBytes =  FileUtils.readFileToByteArray(new File(filePath));
		InputStream imgStream = new ByteArrayInputStream(imgBytes);
		OutputStream stream = response.getOutputStream();
		int len = 0;
	    byte[] b = new byte[1024];
	    while ((len = imgStream.read(b, 0, 1024)) != -1) 
	    {
	        stream.write(b, 0, len);
	    }
	    stream.flush();
        stream.close();
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
	
}
