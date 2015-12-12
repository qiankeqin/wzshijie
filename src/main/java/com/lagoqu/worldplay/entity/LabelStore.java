package com.lagoqu.worldplay.entity;

import java.sql.Timestamp;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**描述：标签库<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月29日上午11:13:06 <br>
 * E-mail:  <br> 
 */
@ATable(name = "LabelStore", pkname = "LabelStoreID")
public class LabelStore extends BaseEntity{

	public int labelStoreID;    //ID
	public String labelStoreName;  //标签名字
	public int labelStoreState;    //状态
	public int labelStoreSort;   //排序
	private String operationName;     //操作人
	private Timestamp operationTime;  //操作时间
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public Timestamp getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Timestamp operationTime) {
		this.operationTime = operationTime;
	}
	public int getLabelStoreID() {
		return labelStoreID;
	}
	public void setLabelStoreID(int labelStoreID) {
		this.labelStoreID = labelStoreID;
	}
	public String getLabelStoreName() {
		return labelStoreName;
	}
	public void setLabelStoreName(String labelStoreName) {
		this.labelStoreName = labelStoreName;
	}
	public int getLabelStoreState() {
		return labelStoreState;
	}
	public void setLabelStoreState(int labelStoreState) {
		this.labelStoreState = labelStoreState;
	}
	public int getLabelStoreSort() {
		return labelStoreSort;
	}
	public void setLabelStoreSort(int labelStoreSort) {
		this.labelStoreSort = labelStoreSort;
	}
	
	
	
}
