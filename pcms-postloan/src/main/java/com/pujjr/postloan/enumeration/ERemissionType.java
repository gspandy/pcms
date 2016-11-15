package com.pujjr.postloan.enumeration;

/**减免类型
 * @author tom
 *
 */

public enum ERemissionType {
	REMISSION("jmlx01","正常减免"),
	SETTLE("jmlx02","提前结清减免"),
	EXTENDPERIOD("jmlx03","展期减免"),
	PUBLICREPAY("jmlx04","对公还款减免");
	
	private String name;
	private String text;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	private ERemissionType(String name,String text){
		this.name = name;
		this.text = name;
	}
}
