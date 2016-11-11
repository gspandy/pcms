package com.pujjr.postloan.enumeration;

/**计息方式
 * @author tom
 *
 */
public enum EInterestMode {
	CPM("CPM","等额本息"),
	CONST("CONST","等额利息"),
	ONETIME("ONETIME","一次付息按月还款"),
	MONTLY("MONTLY","按月付息到期还本");
	
	private String name;
	private String text;
	
	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

	private EInterestMode(String name,String text){
		this.name = name;
		this.text = text;
	}
}
