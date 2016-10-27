package com.pujjr.carcredit.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.pujjr.carcredit.service.IPrintDataSrcServcie;
import com.pujjr.carcredit.service.IPrintService;
import com.pujjr.carcredit.vo.LeaseConstractVo;
import com.pujjr.file.service.IFileService;

@Service
@Transactional
public class PrintServiceImpl implements IPrintService {
	private Logger logger = Logger.getLogger(PrintServiceImpl.class);
	public static String classPath = PrintServiceImpl.class.getClassLoader().getResource("").toString();
	String url = PrintServiceImpl.class.getClassLoader().getResource("").toString();
	@Autowired
	private IFileService fileServiceImpl;
	@Autowired
	private IPrintDataSrcServcie printDataSrcServiceImpl;
	@Override
	public void prtLeaseContract(String operId,String businessId,String url) {
		logger.debug("prtLeaseContract");
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		File file = null;
		try {
			//测试：读取本地末班
			pdfReader = new PdfReader(classPath+"/pdf/tpl/1-融资租赁合同-套打-模板.pdf");
			//生产：读取存储上模板
//			pdfReader = new PdfReader();
			file = new File(url+"/print/1-融资租赁合同-print.pdf");
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
//			Font font  = new Font(FontFamily.TIMES_ROMAN, 20);
			Font font = new Font(bf,10,Font.NORMAL);
			AcroFields fields = pdfStamper.getAcroFields();
			Map<String, Item> map = fields.getFields();
			for(Map.Entry<String, Item> entry:map.entrySet()){
				fields.setFieldProperty(entry.getKey(), "textfont", bf, null);
				fields.setFieldProperty(entry.getKey(), "textsize", new Float(6), null); 
				System.out.println(entry.getKey()+"|"+fields.getField(entry.getKey()));
			}
			//获取贷款合同打印数据源
			LeaseConstractVo leaseConstractVo = printDataSrcServiceImpl.getPrintLeaseConstract(businessId);
			printDataSrcServiceImpl.setAcroFields(fields,leaseConstractVo);
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			pdfReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		生产：文件上传存储
//		删除临时文件
//		file.delete();
	}
	@Override
	public void prtMortgageContract(String operId, String businessId) {
		// TODO Auto-generated method stub
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		try {
			pdfReader = new PdfReader(classPath+"/pdf/tpl/2-抵押合同（版本A）-套打-有背景.pdf");
//			os = new FileOutputStream("d:\\潽金融资租赁有限公司抵押合同B-print.pdf");
//			File file = new File(classPath+"/pdf/print/潽金融资租赁有限公司抵押合同B-print.pdf");
			File file = new File("print/2-抵押合同（版本B）-套打-print.pdf");
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
	}
	@Override
	public void prtDeleiverReceipt(String operId, String bussinessId) {
		// TODO Auto-generated method stub
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		try {
			pdfReader = new PdfReader(classPath+"/pdf/tpl/3-车辆交接单-套打-模板-有背景.pdf");
			File file = new File("print/3-车辆交接单-套打-模板-print.pdf");
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
	}
	@Override
	public void prtMortgageList(String operId, String businessId) {
		// TODO Auto-generated method stub
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		int carAmt = 20;//抵押车辆数量
		try {
			pdfReader = new PdfReader(classPath+"/pdf/tpl/4-抵押车辆清单-模板.pdf");
			File file = new File("print/4-抵押车辆清单-print.pdf");
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
	}
	@Override
	public void prtLoanReceipt(String operId, String businessId) {
		// TODO Auto-generated method stub
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		try {
			pdfReader = new PdfReader(classPath+"/pdf/tpl/5-收据-模板.pdf");
			File file = new File("print/5-收据-print.pdf");
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
	}
	@Override
	public void prtRepayRemind(String operId, String businessId) {
		// TODO Auto-generated method stub
		int period = 12;
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		
		try {
			pdfReader = new PdfReader(classPath+"/pdf/tpl/6-还款提醒-模板.pdf");
			File file = new File("print/6-还款提醒-print.pdf");
			if(file.exists())
				file.createNewFile();
			os = new FileOutputStream(file);
			pdfStamper = new PdfStamper(pdfReader, os);
//			BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
			BaseFont bf = BaseFont.createFont(classPath+"/MSYH.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
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
	}
	@Override
	public void prtColesseePromise(String operId, String businessId) {
		// TODO Auto-generated method stub
		int period = 12;
		PdfReader pdfReader = null;
		OutputStream os = null;
		PdfStamper pdfStamper = null;
		
		try {
			pdfReader = new PdfReader(classPath+"/pdf/tpl/7-共同还租承诺书-模板.pdf");
			File file = new File("print/7-共同还租承诺书-print.pdf");
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
	}
	@Override
	public String generateContract(String appId, String contractKey) {
		// TODO Auto-generated method stub
		return "1.pdf";
	}
}
