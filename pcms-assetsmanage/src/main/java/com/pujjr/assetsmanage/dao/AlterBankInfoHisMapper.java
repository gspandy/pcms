package com.pujjr.assetsmanage.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.assetsmanage.domain.AlterBankInfoHis;

public interface AlterBankInfoHisMapper {
    int deleteByPrimaryKey(String id);

    int insert(AlterBankInfoHis record);

    int insertSelective(AlterBankInfoHis record);

    AlterBankInfoHis selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AlterBankInfoHis record);

    int updateByPrimaryKey(AlterBankInfoHis record);
    
    List<HashMap<String,Object>> selectAlterBankInfoHisList(@Param("appId")String appId);
    
    AlterBankInfoHis selectByAppIdAndAcctNo(@Param("appId")String appId,@Param("repayAcctNo")String repayAcctNo);
}