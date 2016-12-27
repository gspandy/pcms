package com.pujjr.carcredit.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 表格数据初始化
 * @author pujjr
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public interface ITableInitService {
	public void hisBeanMapInit();
	public void hisFieldCommentInit();
}
