package com.pujjr.carcredit.service;

import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import com.pujjr.carcredit.vo.ApplyVo;

/**
 * @author tom
 *
 */
public interface IPrintCheckLetter {
	public void createSignDate(PdfPTable table,Font fieldNameFont,Font fieldFont);
	public void createImportantTips(PdfPTable table,Font fieldNameFont,Font fieldFont);
	public void createAddCondition(PdfPTable table,Font fieldNameFont,Font fieldFont);
	 public void createSmallPdf(String dest) throws IOException, DocumentException;
	 public void createFinanceInfo(PdfPTable table,Font fieldNameFont,Font fieldFont,ApplyVo applyVo);
	void createPdf(String appId,String contextPath,String ossKey);
}
