package com.lagoqu.worldplay.entity;

import java.sql.Timestamp;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**描述：足迹记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月29日上午10:42:36 <br>
 * E-mail:  <br> 
 */
@ATable(name = "TrackRecord", pkname = "trackRecordID")
public class TrackRecord extends BaseEntity{

	public int trackRecordID;           //足迹记录ID
	public int crowdfundID;             //助力ID
	public String trackRecordTitle;     //足迹图片标题
	public String trackRecordImage;     //足迹图片
	public String trackRecordLocation;  //足迹图片位置
	public Timestamp trackRecordCreateTime;   //足迹图片时间
	public int getTrackRecordID() {
		return trackRecordID;
	}
	public void setTrackRecordID(int trackRecordID) {
		this.trackRecordID = trackRecordID;
	}
	public int getCrowdfundID() {
		return crowdfundID;
	}
	public void setCrowdfundID(int crowdfundID) {
		this.crowdfundID = crowdfundID;
	}
	public String getTrackRecordTitle() {
		return trackRecordTitle;
	}
	public void setTrackRecordTitle(String trackRecordTitle) {
		this.trackRecordTitle = trackRecordTitle;
	}
	public String getTrackRecordImage() {
		return trackRecordImage;
	}
	public void setTrackRecordImage(String trackRecordImage) {
		this.trackRecordImage = trackRecordImage;
	}
	public String getTrackRecordLocation() {
		return trackRecordLocation;
	}
	public void setTrackRecordLocation(String trackRecordLocation) {
		this.trackRecordLocation = trackRecordLocation;
	}
	public Timestamp getTrackRecordCreateTime() {
		return trackRecordCreateTime;
	}
	public void setTrackRecordCreateTime(Timestamp trackRecordCreateTime) {
		this.trackRecordCreateTime = trackRecordCreateTime;
	}
	
	
}
