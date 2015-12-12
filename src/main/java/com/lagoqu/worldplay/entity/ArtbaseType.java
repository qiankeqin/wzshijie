package com.lagoqu.worldplay.entity;

import java.sql.Timestamp;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;
@ATable(name = "ArtbaseType", pkname = "artbaseTypeID")
public class ArtbaseType extends BaseEntity{

	private int artbaseTypeID;        //id
	private String artbaseTypeName;   //类型名称
	private int artbaseTypeState;      //状态
	private int artbaseTypeSort;      //排序
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
	

	public int getArtbaseTypeID() {
		return artbaseTypeID;
	}
	public void setArtbaseTypeID(int artbaseTypeID) {
		this.artbaseTypeID = artbaseTypeID;
	}
	public String getArtbaseTypeName() {
		return artbaseTypeName;
	}
	public void setArtbaseTypeName(String artbaseTypeName) {
		this.artbaseTypeName = artbaseTypeName;
	}
	public int getArtbaseTypeState() {
		return artbaseTypeState;
	}
	public void setArtbaseTypeState(int artbaseTypeState) {
		this.artbaseTypeState = artbaseTypeState;
	}
	public int getArtbaseTypeSort() {
		return artbaseTypeSort;
	}
	public void setArtbaseTypeSort(int artbaseTypeSort) {
		this.artbaseTypeSort = artbaseTypeSort;
	}
	
}
