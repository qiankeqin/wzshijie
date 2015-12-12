package com.lagoqu.worldplay.entity;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;
/**描述：举报记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月29日上午11:08:11 <br>
 * E-mail:  <br> 
 */
@ATable(name = "ReportRecord", pkname = "reportRecordID")
public class ReportRecord extends BaseEntity{
	
	public int reportRecordID;     //ID
	public int reportRecordMumberID;  //举报人ID
	public String reportRecordPhone;  //举报人手机号
	public String reportRecordName;   //举报人
	public int crowdfundID;     //发布内容ID
	public int membersID;     //发布人ID
	public String membersPhone;   //发布人手机号
	public String reportContent;   //举报内容
	public String reportImage;    //举报图片
	public int getReportRecordID() {
		return reportRecordID;
	}
	public void setReportRecordID(int reportRecordID) {
		this.reportRecordID = reportRecordID;
	}
	public int getReportRecordMumberID() {
		return reportRecordMumberID;
	}
	public void setReportRecordMumberID(int reportRecordMumberID) {
		this.reportRecordMumberID = reportRecordMumberID;
	}
	public String getReportRecordPhone() {
		return reportRecordPhone;
	}
	public void setReportRecordPhone(String reportRecordPhone) {
		this.reportRecordPhone = reportRecordPhone;
	}
	public String getReportRecordName() {
		return reportRecordName;
	}
	public void setReportRecordName(String reportRecordName) {
		this.reportRecordName = reportRecordName;
	}
	public int getCrowdfundID() {
		return crowdfundID;
	}
	public void setCrowdfundID(int crowdfundID) {
		this.crowdfundID = crowdfundID;
	}
	public int getMembersID() {
		return membersID;
	}
	public void setMembersID(int membersID) {
		this.membersID = membersID;
	}
	public String getMembersPhone() {
		return membersPhone;
	}
	public void setMembersPhone(String membersPhone) {
		this.membersPhone = membersPhone;
	}
	public String getReportContent() {
		return reportContent;
	}
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	public String getReportImage() {
		return reportImage;
	}
	public void setReportImage(String reportImage) {
		this.reportImage = reportImage;
	}
	
	
	

}
