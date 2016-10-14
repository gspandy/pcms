package com.pujjr.file.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.pujjr.file.po.CategoryDirectoryPo;
@Service
public interface IFileService 
{
	List<CategoryDirectoryPo> getApplyFormCategoryDirectoryList(String categoryKey,String appId);
}
