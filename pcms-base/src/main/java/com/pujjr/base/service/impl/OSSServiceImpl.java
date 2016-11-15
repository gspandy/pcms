package com.pujjr.base.service.impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.pujjr.base.service.IOSSService;
@Service
public class OSSServiceImpl implements IOSSService 
{
	@Value("${endpoint}")
	private String endpoint;
	@Value("${accessKeyId}")
	private String accessKeyId;
	@Value("${accessKeySecret}")
	private String accessKeySecret;
	@Value("${bucketName}")
	private String bucketName;
	@Override
	public void putObject(String ossKey, InputStream inputStream) 
	{
		// TODO Auto-generated method stub
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		try
		{
			if (ossClient.doesBucketExist(bucketName)) 
			{
				System.out.println("您已经创建Bucket：" + bucketName + "。");
			}
			else 
			{
				System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
				// 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
				// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
				ossClient.createBucket(bucketName);
			}
			ossClient.putObject(bucketName, ossKey, inputStream);
		}
		catch (OSSException oe) 
		{
			oe.printStackTrace();
		} 
		catch (ClientException ce) 
		{
			ce.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			ossClient.shutdown();
		}
	}
	@Override
	public void deleteObject(String ossKey) {
		// TODO Auto-generated method stub
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		try
		{
			ossClient.deleteObject(bucketName, ossKey);
		}
		catch (OSSException oe) 
		{
			oe.printStackTrace();
		} 
		catch (ClientException ce) 
		{
			ce.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			ossClient.shutdown();
		}
		
	}
	@Override
	public InputStream getObject(String ossKey) {
		// TODO Auto-generated method stub
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		OSSObject ossObject = ossClient.getObject(bucketName, ossKey);
		return ossObject.getObjectContent();
	}
	@Override
	public void putObject(String ossKey, InputStream inputStream, ObjectMetadata meta) {
		// TODO Auto-generated method stub
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		try 
		{
			if (ossClient.doesBucketExist(bucketName)) 
			{
				System.out.println("您已经创建Bucket：" + bucketName + "。");
			} else {
				System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
				// 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
				// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
				ossClient.createBucket(bucketName);
			}
			ossClient.putObject(bucketName, ossKey, inputStream,meta);
		} catch (OSSException oe) {
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
