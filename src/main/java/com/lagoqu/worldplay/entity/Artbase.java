package com.lagoqu.worldplay.entity;

import java.sql.Timestamp;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;
/**描述：文字库<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月29日上午11:14:47 <br>
 * E-mail:  <br> 
 */
@ATable(name = "Artbase", pkname = "artbaseID")
public class Artbase extends BaseEntity{

	public int artbaseID;   //ID 
	public String artbaseContent;  //内容
	public int artbaseType;   //类型
	public int artbaseMark;   //角标
	public String artbaseMarkPath;  //角标路径
	public int artbaseState;    //状态
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
	public int getArtbaseID() {
		return artbaseID;
	}
	public void setArtbaseID(int artbaseID) {
		this.artbaseID = artbaseID;
	}
	public String getArtbaseContent() {
		return artbaseContent;
	}
	public void setArtbaseContent(String artbaseContent) {
		this.artbaseContent = artbaseContent;
	}
	public int getArtbaseType() {
		return artbaseType;
	}
	public void setArtbaseType(int artbaseType) {
		this.artbaseType = artbaseType;
	}
	public int getArtbaseMark() {
		return artbaseMark;
	}
	public void setArtbaseMark(int artbaseMark) {
		this.artbaseMark = artbaseMark;
	}
	public String getArtbaseMarkPath() {
		return artbaseMarkPath;
	}
	public void setArtbaseMarkPath(String artbaseMarkPath) {
		this.artbaseMarkPath = artbaseMarkPath;
	}
	public int getArtbaseState() {
		return artbaseState;
	}
	public void setArtbaseState(int artbaseState) {
		this.artbaseState = artbaseState;
	}
	
	
	
	
}
