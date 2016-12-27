package com.pujjr.file.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.poifs.crypt.dsig.facets.OOXMLSignatureFacet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.pujjr.file.dao.DirectoryFileMapper;
import com.pujjr.file.dao.FileMapper;
import com.pujjr.file.domain.DirectoryFile;
import com.pujjr.file.po.CategoryDirectoryPo;
import com.pujjr.file.service.IFileService;
import com.pujjr.utils.ImageUtil;
import com.pujjr.utils.Utils;
@Service
@Transactional(rollbackFor=Exception.class)
public class FileServiceImpl implements IFileService 
{
	@Autowired
	private FileMapper fileDao;
	@Autowired
	private DirectoryFileMapper directoryFileDao;
	@Value("${endpoint}")
	private String endpoint;
	@Value("${accessKeyId}")
	private String accessKeyId;
	@Value("${accessKeySecret}")
	private String accessKeySecret;
	@Value("${bucketName}")
	private String bucketName;
	
	@Override
	public List<CategoryDirectoryPo> getTemplateCategoryDirectoryList(String templateId,String categoryKey,String businessId) {
		// TODO Auto-generated method stub
		return fileDao.selectTemplateCategoryDirectoryList(templateId,categoryKey, businessId);
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

	@Override
	public void saveFile(MultipartFile file, String businessId, String dirId, String operId) {
		String fileId = Utils.get16UUID();
		String fileSuffix = Utils.getFileSuffix(file.getOriginalFilename());
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		try
		{
			if (ossClient.doesBucketExist(bucketName)) {
				System.out.println("您已经创建Bucket：" + bucketName + "。");
			} else {
				System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
				// 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
				// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
				ossClient.createBucket(bucketName);
			}
			//上传原始文件至OSS
			String ossKey = "resource/"+businessId+"/"+fileId+fileSuffix;
			ossClient.putObject(bucketName, ossKey, file.getInputStream());
			//上传缩略图至OSS
			String ossKeyPreview = "resource/"+businessId+"/"+fileId+"-preview"+fileSuffix;
			ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
			ImageUtil imgUtil = new ImageUtil();
			imgUtil.thumbnailImage(file.getInputStream(), file.getOriginalFilename(),bOutput, 100, 100, "preview", false);
			ossClient.putObject(bucketName, ossKeyPreview, new ByteArrayInputStream(bOutput.toByteArray()));
			//保存文件信息
			DirectoryFile fileMetaInfo = new DirectoryFile();
			fileMetaInfo.setId(fileId);
			fileMetaInfo.setBusinessId(businessId);
			fileMetaInfo.setDirId(dirId);
			fileMetaInfo.setOssKey(ossKey);
			fileMetaInfo.setOssKeyPreview(ossKeyPreview);
			fileMetaInfo.setFileName(file.getOriginalFilename());
			fileMetaInfo.setFileSize(Integer.parseInt(String.valueOf(file.getSize()/1024)));
			fileMetaInfo.setFileType(fileSuffix);
			fileMetaInfo.setCreateId(operId);
			fileMetaInfo.setCreateTime(new Date());
			directoryFileDao.insert(fileMetaInfo);
		}catch (OSSException oe) {
			oe.printStackTrace();
		} catch (ClientException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ossClient.shutdown();
		}
		
		
	}

	@Override
	public DirectoryFile getFileInfo(String fileId) {
		// TODO Auto-generated method stub
		return directoryFileDao.selectByPrimaryKey(fileId);
	}

	@Override
	public void deleteFile(String fileId) throws Exception {
		// TODO Auto-generated method stub
		DirectoryFile file = directoryFileDao.selectByPrimaryKey(fileId);
		if(file == null)
		{
			throw new Exception("文件不存在");
		}
		//删除OSS上的文件
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		try
		{
			ossClient.deleteObject(bucketName, file.getOssKey());
		}
		catch(Exception e)
		{
			throw new Exception("删除文件失败 "+e.getMessage());
		}
		finally
		{
			ossClient.shutdown();
		}
		directoryFileDao.deleteByPrimaryKey(fileId);
		
	}

	@Override
	public String getApplyProductTemplateId(String appId) {
		// TODO Auto-generated method stub
		return fileDao.selectApplyProductTemplateId(appId);
	}

	@Override
	public void savePrintFile(File file, String businessId, String operId) {
		String fileId = Utils.get16UUID();
//		String originalName = file.getOriginalFilename();
		String originalName = file.getName();
		String fileSuffix = Utils.getFileSuffix(originalName);
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		try
		{
			if (ossClient.doesBucketExist(bucketName)) {
				System.out.println("您已经创建Bucket：" + bucketName + "。");
			} else {
				System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
				// 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
				// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
				ossClient.createBucket(bucketName);
			}
			//上传原始文件至OSS
			String ossKey = "resource/"+businessId+"/print"+"/"+originalName;
			ossClient.putObject(bucketName, ossKey, new FileInputStream(file));
			//保存文件信息
			DirectoryFile fileMetaInfo = new DirectoryFile();
			fileMetaInfo.setId(fileId);
			fileMetaInfo.setBusinessId(businessId);
//			fileMetaInfo.setDirId(dirId);
			fileMetaInfo.setOssKey(ossKey);
//			fileMetaInfo.setOssKeyPreview(ossKeyPreview);
			fileMetaInfo.setFileName(file.getName());
			fileMetaInfo.setFileSize(Integer.parseInt(String.valueOf(file.length()/1024)));
			fileMetaInfo.setFileType(fileSuffix);
			fileMetaInfo.setCreateId(operId);
			fileMetaInfo.setCreateTime(new Date());
			directoryFileDao.insert(fileMetaInfo);
		}catch (OSSException oe) {
			oe.printStackTrace();
		} catch (ClientException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ossClient.shutdown();
		}
		
		
	}

	

}
