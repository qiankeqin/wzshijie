package com.lagoqu.worldplay.entity;

import java.sql.Timestamp;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**描述：图片库<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月29日上午11:04:34 <br>
 * E-mail:  <br> 
 */
@ATable(name = "PhotoGallery", pkname = "photoGalleryID")
public class PhotoGallery extends BaseEntity{

	public int photoGalleryID;      //ID
	public String photoGalleryName; //图片名称
	public String photoGalleryPath; //图片路径
	public int plSysVersionType;    //图片分类
	public String photoGallerySubscriptPath;  //图片角标路径
	public int photoGalleryState;    //图片状态
	public int photoGallerySort;     //图片排序
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
	public int getPhotoGalleryID() {
		return photoGalleryID;
	}
	public void setPhotoGalleryID(int photoGalleryID) {
		this.photoGalleryID = photoGalleryID;
	}
	public String getPhotoGalleryName() {
		return photoGalleryName;
	}
	public void setPhotoGalleryName(String photoGalleryName) {
		this.photoGalleryName = photoGalleryName;
	}
	public String getPhotoGalleryPath() {
		return photoGalleryPath;
	}
	public void setPhotoGalleryPath(String photoGalleryPath) {
		this.photoGalleryPath = photoGalleryPath;
	}
	public int getPlSysVersionType() {
		return plSysVersionType;
	}
	public void setPlSysVersionType(int plSysVersionType) {
		this.plSysVersionType = plSysVersionType;
	}
	public String getPhotoGallerySubscriptPath() {
		return photoGallerySubscriptPath;
	}
	public void setPhotoGallerySubscriptPath(String photoGallerySubscriptPath) {
		this.photoGallerySubscriptPath = photoGallerySubscriptPath;
	}
	public int getPhotoGalleryState() {
		return photoGalleryState;
	}
	public void setPhotoGalleryState(int photoGalleryState) {
		this.photoGalleryState = photoGalleryState;
	}
	public int getPhotoGallerySort() {
		return photoGallerySort;
	}
	public void setPhotoGallerySort(int photoGallerySort) {
		this.photoGallerySort = photoGallerySort;
	}
	
	
	
}
