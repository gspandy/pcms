package com.pujjr.carcredit.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.border.Border;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.service.IBankService;
import com.pujjr.base.service.ICarService;
import com.pujjr.base.service.IProductService;
import com.pujjr.base.service.ISysAreaService;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.service.ISysDictService;
import com.pujjr.carcredit.controller.ApplyController;
import com.pujjr.carcredit.domain.ApplyCloessee;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.IPrintCheckLetter;
import com.pujjr.carcredit.service.IPrintDataSrcServcie;
import com.pujjr.carcredit.service.ISignContractService;
import com.pujjr.carcredit.vo.ApplyCloesseeVo;
import com.pujjr.carcredit.vo.ApplyFinanceVo;
import com.pujjr.carcredit.vo.ApplyTenantVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.LeaseCarVo;

/**
 * @author tom 核准函测试（动态生成pdf）
 */
@Service
@Transactional
public class PrintCheckLetterImpl implements IPrintCheckLetter {
	public static String classPath = PrintServiceImpl.class.getClassLoader().getResource("").toString();
	public static final String DEST = "8-核准函.pdf";
	private Logger logger = Logger.getLogger(PrintDataSrcServiceImpl.class);
	@Autowired
	private IApplyService applyServiceImpl;
	@Autowired
	private ISignContractService signContractServiceImpl;
	@Autowired
	private ICarService carServiceImpl;
	@Autowired
	private ISysAreaService sysAreaServiceImpl;
	@Autowired
	private ISysDictService sysDictServiceImpl;
	@Autowired
	private ISysBranchService sysBranchService;
	@Autowired
	private IBankService bankServiceImpl;
	@Autowired
	private IPrintDataSrcServcie printDataSrcServiceImpl;
	@Autowired
	private IProductService productServiceImpl;
	private int rowHeight1 = 30;
	private int rowHeight2 = 15;
	private int rowHeight3 = 10;

	/**
	 * 签字日期 tom 2016年10月28日
	 */
	public void createSignDate(PdfPTable table, Font fieldNameFont, Font fieldFont) {
		String[] imptTips = new String[] { "出租人签字/盖章", "潽金融资租赁有限公司", "年 月 日" };
		// PdfPCell cell = new PdfPCell(new Paragraph("111", fieldNameFont));

		PdfPTable table2 = new PdfPTable(10);
		// table.setTotalWidth(new
		// float[]{100,100,100,100,100,100,100,100,100,100});
		table2.setWidthPercentage(new Float(10));

		PdfPCell innerCell = new PdfPCell(new Paragraph("出租人签字/盖章", fieldNameFont));
		innerCell.setColspan(10);
		innerCell.setFixedHeight(25);
		innerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		innerCell.setBorder(Rectangle.NO_BORDER);
		table2.addCell(innerCell);

		PdfPCell innerCell2 = new PdfPCell(new Paragraph("潽金融资租赁有限公司", fieldNameFont));
		innerCell2.setColspan(10);
		innerCell2.setFixedHeight(25);
		innerCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		innerCell2.setBorder(Rectangle.NO_BORDER);
		table2.addCell(innerCell2);

		PdfPCell innerCell3 = new PdfPCell(new Paragraph("年           月          日", fieldNameFont));
		innerCell3.setColspan(10);
		innerCell3.setFixedHeight(25);
		innerCell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
		innerCell3.setBorder(Rectangle.NO_BORDER);
		table2.addCell(innerCell3);

		PdfPCell cell = new PdfPCell(table2);
		// Paragraph pgh1 = new Paragraph("111", fieldNameFont);
		// Paragraph pgh2 = new Paragraph("111", fieldNameFont);
		// Paragraph pgh3 = new Paragraph("111", fieldNameFont);
		// cell.addElement(new Phrase("fdsffdsfds"));
		cell.setFixedHeight(75);
		cell.setColspan(10);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);
		/*
		 * for (int i = 0; i < imptTips.length; i++) { if(cell == null) cell =
		 * new PdfPCell(new Paragraph(imptTips[i], fieldNameFont)); // else //
		 * cell.addElement(new Paragraph(imptTips[i], fieldNameFont)); //
		 * Paragraph pgh = new Paragraph(imptTips[i], fieldNameFont); //
		 * cell.addElement(pgh);
		 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		 * cell.setFixedHeight(80); cell.setColspan(10); }
		 */

		/*
		 * PdfPCell cell2 = new PdfPCell(new Paragraph("111", fieldNameFont));
		 * // cell.addElement(new Phrase("fdsffdsfds"));
		 * cell2.setFixedHeight(20); cell2.setColspan(10); //
		 * cell2.setBorder(Rectangle.NO_BORDER);
		 * cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		 * table.addCell(cell2);
		 * 
		 * PdfPCell cell3 = new PdfPCell(new Paragraph("111", fieldNameFont));
		 * // cell.addElement(new Phrase("fdsffdsfds"));
		 * cell3.setFixedHeight(20); cell3.setColspan(10);
		 * cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
		 * table.addCell(cell3);
		 */

	}

	/**
	 * 附加条件 tom 2016年10月28日
	 */
	public void createImportantTips(PdfPTable table, Font fieldNameFont, Font fieldFont) {
		String[] imptTips = new String[] { "重要提示：",
				"1、如发生承租人所购车辆的净车价、车辆所有人名称变更、承租人或保证人提供虚假信息、不接受调整后融资政策、撤销租赁申请等非潽金原因而导致影响审核结果之情形，则潽金有权视",
				"具体情况终止操作该笔租赁业务。", "2、应客户要求，上述租赁申请结果可能发生变更，届时潽金将出具《融资租赁申请核准通知函》，请贵公司以潽金最新出具的版本为准。",
				"3、本通知函不作为贵公司放车的依据，如果承租人不能履行全部租赁措施，则潽金将拒绝放款，请贵公司谨慎确认交付车辆的时间并承担相应的风险。" };
		PdfPCell cell = new PdfPCell();
		for (int i = 0; i < imptTips.length; i++) {
			Paragraph pgh = new Paragraph(imptTips[i], fieldNameFont);
			// PdfPCell carModel = new PdfPCell(new Paragraph("重要提示：",
			// fieldNameFont));
			cell.addElement(pgh);
		}
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setFixedHeight(80);
		cell.setColspan(10);
		table.addCell(cell);

	}

	/**
	 * 附加条件 tom 2016年10月28日
	 */
	public void createAddCondition(PdfPTable table, Font fieldNameFont, Font fieldFont) {
		PdfPCell applyInfo = new PdfPCell(new Paragraph("放款附加条件", fieldNameFont));
		applyInfo.setColspan(10);
		applyInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(applyInfo);

		PdfPCell carModel = new PdfPCell(new Paragraph("放款条件:见票放款 放款附加条件:增加哥哥为共租人，补充提供身份证复印件。", fieldNameFont));
		carModel.setHorizontalAlignment(Element.ALIGN_LEFT);
		carModel.setFixedHeight(100);
		carModel.setColspan(10);
		table.addCell(carModel);
	}

	public void createSmallPdf(String dest) throws IOException, DocumentException {
		// Rectangle small = new Rectangle(290,100);
		Font smallfont = new Font(FontFamily.HELVETICA, 10);
		// Document document = new Document(small, 5, 5, 5, 5);
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
		document.open();
		PdfPTable table = new PdfPTable(2);
		table.setTotalWidth(new float[] { 160, 120 });
		table.setLockedWidth(true);
		PdfContentByte cb = writer.getDirectContent();
		// first row
		PdfPCell cell = new PdfPCell(new Phrase("Some text here"));
		cell.setFixedHeight(30);
		// cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(2);
		table.addCell(cell);
		/*
		 * // second row cell = new PdfPCell(new Phrase("Some more text",
		 * smallfont)); cell.setFixedHeight(30);
		 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		 * cell.setBorder(Rectangle.NO_BORDER); table.addCell(cell); Barcode128
		 * code128 = new Barcode128(); code128.setCode("14785236987541");
		 * code128.setCodeType(Barcode128.CODE128); Image code128Image =
		 * code128.createImageWithBarcode(cb, null, null); cell = new
		 * PdfPCell(code128Image, true); cell.setBorder(Rectangle.NO_BORDER);
		 * cell.setFixedHeight(30); table.addCell(cell); // third row
		 * table.addCell(cell); cell = new PdfPCell(new Phrase(
		 * "and something else here", smallfont));
		 * cell.setBorder(Rectangle.NO_BORDER);
		 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		 * table.addCell(cell);
		 */
		document.add(table);
		document.close();
	}

	public void createFinanceInfo(PdfPTable table, Font fieldNameFont, Font fieldFont, ApplyVo applyVo) {

		// 计算总融资信息
		List<ApplyFinanceVo> finances = applyVo.getFinances();
		System.out.println("*****************applyVo:" + JSONObject.toJSONString(applyVo));
		double TotalSalePrice = 0;
		double TotalPurchaseTax = 0;
		double TotalGpsFee = 0;
		double TotalFinanceFee = 0;
		double TotalServiceFee = 0;
		double TotalTranserFee = 0;
		double TotalInsuranceFee = 0;
		double TotalAddonFee = 0;
		double TotalDelayInsuranceFee = 0;
		for (ApplyFinanceVo finance : finances) {
			if (finance.getSalePrice() == null)
				continue;
			TotalSalePrice += finance.getSalePrice();
			TotalPurchaseTax += finance.getPurchaseTax();
			TotalGpsFee += finance.getFinanceFee();
			TotalFinanceFee += finance.getFinanceFee();
			TotalServiceFee += finance.getServiceFee();
			TotalTranserFee += finance.getTransferFee();
			TotalInsuranceFee += finance.getInsuranceFee();
			TotalAddonFee += finance.getAddonFee();
			TotalDelayInsuranceFee = finance.getDelayInsuranceFee();
		}

		PdfPCell financeInfo = new PdfPCell(new Paragraph("融资信息", fieldNameFont));
		financeInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
		financeInfo.setColspan(10);
		table.addCell(financeInfo);
		// row1 begin
		String[] row1 = new String[] { "融资项目", "裸车价", "GPS费用", "购置税", "服务费", "保险费", "延保费", "过户费", "加装费", "融资手续费" };
		String[] values = new String[] { TotalSalePrice + "", TotalPurchaseTax + "", TotalGpsFee + "",
				TotalFinanceFee + "", TotalServiceFee + "", TotalTranserFee + "", TotalInsuranceFee + "",
				TotalAddonFee + "", TotalDelayInsuranceFee + "" };
		for (int i = 0; i < row1.length; i++) {
			PdfPCell tempFee = new PdfPCell(new Paragraph(row1[i], fieldNameFont));
			tempFee.setFixedHeight(rowHeight2);
			tempFee.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(tempFee);
		}
		// row2 begin
		PdfPCell priceField = new PdfPCell(new Paragraph("价款（元）", fieldNameFont));
		priceField.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(priceField);
		for (int i = 0; i < values.length; i++) {
			PdfPCell salPriceField = new PdfPCell(new Paragraph(values[i], fieldFont));
			salPriceField.setFixedHeight(rowHeight2);
			salPriceField.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(salPriceField);
		}

		// row3 begin
		double initPayPercent = 0;
		double yearRate = 0;
		double collateral = 0;
		double initPayAmount = 0;
		double financeAmount = 0;
		double period = 0;// 融资期限
		double monthRepay = 0;// 月供款待定？？？？2016-10-28
		double repayMode = 0;// 还款方式待定？？？？2016-10-28
		double repayDay = 0;// 约定还款日待定？？？？2016-10-28
		String productCode = applyVo.getProductCode();
		Product product = productServiceImpl.getProductByProductCode(productCode);
		yearRate = product.getYearRate();
		for (int i = 0; i < finances.size(); i++) {
			if (i == 0) {// 存在多条，取第一条20160628
				ApplyFinanceVo finance = finances.get(i);
				collateral = finance.getCollateral();
				try {
					initPayAmount = finance.getInitPayAmount();
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("获取首付款失败");
				}
				try {
					financeAmount = finance.getFinanceAmount();
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("获取融资金额失败");
				}

			}
		}
		period = applyVo.getPeriod();
		String[] row3 = new String[] { "首付比例/抵押比率", "利率", "保证金（元）", "首付款", "融资金额", "融资期限", "月供款（元）", "还款方式", "约定还款日" };
		Double[] valueArray = new Double[] { initPayPercent, yearRate, collateral, initPayAmount, financeAmount, period,
				monthRepay, repayMode, repayDay };
		for (int i = 0; i < row3.length; i++) {
			PdfPCell tempFee = new PdfPCell(new Paragraph(row3[i], fieldNameFont));
			tempFee.setHorizontalAlignment(Element.ALIGN_CENTER);
			if (i == row3.length - 1)
				tempFee.setColspan(2);
			tempFee.setFixedHeight(rowHeight2);
			table.addCell(tempFee);
		}
		for (int i = 0; i < valueArray.length; i++) {
			PdfPCell tempCel = new PdfPCell(new Paragraph(valueArray[i] + "", fieldFont));
			tempCel.setHorizontalAlignment(Element.ALIGN_CENTER);
			if (i == valueArray.length - 1)
				tempCel.setColspan(2);
			tempCel.setFixedHeight(rowHeight2);
			table.addCell(tempCel);
		}
	}

	public void createCarInfo(PdfPTable table, Font fieldNameFont, Font fieldFont, ApplyVo applyVo) {

		PdfPCell applyInfo = new PdfPCell(new Paragraph("车辆信息", fieldNameFont));
		applyInfo.setColspan(10);
		applyInfo.setFixedHeight(rowHeight2);
		applyInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(applyInfo);

		PdfPCell carModel = new PdfPCell(new Paragraph("车型", fieldNameFont));
		carModel.setHorizontalAlignment(Element.ALIGN_CENTER);
		carModel.setColspan(3);
		table.addCell(carModel);

		PdfPCell carColor = new PdfPCell(new Paragraph("颜色", fieldNameFont));
		carColor.setHorizontalAlignment(Element.ALIGN_CENTER);
		carColor.setColspan(1);
		table.addCell(carColor);

		PdfPCell carVin = new PdfPCell(new Paragraph("车架号", fieldNameFont));
		carVin.setHorizontalAlignment(Element.ALIGN_CENTER);
		carVin.setColspan(2);
		table.addCell(carVin);

		PdfPCell carEngineNo = new PdfPCell(new Paragraph("发动机号", fieldNameFont));
		carEngineNo.setHorizontalAlignment(Element.ALIGN_CENTER);
		carEngineNo.setColspan(2);
		table.addCell(carEngineNo);

		PdfPCell dealPrice = new PdfPCell(new Paragraph("出租与承租方协议价（元）", fieldNameFont));
		dealPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
		dealPrice.setColspan(2);
		table.addCell(dealPrice);

		List<LeaseCarVo> leaseCarList = printDataSrcServiceImpl.getLeaseCarList(applyVo);
		for (int i = 0; i < leaseCarList.size(); i++) {
			LeaseCarVo leaseCarVo = leaseCarList.get(i);
			PdfPCell carModelField = new PdfPCell(new Paragraph(leaseCarVo.getBrandSerial(), fieldFont));
			carModelField.setHorizontalAlignment(Element.ALIGN_LEFT);
			carModelField.setColspan(3);
			carModelField.setFixedHeight(rowHeight2);
			table.addCell(carModelField);
			PdfPCell carColorField = new PdfPCell(new Paragraph(leaseCarVo.getCarColor(), fieldFont));
			carColorField.setHorizontalAlignment(Element.ALIGN_LEFT);
			carColorField.setColspan(1);
			table.addCell(carColorField);
			PdfPCell carVinField = new PdfPCell(new Paragraph(leaseCarVo.getCarVin(), fieldFont));
			carVinField.setHorizontalAlignment(Element.ALIGN_LEFT);
			carVinField.setColspan(2);
			table.addCell(carVinField);
			PdfPCell carEngineNoField = new PdfPCell(new Paragraph(leaseCarVo.getCarEngineNo(), fieldFont));
			carEngineNoField.setHorizontalAlignment(Element.ALIGN_LEFT);
			carEngineNoField.setColspan(2);
			table.addCell(carEngineNoField);
			PdfPCell dealPriceField = new PdfPCell(new Paragraph("待定...", fieldFont));
			dealPriceField.setHorizontalAlignment(Element.ALIGN_LEFT);
			dealPriceField.setColspan(2);
			table.addCell(dealPriceField);
		}
	}

	public void createTenantInfo(PdfPTable table, Font fieldNameFont, Font fieldFont, ApplyVo applyVo) {
		ApplyTenantVo applyTenantVo = applyVo.getTenant();
		ApplyCloessee applyCloessee = applyVo.getCloessee();
		PdfPCell applyInfo = new PdfPCell(new Paragraph("申请人信息", fieldNameFont));
		applyInfo.setColspan(10);
		applyInfo.setFixedHeight(rowHeight2);
		applyInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(applyInfo);

		PdfPCell tenant = new PdfPCell(new Paragraph("承租人:", fieldNameFont));
		tenant.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tenant.setColspan(1);
		tenant.setFixedHeight(rowHeight2);
		table.addCell(tenant);
		PdfPCell tenantField = new PdfPCell(new Paragraph(applyTenantVo.getName(), fieldFont));
		tenantField.setHorizontalAlignment(Element.ALIGN_LEFT);
		tenantField.setColspan(2);
		applyInfo.setFixedHeight(rowHeight2);
		table.addCell(tenantField);

		PdfPCell idNo = new PdfPCell(new Paragraph("证件号码：", fieldNameFont));
		idNo.setHorizontalAlignment(Element.ALIGN_RIGHT);
		idNo.setColspan(1);
		applyInfo.setFixedHeight(rowHeight2);
		table.addCell(idNo);
		PdfPCell idNoField = new PdfPCell(new Paragraph(applyTenantVo.getIdNo(), fieldFont));
		idNoField.setHorizontalAlignment(Element.ALIGN_LEFT);
		idNoField.setColspan(2);
		applyInfo.setFixedHeight(rowHeight2);
		table.addCell(idNoField);

		PdfPCell mobile = new PdfPCell(new Paragraph("联系电话：", fieldNameFont));
		mobile.setHorizontalAlignment(Element.ALIGN_RIGHT);
		mobile.setColspan(1);
		applyInfo.setFixedHeight(rowHeight2);
		table.addCell(mobile);

		PdfPCell mobileField = new PdfPCell(new Paragraph(applyTenantVo.getMobile(), fieldFont));
		mobileField.setHorizontalAlignment(Element.ALIGN_LEFT);
		mobileField.setColspan(3);
		applyInfo.setFixedHeight(rowHeight2);
		table.addCell(mobileField);

		PdfPCell colessee = new PdfPCell(new Paragraph("共同借款人:", fieldNameFont));
		colessee.setHorizontalAlignment(Element.ALIGN_RIGHT);
		colessee.setColspan(1);
		colessee.setFixedHeight(rowHeight2);
		table.addCell(colessee);
		PdfPCell colesseeField = new PdfPCell(new Paragraph(applyCloessee.getName(), fieldFont));
		colesseeField.setHorizontalAlignment(Element.ALIGN_LEFT);
		colesseeField.setColspan(2);
		table.addCell(colesseeField);

		PdfPCell clIdNo = new PdfPCell(new Paragraph("证件号码：", fieldNameFont));
		clIdNo.setHorizontalAlignment(Element.ALIGN_RIGHT);
		clIdNo.setColspan(1);
		table.addCell(clIdNo);
		PdfPCell cIdNoField = new PdfPCell(new Paragraph(applyCloessee.getIdNo(), fieldFont));
		cIdNoField.setHorizontalAlignment(Element.ALIGN_LEFT);
		cIdNoField.setColspan(2);
		table.addCell(cIdNoField);

		PdfPCell clMobile = new PdfPCell(new Paragraph("联系电话：", fieldNameFont));
		clMobile.setHorizontalAlignment(Element.ALIGN_RIGHT);
		clMobile.setColspan(1);
		table.addCell(clMobile);
		PdfPCell clMobileField = new PdfPCell(new Paragraph(applyCloessee.getMobile(), fieldFont));
		clMobileField.setHorizontalAlignment(Element.ALIGN_LEFT);
		clMobileField.setColspan(3);
		table.addCell(clMobileField);
	}

	@Override
	public void createPdf(String appId, String contextPath, String ossKey) {
		Document document = new Document();
		PdfWriter pdfWriter = null;
		File file = null;
		try {
			BaseFont bf = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
			file = new File(contextPath + "\\" + PrintCheckLetterImpl.DEST);
			if (!file.exists())
				file.createNewFile();
			pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			PdfPTable table = new PdfPTable(10);
			// table.setTotalWidth(new
			// float[]{100,100,100,100,100,100,100,100,100,100});
			table.setWidthPercentage(new Float(110));
			Font titleFont = new Font(bf, 16, Font.BOLD);
			Font orgFont = new Font(bf, 12, Font.BOLD);// 经销商字体
			Font welcFont = new Font(bf, 10, Font.NORMAL);// 首段字体
			Font fieldNameFont = new Font(bf, 8, Font.BOLD);// 字段名字体
			Font fieldFont = new Font(bf, 8, Font.NORMAL);// 字段值字体（正文字体）
			Font underLineFont = new Font(bf, 10, Font.UNDERLINE);// 下划线字体

			Image logoImage2 = Image.getInstance(contextPath + "/resource/print/logo.jpg");
			logoImage2.scalePercent(4);
			logoImage2.setAbsolutePosition(10, 810);
			document.add(logoImage2);

			/*
			 * Image logoImage = Image.getInstance("print/logo.jpg");
			 * logoImage.setWidthPercentage(15); PdfPCell logoCell = new
			 * PdfPCell(); logoCell.addElement(logoImage);
			 * logoCell.setColspan(10); logoCell.setBorder(Rectangle.NO_BORDER);
			 * logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * table.addCell(logoCell);
			 */

			Paragraph title = new Paragraph("核准通知函", titleFont);
			PdfPCell titleCell = new PdfPCell(title);
			titleCell.setFixedHeight(rowHeight1);
			titleCell.setBorder(Rectangle.NO_BORDER);
			titleCell.setColspan(10);
			titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(titleCell);

			ApplyVo applyVo = applyServiceImpl.getApplyDetail(appId);
			ApplyTenantVo applyTenant = applyVo.getTenant();
			ApplyCloesseeVo applyColessee = applyVo.getCloessee();

			PdfPCell branchCell = new PdfPCell();
			SysBranch sysBranch = sysBranchService.getSysBranch("", applyVo.getCreateBranchCode());
			String branchName = sysBranch.getBranchName();
			Paragraph branchPgh = new Paragraph("尊敬的:" + branchName + ":", orgFont);
			branchCell.setColspan(10);
			branchCell.setBorder(Rectangle.NO_BORDER);
			branchCell.addElement(branchPgh);
			table.addCell(branchCell);

			PdfPCell welcCell = new PdfPCell();
			String wel1 = "感谢贵公司提交客户：";
			String wel2 = applyTenant.getName() + "      ";// 承租人
			String wel3 = "的租赁申请，该项租赁申请，经审已被核准，具体核准日期为：";
			String wel4 = "2016/10/27 17:10:01";// 核准日期
			String wel5 = "。敬请确认以下信息，如有错误请务必及时告知。本核准函有效期为60日（自核准日次日起算）。十分感谢您的支持！";
			// Phrase firstPgh = new Phrase();
			Paragraph firstPgh = new Paragraph();
			firstPgh.add(new Phrase(wel1, welcFont));
			firstPgh.add(new Phrase(wel2, underLineFont));
			firstPgh.add(new Phrase(wel3, welcFont));
			firstPgh.add(new Phrase(wel4, underLineFont));
			firstPgh.add(new Phrase(wel5, welcFont));
			welcCell.setColspan(10);
			welcCell.setFixedHeight(45);
			welcCell.setBorder(Rectangle.NO_BORDER);
			welcCell.addElement(firstPgh);
			table.addCell(welcCell);

			this.createTenantInfo(table, fieldNameFont, fieldFont, applyVo);
			this.createCarInfo(table, fieldNameFont, fieldFont, applyVo);
			this.createFinanceInfo(table, fieldNameFont, fieldFont, applyVo);
			this.createAddCondition(table, fieldNameFont, fieldFont);
			this.createImportantTips(table, fieldNameFont, fieldFont);
			this.createSignDate(table, fieldNameFont, fieldFont);
			document.add(table);
			document.close();
			pdfWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
