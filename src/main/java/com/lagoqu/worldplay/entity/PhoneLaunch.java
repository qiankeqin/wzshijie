package com.lagoqu.worldplay.entity;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**描述：手机启动记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月8日下午3:50:07 <br>
 * E-mail:  <br> 
 */
@ATable(name = "PhoneLaunch", pkname = "plID")
public class PhoneLaunch extends BaseEntity{

	private int plID;               //启动记录
	private String plImei;          //手机iemi
	private String plType;          //手机类型
	private String plSysVersion;    //手机系统版本
	private String plResolution;    //手机分辨率
	public int getPlID() {
		return plID;
	}
	public void setPlID(int plID) {
		this.plID = plID;
	}
	public String getPlImei() {
		return plImei;
	}
	public void setPlImei(String plImei) {
		this.plImei = plImei;
	}
	public String getPlType() {
		return plType;
	}
	public void setPlType(String plType) {
		this.plType = plType;
	}
	public String getPlSysVersion() {
		return plSysVersion;
	}
	public void setPlSysVersion(String plSysVersion) {
		this.plSysVersion = plSysVersion;
	}
	public String getPlResolution() {
		return plResolution;
	}
	public void setPlResolution(String plResolution) {
		this.plResolution = plResolution;
	}
	
}
