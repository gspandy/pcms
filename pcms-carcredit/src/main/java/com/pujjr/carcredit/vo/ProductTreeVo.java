package com.pujjr.carcredit.vo;

import java.util.ArrayList;
import java.util.List;

import com.pujjr.base.domain.Product;
/**产品树结构**/
public class ProductTreeVo extends Product 
{
	/**显示标签信息**/
	private String label ;
	/**上级节点ID**/
	private String parentId;
	
	/**1-产品 2-产品分类**/
	private String nodeType;
	
	private List<ProductTreeVo> children = new ArrayList<ProductTreeVo>();

	public List<ProductTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<ProductTreeVo> children) {
		this.children = children;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
