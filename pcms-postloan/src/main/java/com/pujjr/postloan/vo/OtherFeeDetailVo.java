package com.pujjr.postloan.vo;
/**
 * 其他费用申请明细对象
 * @author 150032
 *
 */
public class OtherFeeDetailVo 
{
	//明细名称
	private String itemName;
	//明细名称描述
	private String itemNameDesc;
	//明细金额
	private double itemAmount;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemNameDesc() {
		return itemNameDesc;
	}
	public void setItemNameDesc(String itemNameDesc) {
		this.itemNameDesc = itemNameDesc;
	}
	public double getItemAmount() {
		return itemAmount;
	}
	public void setItemAmount(double itemAmount) {
		this.itemAmount = itemAmount;
	}
}
