package com.lagoqu.worldplay.entity;

import java.sql.Timestamp;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;



/**描述：提现记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年7月6日下午3:59:17 <br>
 * E-mail:  <br> 
 */
@ATable(name = "WithdrawDetails", pkname = "wDetailsID")
public class WithdrawDetails extends BaseEntity{

	
	private int wDetailsID;           //提现编号
	private int membersID;            //会员编号
	private int wDetailsAccount;      //提现金额
	private String wDetailsUserName;  //提现账号
	private int wDetailsType;         //提现类型   0、支付宝  1微信  2银行卡
	private String wDetailsName;      //提现姓名
	private int wDetailsStatus;       //提现状态   1提现中  2已到账
	private Timestamp updateTime;     //提现时间
	private String operationName;     //操作人
	
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public int getWDetailsID() {
		return wDetailsID;
	}
	public void setWDetailsID(int wDetailsID) {
		this.wDetailsID = wDetailsID;
	}
	public int getMembersID() {
		return membersID;
	}
	public void setMembersID(int membersID) {
		this.membersID = membersID;
	}
	public int getWDetailsAccount() {
		return wDetailsAccount;
	}
	public void setWDetailsAccount(int wDetailsAccount) {
		this.wDetailsAccount = wDetailsAccount;
	}
	public String getWDetailsUserName() {
		return wDetailsUserName;
	}
	public void setWDetailsUserName(String wDetailsUserName) {
		this.wDetailsUserName = wDetailsUserName;
	}
	public int getWDetailsType() {
		return wDetailsType;
	}
	public void setWDetailsType(int wDetailsType) {
		this.wDetailsType = wDetailsType;
	}
	public String getWDetailsName() {
		return wDetailsName;
	}
	public void setWDetailsName(String wDetailsName) {
		this.wDetailsName = wDetailsName;
	}
	public int getWDetailsStatus() {
		return wDetailsStatus;
	}
	public void setWDetailsStatus(int wDetailsStatus) {
		this.wDetailsStatus = wDetailsStatus;
	}
	
	
	
}
