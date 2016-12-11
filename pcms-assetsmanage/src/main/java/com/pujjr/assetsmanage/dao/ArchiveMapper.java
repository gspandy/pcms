package com.pujjr.assetsmanage.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.assetsmanage.po.ArchiveDetailPo;

public interface ArchiveMapper 
{
	List<HashMap<String,Object>> selectArchiveToDoTaskList(@Param("assignee")String assignee); 
	
	List<ArchiveDetailPo>  selectArchiveTaskDetail(@Param("archiveTaskId")String archiveTaskId,@Param("archiveTaskType")String archiveTaskType);
	
	List<ArchiveDetailPo>  selectArchiveTaskDetailNoNull(@Param("archiveTaskId")String archiveTaskId);
 	
	List<HashMap<String,Object>> selectArchiveList();
}
