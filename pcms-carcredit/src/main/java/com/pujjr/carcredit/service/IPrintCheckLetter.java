package com.pujjr.carcredit.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import com.pujjr.carcredit.vo.ApplyVo;

/**
 * @author tom
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public interface IPrintCheckLetter {
	public void createSignDate(PdfPTable table,Font fieldNameFont,Font fieldFont);
	public void createImportantTips(PdfPTable table,Font fieldNameFont,Font fieldFont);
	public void createAddCondition(PdfPTable table,Font fieldNameFont,Font fieldFont);
	public void createSmallPdf(String dest) throws IOException, DocumentException;
	public void createFinanceInfo(PdfPTable table,Font fieldNameFont,Font fieldFont,ApplyVo applyVo);
	File createPdf(String appId,String contextPath,String contractName);
}
