package com.pujjr.carcredit.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.poifs.crypt.dsig.facets.OOXMLSignatureFacet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.pujjr.base.domain.ContractInfo;
import com.pujjr.base.service.IContractService;
import com.pujjr.base.service.IOSSService;
import com.pujjr.base.service.impl.OSSServiceImpl;
import com.pujjr.carcredit.service.IPrintCheckLetter;
import com.pujjr.carcredit.service.IPrintDataSrcServcie;
import com.pujjr.carcredit.service.IPrintService;
import com.pujjr.carcredit.vo.PColesseePromiseVo;
import com.pujjr.carcredit.vo.PDeleiverReceiptVo;
import com.pujjr.carcredit.vo.PLeaseConstractVo;
import com.pujjr.carcredit.vo.PLoanReceiptVo;
import com.pujjr.carcredit.vo.PMortgageContractAVo;
import com.pujjr.carcredit.vo.PMortgageListVo;
import com.pujjr.carcredit.vo.PRepayRemindVo;
import com.pujjr.file.service.IFileService;
import com.pujjr.utils.Utils;

@Service
@Transactional(rollbackFor=Exception.class)
public class PrintServiceImpl implements IPrintService {
	
	private Logger logger = Logger.getLogger(PrintServiceImpl.class);
	public static String classPath = PrintServiceImpl.class.getClassLoader().getResource("").toString();
	String contextPath = PrintServiceImpl.class.getClassLoader().getResource("").toString();
	
	@Autowired
	private IContractService contractServiceImpl;
	@Autowired
	private IFileService fileServiceImpl;
	@Autowired
	private IPrintDataSrcServcie printDataSrcServiceImpl;
	@Autowired
	private IOSSService ossServiceImpl;
	@Value("${pcms.sys_mode}")
	private String sysMode;
	
//	public static final String DEST = "print/8-核准函.pdf";
	@Autowired
	private IPrintCheckLetter printCheckLetterImpl;
	@Override
	public String prtLeaseContract(String businessId,String contextPath,String contractKey) {
		logger.debug("prtLeaseContract");
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		File file = null;
		String contractName = "";
		String ossKey = "";
		try {
			if("run".equals(sysMode)){
				//生产：读取存储上模板
				ContractInfo contractInfo = contractServiceImpl.getContractInfoByKey(contractKey);
				logger.debug("contractInfo:"+JSONObject.toJSONString(contractInfo));
				pdfReader = new PdfReader(ossServiceImpl.getObject(contractInfo.getOssKey()));
			}else{
				//测试：读取本地模板
				pdfReader = new PdfReader(classPath+"/pdf/tpl/1-融资租赁合同-套打-模板.pdf");
			}
			contractName = "1-融资租赁合同-"+businessId+"-"+".pdf";
			ossKey = "resource/"+businessId+"/print/"+contractName;
			file = new File(contextPath+"/"+contractName);
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
//			Font font  = new Font(FontFamily.TIMES_ROMAN, 20);
//			Font font = new Font(bf,10,Font.NORMAL);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				fields.setFieldProperty(entry.getKey(), "textfont", bf, null);
				fields.setFieldProperty(entry.getKey(), "textsize", new Float(6), null); 
				System.out.println(entry.getKey()+"|"+fields.getField(entry.getKey()));
				logger.debug("pdf表单字段："+entry.getKey()+"|"+fields.getField(entry.getKey()));
			}
			//获取贷款合同打印数据源
			PLeaseConstractVo leaseConstractVo = printDataSrcServiceImpl.getPrintLeaseConstract(businessId);
			printDataSrcServiceImpl.setAcroFields(fields,leaseConstractVo);
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
			os.close();
			ossServiceImpl.putObject(ossKey, new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		删除临时文件
		file.delete();
		return ossKey;
	}
	@Override
	public String prtMortgageContract(String businessId,String contextPath,String contractKey) {
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		String ossKey = "";
		File file = null;
		String contractName = "";
		try {
			if("run".equals(sysMode)){
				//生产：读取存储上模板
				ContractInfo contractInfo = contractServiceImpl.getContractInfoByKey(contractKey);
				logger.debug("contractInfo:"+JSONObject.toJSONString(contractInfo));
				pdfReader = new PdfReader(ossServiceImpl.getObject(contractInfo.getOssKey()));
			}else{
				//测试：读取本地模板
				if("dyhtA".equals(contractKey))
					pdfReader = new PdfReader(classPath+"/pdf/tpl/2-抵押合同（版本A）-套打-无背景.pdf");
				else
					pdfReader = new PdfReader(classPath+"/pdf/tpl/2-抵押合同（版本B）-套打-无背景.pdf");
			}
			if("dyhtA".equals(contractKey))
				contractName = "2-抵押合同（版本A）-"+businessId+"-"+".pdf";
			else
				contractName = "2-抵押合同（版本B）-"+businessId+"-"+".pdf";
			ossKey = "resource/"+businessId+"/print/"+contractName;
			file = new File(contextPath+"/"+contractName);
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
//			Font font  = new Font(FontFamily.TIMES_ROMAN, 20);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				fields.setFieldProperty(entry.getKey(), "textfont", bf, null);
				fields.setFieldProperty(entry.getKey(), "textsize", new Float(6), null); 
				System.out.println(entry.getKey());
			}
			PMortgageContractAVo pmca = printDataSrcServiceImpl.getMortgageContract(businessId,contractKey);
			logger.debug("pmca:"+JSONObject.toJSONString(pmca));
			printDataSrcServiceImpl.setAcroFields(fields, pmca);
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
			ossServiceImpl.putObject(ossKey, new FileInputStream(file));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		file.delete();
		return ossKey;
	}

	@Override
	public String prtDeleiverReceipt(String businessId,String contextPath,String contractKey) {
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		String ossKey = "";
		File file = null;
		String contractName = "";
		try {
			if("run".equals(sysMode)){
				//生产：读取存储上模板
				ContractInfo contractInfo = contractServiceImpl.getContractInfoByKey(contractKey);
				logger.debug("contractInfo:"+JSONObject.toJSONString(contractInfo));
				pdfReader = new PdfReader(ossServiceImpl.getObject(contractInfo.getOssKey()));
			}else{
				//测试：读取本地模板
				pdfReader = new PdfReader(classPath+"/pdf/tpl/3-车辆交接单-套打-模板-无背景.pdf");
			}
			contractName = "3-车辆交接单-套打-"+businessId+"-"+".pdf";
			ossKey = "resource/"+businessId+"/print/"+contractName;
			file = new File(contextPath+"/"+contractName);
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				fields.setFieldProperty(entry.getKey(), "textfont", bf, null);
				fields.setFieldProperty(entry.getKey(), "textsize", new Float(8), null); 
			}
			PDeleiverReceiptVo pdrv = printDataSrcServiceImpl.getDeleiverReceipt(businessId);
			printDataSrcServiceImpl.setAcroFields(fields, pdrv);
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
			ossServiceImpl.putObject(ossKey, new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		file.delete();
		return ossKey;
	}
	@Override
	public String prtMortgageList(String businessId,String contextPath,String contractKey) {
		// TODO Auto-generated method stub
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		String ossKey = "";
		int carAmt = 20;//抵押车辆数量
		File file = null;
		String contractName = "";
		try {
			if("run".equals(sysMode)){
				//生产：读取存储上模板
				ContractInfo contractInfo = contractServiceImpl.getContractInfoByKey(contractKey);
				logger.debug("contractInfo:"+JSONObject.toJSONString(contractInfo));
				pdfReader = new PdfReader(ossServiceImpl.getObject(contractInfo.getOssKey()));
			}else{
				//测试：读取本地模板
				pdfReader = new PdfReader(classPath+"/pdf/tpl/4-抵押车辆清单-模板.pdf");
			}
			contractName = "4-抵押车辆清单-"+businessId+"-"+".pdf";
			ossKey = "resource/"+businessId+"/print/"+contractName;
			file = new File(contextPath+"/"+contractName);
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				fields.setFieldProperty(entry.getKey(), "textfont", bf, null);
				fields.setFieldProperty(entry.getKey(), "textsize", new Float(5), null); 
			}
			PMortgageListVo pmlv = printDataSrcServiceImpl.getMortgageList(businessId);
			printDataSrcServiceImpl.setMorgageListFields(fields, pmlv);
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
			ossServiceImpl.putObject(ossKey, new FileInputStream(file));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		file.delete();
		return ossKey;
	}
	@Override
	public String prtLoanReceipt(String businessId,String contextPath,String contractKey) {
		// TODO Auto-generated method stub
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		String ossKey = "";
		File file = null;
		String contractName = "";
		try {
			if("run".equals(sysMode)){
				//生产：读取存储上模板
				ContractInfo contractInfo = contractServiceImpl.getContractInfoByKey(contractKey);
				logger.debug("contractInfo:"+JSONObject.toJSONString(contractInfo));
				pdfReader = new PdfReader(ossServiceImpl.getObject(contractInfo.getOssKey()));
			}else{
				//测试：读取本地模板
				pdfReader = new PdfReader(classPath+"/pdf/tpl/5-收据-模板.pdf");
			}
			contractName = "5-收据-"+businessId+"-"+".pdf";
			ossKey = "resource/"+businessId+"/print/"+contractName;
			file = new File(contextPath+"/"+contractName);
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				fields.setFieldProperty(entry.getKey(), "textfont", bf, null);
				fields.setFieldProperty(entry.getKey(), "textsize", new Float(10), null); 
			}
			PLoanReceiptVo plrv = printDataSrcServiceImpl.getLoanReceipt(businessId);
			printDataSrcServiceImpl.setAcroFields(fields, plrv);
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
			ossServiceImpl.putObject(ossKey, new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		file.delete();
		return ossKey;
	}
	@Override
	public String prtRepayRemind(String businessId,String contextPath,String contractKey) {
		// TODO Auto-generated method stub
		int period = 12;
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		String ossKey = "";
		File file = null;
		String contractName = "";
		try {
			if("run".equals(sysMode)){
				//生产：读取存储上模板
				ContractInfo contractInfo = contractServiceImpl.getContractInfoByKey(contractKey);
				logger.debug("contractInfo:"+JSONObject.toJSONString(contractInfo));
				pdfReader = new PdfReader(ossServiceImpl.getObject(contractInfo.getOssKey()));
			}else{
				//测试：读取本地模板
				pdfReader = new PdfReader(classPath+"/pdf/tpl/6-还款提醒-模板.pdf");
			}
			contractName = "6-还款提醒-"+businessId+"-"+".pdf";
			ossKey = "resource/"+businessId+"/print/"+contractName;
			file = new File(contextPath+"/"+contractName);
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				fields.setFieldProperty(entry.getKey(), "textfont", bf, null);
				fields.setFieldProperty(entry.getKey(), "textsize", new Float(8), null); 
			}
			PRepayRemindVo prrv = printDataSrcServiceImpl.getRepayRemind(businessId);
			printDataSrcServiceImpl.setRepayRemindFields(fields, prrv);
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
			ossServiceImpl.putObject(ossKey, new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		file.delete();
		return ossKey;
	}
	@Override
	public String prtColesseePromise(String businessId,String contextPath,String contractKey) {
		// TODO Auto-generated method stub
		int period = 12;
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		String ossKey = "";
		File file = null;
		String contractName = "";
		try {
			if("run".equals(sysMode)){
				//生产：读取存储上模板
				ContractInfo contractInfo = contractServiceImpl.getContractInfoByKey(contractKey);
				logger.debug("contractInfo:"+JSONObject.toJSONString(contractInfo));
				pdfReader = new PdfReader(ossServiceImpl.getObject(contractInfo.getOssKey()));
			}else{
				//测试：读取本地模板
				pdfReader = new PdfReader(classPath+"/pdf/tpl/7-共同还租承诺书-模板.pdf");
			}
			contractName = "7-共同还租承诺书-"+businessId+"-"+".pdf";
			ossKey = "resource/"+businessId+"/print/"+contractName;
			file = new File(contextPath+"/"+contractName);
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				fields.setFieldProperty(entry.getKey(), "textfont", bf, null);
				if("contractNo".equals(entry.getKey()))
					fields.setFieldProperty(entry.getKey(), "textsize", new Float(8), null); 
				else{
					fields.setFieldProperty(entry.getKey(), "textsize", new Float(12), null); 
				}
			}
			PColesseePromiseVo pcpv = printDataSrcServiceImpl.getColesseePromise(businessId);
			printDataSrcServiceImpl.setAcroFields(fields, pcpv);
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
			ossServiceImpl.putObject(ossKey, new FileInputStream(file));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		file.delete();
		return ossKey;
	}
	
	@Override
	public String prtCheckLetter(String appId,String contextPath, String contractKey) {
		int period = 12;
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		String ossKey = "";
		File file = null;
		String contractName = "";
		try {
			contractName = "8-核准函-"+appId+"-"+".pdf";
			ossKey = "resource/"+appId+"/print/"+contractName;
			file = printCheckLetterImpl.createPdf(appId,contextPath,contractName);
			ossServiceImpl.putObject(ossKey, new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		file.delete();
		return ossKey;
	}
	/**
	 * 测试：读取所上传存储的文件保存至本地
	 * tom 2016年10月31日
	 */
	public void ossReadTest(String ossKey){
		 try {
			InputStream fis = ossServiceImpl.getObject(ossKey);
			File fileOut = new File("d:\\fileOut.pdf");
			FileOutputStream fos = new FileOutputStream(fileOut);
			byte[] buf = new byte[1024];
			int length = 0;
			while((length = fis.read(buf)) > 0){
				fos.write(buf, 0, length);
			}
			fos.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String generateContract(String appId, String contractKey,String contextPath) {
		String ossKey = "";
		switch(contractKey){
		case "zlht":	//租赁合同
			ossKey = this.prtLeaseContract(appId, contextPath, contractKey);
			break;
		case "dyhtA":	//抵押合同A版
			ossKey = this.prtMortgageContract(appId, contextPath, contractKey);
			break;
		case "dyhtB":	//抵押合同B版
			ossKey = this.prtMortgageContract(appId, contextPath, contractKey);
			break;
		case "jjd":		//交接单
			ossKey = this.prtDeleiverReceipt(appId, contextPath, contractKey);
			break;
		case "dyqd":	//抵押清单
			ossKey = this.prtMortgageList(appId, contextPath, contractKey);
			break;
		case "sj":		//收据
			ossKey = this.prtLoanReceipt(appId, contextPath, contractKey);
			break;
		case "hktx":	//还款提醒
			ossKey = this.prtRepayRemind(appId, contextPath, contractKey);
			break;
		case "hzcn":	//还租承诺
			ossKey = this.prtColesseePromise(appId, contextPath, contractKey);
			break;
		case "hzh":		//核准函
			ossKey = this.prtCheckLetter(appId,contextPath,contractKey);
			break;
		}
//		this.ossReadTest(ossKey);
		return ossKey;
	}
}
