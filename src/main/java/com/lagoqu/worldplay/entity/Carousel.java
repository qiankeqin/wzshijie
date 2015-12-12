package com.lagoqu.worldplay.entity;

import java.sql.Timestamp;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;
/**描述：轮播图<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月29日上午10:42:44 <br>
 * E-mail:  <br> 
 */
@ATable(name = "Carousel", pkname = "carouselID")
public class Carousel extends BaseEntity{

	public int carouselID;     //ID
	public String carouselName; //标题
	public String carouselPosition;  //位置
	public String carouselPath;    //图片路径
	public int carouselType;     //分类 0 心愿 1 足迹 2 无动作 3 链接
	public String carouselContent;  //内容
	public int carouselState;       //状态   0 未删除   1 已删除
	public int carouselSort;        //排序
	public Timestamp startTime;     //定时开始时间
	public Timestamp endTime;       //定时结束时间
	private String operationName;     //操作人
	private Timestamp operationTime;  //操作时间
	
	
	public int getCarouselID() {
		return carouselID;
	}
	public void setCarouselID(int carouselID) {
		this.carouselID = carouselID;
	}
	public String getCarouselName() {
		return carouselName;
	}
	public void setCarouselName(String carouselName) {
		this.carouselName = carouselName;
	}
	public String getCarouselPosition() {
		return carouselPosition;
	}
	public void setCarouselPosition(String carouselPosition) {
		this.carouselPosition = carouselPosition;
	}
	public String getCarouselPath() {
		return carouselPath;
	}
	public void setCarouselPath(String carouselPath) {
		this.carouselPath = carouselPath;
	}
	public int getCarouselType() {
		return carouselType;
	}
	public void setCarouselType(int carouselType) {
		this.carouselType = carouselType;
	}
	public String getCarouselContent() {
		return carouselContent;
	}
	public void setCarouselContent(String carouselContent) {
		this.carouselContent = carouselContent;
	}
	public int getCarouselState() {
		return carouselState;
	}
	public void setCarouselState(int carouselState) {
		this.carouselState = carouselState;
	}
	public int getCarouselSort() {
		return carouselSort;
	}
	public void setCarouselSort(int carouselSort) {
		this.carouselSort = carouselSort;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
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
	
	
}
