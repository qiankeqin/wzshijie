package com.lagoqu.worldplay.entity;

import java.sql.Timestamp;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**描述：旅游助力<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月5日上午10:28:40 <br>
 * E-mail:  <br> 
 */
@ATable(name = "Crowdfund", pkname = "crowdfundID")
public class Crowdfund extends BaseEntity{

	private int crowdfundID;          //助力编号
	private int membersID;            //会员编号
	private String crowdfundTitle;    //助力标题
	private int crowdfundType;        //助理分类     0 ：心愿 1： 足迹
	private String crowdfundDesc;     //助力描述
	private String crowdfundVoice;    //语音文件
	private int crowdfundVoiceSec;    //语音秒数
	private String crowdfundPath;     //助力封面图
	private String crowdfundPic;      //助力图片
	private int crowdfundDays;        //有效天数
	private Timestamp endTime;        //截至时间
	private int crowdfundIsDel;       //是否删除      0:未删除  1已删除   2彻底删除
	private int crowdfundaccTimes;    //访问次数
	private int crowdfundTimes;       //助力次数
	private int crowdfundAccount;     //助力金额
	private Timestamp updateTime;     //修改时间
	private Timestamp deleteTime;     //删除时间
	private String crowdfundRepay;    //回报描述
	private int crowdfundGap;         //资金缺口
	private String operationName;     //操作人
	private Timestamp operationTime;  //操作时间
	private int crowdfundState=0;     //助理状态     0正常发布      1 后台发布
	private int crowdfundSort;        //助力排序
	private String crowdfundLabel;    //热门标签
	private String crowdfundcentont;  //总的描述      新加的（修改改了）
	private int crowdfundRdState;     //推荐状态    0未推荐    1已推荐
	
	public String getCrowdfundcentont() {
		return crowdfundcentont;
	}
	public void setCrowdfundcentont(String crowdfundcentont) {
		this.crowdfundcentont = crowdfundcentont;
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
	public String getCrowdfundTitle() {
		return crowdfundTitle;
	}
	public void setCrowdfundTitle(String crowdfundTitle) {
		this.crowdfundTitle = crowdfundTitle;
	}
	public int getCrowdfundType() {
		return crowdfundType;
	}
	public void setCrowdfundType(int crowdfundType) {
		this.crowdfundType = crowdfundType;
	}
	public String getCrowdfundDesc() {
		return crowdfundDesc;
	}
	public void setCrowdfundDesc(String crowdfundDesc) {
		this.crowdfundDesc = crowdfundDesc;
	}
	public String getCrowdfundVoice() {
		return crowdfundVoice;
	}
	public void setCrowdfundVoice(String crowdfundVoice) {
		this.crowdfundVoice = crowdfundVoice;
	}
	public int getCrowdfundVoiceSec() {
		return crowdfundVoiceSec;
	}
	public void setCrowdfundVoiceSec(int crowdfundVoiceSec) {
		this.crowdfundVoiceSec = crowdfundVoiceSec;
	}
	public String getCrowdfundPath() {
		return crowdfundPath;
	}
	public void setCrowdfundPath(String crowdfundPath) {
		this.crowdfundPath = crowdfundPath;
	}
	public String getCrowdfundPic() {
		return crowdfundPic;
	}
	public void setCrowdfundPic(String crowdfundPic) {
		this.crowdfundPic = crowdfundPic;
	}
	public int getCrowdfundDays() {
		return crowdfundDays;
	}
	public void setCrowdfundDays(int crowdfundDays) {
		this.crowdfundDays = crowdfundDays;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public int getCrowdfundIsDel() {
		return crowdfundIsDel;
	}
	public void setCrowdfundIsDel(int crowdfundIsDel) {
		this.crowdfundIsDel = crowdfundIsDel;
	}
	public int getCrowdfundaccTimes() {
		return crowdfundaccTimes;
	}
	public void setCrowdfundaccTimes(int crowdfundaccTimes) {
		this.crowdfundaccTimes = crowdfundaccTimes;
	}
	public int getCrowdfundTimes() {
		return crowdfundTimes;
	}
	public void setCrowdfundTimes(int crowdfundTimes) {
		this.crowdfundTimes = crowdfundTimes;
	}
	public int getCrowdfundAccount() {
		return crowdfundAccount;
	}
	public void setCrowdfundAccount(int crowdfundAccount) {
		this.crowdfundAccount = crowdfundAccount;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Timestamp getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}
	public String getCrowdfundRepay() {
		return crowdfundRepay;
	}
	public void setCrowdfundRepay(String crowdfundRepay) {
		this.crowdfundRepay = crowdfundRepay;
	}
	public int getCrowdfundGap() {
		return crowdfundGap;
	}
	public void setCrowdfundGap(int crowdfundGap) {
		this.crowdfundGap = crowdfundGap;
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
	public int getCrowdfundState() {
		return crowdfundState;
	}
	public void setCrowdfundState(int crowdfundState) {
		this.crowdfundState = crowdfundState;
	}
	public int getCrowdfundSort() {
		return crowdfundSort;
	}
	public void setCrowdfundSort(int crowdfundSort) {
		this.crowdfundSort = crowdfundSort;
	}
	public String getCrowdfundLabel() {
		return crowdfundLabel;
	}
	public void setCrowdfundLabel(String crowdfundLabel) {
		this.crowdfundLabel = crowdfundLabel;
	}
	public int getCrowdfundRdState() {
		return crowdfundRdState;
	}
	public void setCrowdfundRdState(int crowdfundRdState) {
		this.crowdfundRdState = crowdfundRdState;
	}

	
	
}
