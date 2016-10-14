package com.pujjr.file.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.file.po.CategoryDirectoryPo;

public interface FileMapper 
{
	public List<CategoryDirectoryPo> selectApplyFormCategoryDirectoryList(@Param("categoryKey")String categoryKey,@Param("appId")String appId);
}
