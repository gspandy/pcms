package com.pujjr.postloan.enumeration;

/**计息方式
 * @author tom
 *
 */
public enum EInterestMode {
	CPM("CPM","等额本息","hkfs01"),
	CONST("CONST","等额利息","hkfs04"),
	ONETIME("ONETIME","一次付息按月还款","hkfs03"),
	MONTLY("MONTLY","按月付息到期还本","hkfs02");
	
	private String name;
	private String text;
	private String dictCode;
	
	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

	public String getDictCode() {
		return dictCode;
	}


	private EInterestMode(String name,String text,String dictCode){
		this.name = name;
		this.text = text;
		this.dictCode = dictCode;
	}
}
