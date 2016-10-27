package com.pujjr.carcredit.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.pujjr.carcredit.service.IPrintDataSrcServcie;
import com.pujjr.carcredit.service.IPrintService;
import com.pujjr.carcredit.vo.PLeaseConstractVo;
import com.pujjr.carcredit.vo.PMortgageContractAVo;
import com.pujjr.file.service.IFileService;
import com.pujjr.utils.Utils;

@Service
@Transactional
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
			file = new File(contextPath+"/print/"+contractName);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
//		删除临时文件
//		file.delete();
		return ossKey;
	}
	@Override
	public String prtMortgageContractA(String businessId,String contextPath,String contractKey) {
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
				pdfReader = new PdfReader(classPath+"/pdf/tpl/2-抵押合同（版本A）-套打-无背景.pdf");
			}
			contractName = "2-抵押合同（版本A）-"+businessId+"-"+".pdf";
			ossKey = "resource/"+businessId+"/print/"+contractName;
			file = new File(contextPath+"/print/"+contractName);
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
			PMortgageContractAVo pmca = printDataSrcServiceImpl.getMortgageContractA(businessId);
			logger.debug("pmca:"+JSONObject.toJSONString(pmca));
			printDataSrcServiceImpl.setAcroFields(fields, pmca);
			/*
//					PdfMgc pdfMgc = new PdfMgc();
			fields.setField("contactNo1", "200810405234");
//					fields.setFieldProperty("contactNo1", "200810405234", bf, null);
			fields.setField("tenant","ll潽金公司");
			fields.setField("idNo","ll500383198808181994");
			fields.setField("contactNo2","ll200810405234");
			fields.setField("","ll200810405234");
			fields.setField("financeAmt","ll10000");
			fields.setField("financeAmtChn","ll壹万元整");
			fields.setField("pledgerName","ll王二娃");
			fields.setField("pledgerCtftype","ll身份证");
			fields.setField("pledgerCtfNo","ll500383198808181996");
			fields.setField("pledgerPhone","ll18723290701");
			fields.setField("pledgerAddress","ll重庆渝北区");
			
			fields.setField("carBrand", "ll奔驰");
			fields.setField("carEnginNo", "ll奔驰发动机250");
			fields.setField("carModelNo", "ll奔驰ML300");
			fields.setField("carColor", "ll黑色");
			fields.setField("carFrameNo", "ll车架号110");
			fields.setField("carPlateNo", "ll渝A8888");
			fields.setField("startYear","2015");
			fields.setField("startMonth","10");
			fields.setField("startDay","21");
			fields.setField("endYear","2016");
			fields.setField("endMonth","11");
			fields.setField("endDay","14");*/
			
			
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ossKey;
	}
	@Override
	public String prtMortgageContractB(String businessId,String contextPath,String contractKey) {
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
				pdfReader = new PdfReader(classPath+"/pdf/tpl/2-抵押合同（版本B）-套打-有背景.pdf");
			}
			contractName = "2-抵押合同（版本B）-"+businessId+"-"+".pdf";
			ossKey = "resource/"+businessId+"/print/"+contractName;
			file = new File(contextPath+"/print/"+contractName);
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
			Font font  = new Font(FontFamily.TIMES_ROMAN, 20);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				System.out.println(entry.getKey());
			}
//			PdfMgc pdfMgc = new PdfMgc();
			fields.setField("contactNo1", "200810405234");
//			fields.setFieldProperty("contactNo1", "200810405234", bf, null);
			fields.setField("tenant","ll潽金公司");
			fields.setField("idNo","ll500383198808181994");
			fields.setField("contactNo2","ll200810405234");
			fields.setField("","ll200810405234");
			fields.setField("financeAmt","ll10000");
			fields.setField("financeAmtChn","ll壹万元整");
			fields.setField("pledgerName","ll王二娃");
			fields.setField("pledgerCtftype","ll身份证");
			fields.setField("pledgerCtfNo","ll500383198808181996");
			fields.setField("pledgerPhone","ll18723290701");
			fields.setField("pledgerAddress","ll重庆渝北区");
			
			fields.setField("carBrand", "ll奔驰");
			fields.setField("carEnginNo", "ll奔驰发动机250");
			fields.setField("carModelNo", "ll奔驰ML300");
			fields.setField("carColor", "ll黑色");
			fields.setField("carFrameNo", "ll车架号110");
			fields.setField("carPlateNo", "ll渝A8888");
			fields.setField("startYear","2015");
			fields.setField("startMonth","10");
			fields.setField("startDay","21");
			fields.setField("endYear","2016");
			fields.setField("endMonth","11");
			fields.setField("endDay","14");
			
			
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ossKey;
	}
	@Override
	public String prtDeleiverReceipt(String businessId,String contextPath,String contractKey) {
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
				pdfReader = new PdfReader(classPath+"/pdf/tpl/3-车辆交接单-套打-模板-有背景.pdf");
			}
//			File file = new File("print/2-抵押合同（版本B）-套打-print.pdf");
			contractName = "3-车辆交接单-套打-"+businessId+"-"+".pdf";
			ossKey = "resource/"+businessId+"/print/"+contractName;
			file = new File(contextPath+"/print/"+contractName);
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
			Font font = new Font(bf,10,Font.NORMAL);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				fields.setFieldProperty(entry.getKey(), "textfont", bf, null);
				fields.setFieldProperty(entry.getKey(), "textsize", new Float(8), null); 
				System.out.println(entry.getKey()+"|"+fields.getField(entry.getKey()));
			}
			fields.setField("carStyle","潽金carStyle租赁有限公司");
			fields.setField("plateNo","plateNo");
			fields.setField("carColor","云南carColor电子信息产业股份有限公司");
			fields.setField("carEngineNo","carEngineNo");
			fields.setField("carVin", "carVin");
			fields.setField("dealerName", "dealerName");
			fields.setField("tenantName", "tenantIdNo");
			fields.setField("tenantIdNo", "tenantIdNo");
			fields.setField("tenantPhone", "tenantPhone");
			fields.setField("tenantSign", "tenantSign");
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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
//			File file = new File("print/2-抵押合同（版本B）-套打-print.pdf");
			contractName = "4-抵押车辆清单-"+businessId+"-"+".pdf";
			ossKey = "resource/"+businessId+"/print/"+contractName;
			file = new File(contextPath+"/print/"+contractName);
			
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
			Font font = new Font(bf,10,Font.NORMAL);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				fields.setFieldProperty(entry.getKey(), "textfont", bf, null);
				fields.setFieldProperty(entry.getKey(), "textsize", new Float(8), null); 
				System.out.println(entry.getKey()+"|"+fields.getField(entry.getKey()));
			}
			fields.setField("contractNo","200810405234");
			for (int i = 0; i < carAmt; i++) {
				fields.setField("fill_"+(6*i+2),"车牌号"+i+1);
				fields.setField("fill_"+(6*i+3),"品牌及车型"+i+1);
				fields.setField("fill_"+(6*i+4),"车架号"+i+1);
				fields.setField("fill_"+(6*i+5), "发动机号"+i+1);
				fields.setField("fill_"+(6*i+6), "车辆颜色"+i+1);
				fields.setField("fill_"+(6*i+7), "生产商"+i+1);
			}
			fields.setField("carAmt", carAmt+"");
			fields.setField("year", "2016");
			fields.setField("month", "10");
			fields.setField("day", "12");
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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
//			File file = new File("print/2-抵押合同（版本B）-套打-print.pdf");
			contractName = "5-收据-"+businessId+"-"+".pdf";
			ossKey = "resource/"+businessId+"/print/"+contractName;
			file = new File(contextPath+"/print/"+contractName);
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
			Font font = new Font(bf,10,Font.NORMAL);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				fields.setFieldProperty(entry.getKey(), "textfont", bf, null);
				fields.setFieldProperty(entry.getKey(), "textsize", new Float(7), null); 
				System.out.println(entry.getKey()+"|"+fields.getField(entry.getKey()));
			}
			fields.setField("contractNo", "GZZJ-HT2013-JS001");
//			fields.setFieldProperty("contractNo","textsize",new Float(6),null);
			fields.setField("totalLoanAmt", "8563");
			fields.setField("branchName", "云南南天电子信息产业股份有限公司");
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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
//			File file = new File("print/2-抵押合同（版本B）-套打-print.pdf");
			contractName = "6-还款提醒-"+businessId+"-"+".pdf";
			ossKey = "resource/"+businessId+"/print/"+contractName;
			file = new File(contextPath+"/print/"+contractName);
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
//			BaseFont bf = BaseFont.createFont(classPath+"/MSYH.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf,10,Font.NORMAL);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				fields.setFieldProperty(entry.getKey(), "textfont", bf, null);
				fields.setFieldProperty(entry.getKey(), "textsize", new Float(8), null); 
				System.out.println(entry.getKey()+"|"+fields.getField(entry.getKey()));
			}
			for(int i = 0;i < period;i++){
				fields.setField("fill_"+(i*2+1), "2015-01-01");
				fields.setField("fill_"+(i*2+2), "8657");
			}
			fields.setField("accountName", "阿布拉多.艾尼瓦尔.萨迪克买买提");
//			fields.setFieldProperty("contractNo","textsize",new Float(6),null);
			fields.setField("contractNo", " N220160621068");
			fields.setField("totalFinanceAmt", "云南南天电子信息产业股份有限公司");
			fields.setField("period", "12");
			fields.setField("firstRepayDate", "2016-12-11");
			fields.setField("firstRepayFee", "8657");
			
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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
			file = new File(contextPath+"/print/"+contractName);
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			System.out.println("*******************:"+file.length());
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
//			BaseFont bf = BaseFont.createFont(classPath+"/MSYH.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf,10,Font.NORMAL);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				fields.setFieldProperty(entry.getKey(), "textfont", bf, null);
				if("contractNo".equals(entry.getKey()))
					fields.setFieldProperty(entry.getKey(), "textsize", new Float(8), null); 
				else{
					fields.setFieldProperty(entry.getKey(), "textsize", new Float(12), null); 
				}
				System.out.println(entry.getKey()+"|"+fields.getField(entry.getKey()));
			}
			for(int i = 0;i < period;i++){
				fields.setField("fill_"+(i*2+1), "2015-01-01");
				fields.setField("fill_"+(i*2+2), "8657");
			}
//			fields.setFieldProperty("contractNo","textsize",new Float(6),null);
			fields.setField("contractNo", " N220160621068");
			fields.setField("colesseeName", "共租人");
			fields.setField("colesseeIdNo", "500383198808181994");
			fields.setField("tenantName", "承租人");
			fields.setField("tenantIdNo", "500383198808181994");
			fields.setField("relation", "妃子");
			
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
			System.out.println("*******************:"+file.length());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ossKey;
	}
	@Override
	public String generateContract(String appId, String contractKey,String contextPath) {
		String ossKey = "";
		switch(contractKey){
		case "zlht":	//租赁合同
			ossKey = this.prtLeaseContract(appId, contextPath, contractKey);
			break;
		case "dyhtA":	//抵押合同A版
			ossKey = this.prtMortgageContractA(appId, contextPath, contractKey);;
			break;
		case "dyhtB":	//抵押合同B版
			ossKey = this.prtMortgageContractB(appId, contextPath, contractKey);
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
		}
		return ossKey;
	}
}
