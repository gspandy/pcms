package com.pujjr.postloan.enumeration;

/**任务标识
 * @author tom
 */
public enum ETaskTag {
	TQJQ("TQJQ","提前结清"),
	ZHANQI("ZHANQI","展期"),
	BGHKR("BGHKR","变更还款日"),
	DGHK("DGHK","对公还款"),
	TUIKUAN("TUIKUAN","退款"),
	JIANMIAN("JIANMIAN","减免");
	
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

	private ETaskTag(String name,String text){
		this.name = name;
		this.text = text;
	}
}
