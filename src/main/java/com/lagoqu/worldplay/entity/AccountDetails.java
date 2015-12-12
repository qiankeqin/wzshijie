package com.lagoqu.worldplay.entity;


import java.sql.Timestamp;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**描述：账户明细<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年7月6日下午3:51:48 <br>
 * E-mail:  <br> 
 */
@ATable(name = "AccountDetails", pkname = "detailsID")
public class AccountDetails extends BaseEntity{

	private int detailsID;         //明细编号
	private int membersID;         //会员编号
	private int detailsAccount;    //明细金额
	private String detailsType;    //明细类型  1、已到账 （2、提现中  3已提现）
	private int detailsSource;     //账户明细来源  0 、心愿 1、足迹
	private String detailsDesc;    //明细描述
	private int detailsMembersId;  //转账用户
	private Timestamp detailsTime;  //金额可用时间
	public int getDetailsID() {
		return detailsID;
	}
	public void setDetailsID(int detailsID) {
		this.detailsID = detailsID;
	}
	public int getMembersID() {
		return membersID;
	}
	public void setMembersID(int membersID) {
		this.membersID = membersID;
	}
	public int getDetailsAccount() {
		return detailsAccount;
	}
	public void setDetailsAccount(int detailsAccount) {
		this.detailsAccount = detailsAccount;
	}
	public String getDetailsType() {
		return detailsType;
	}
	public void setDetailsType(String detailsType) {
		this.detailsType = detailsType;
	}
	
	public int getDetailsSource() {
		return detailsSource;
	}
	public void setDetailsSource(int detailsSource) {
		this.detailsSource = detailsSource;
	}
	public String getDetailsDesc() {
		return detailsDesc;
	}
	public void setDetailsDesc(String detailsDesc) {
		this.detailsDesc = detailsDesc;
	}
	public int getDetailsMembersId() {
		return detailsMembersId;
	}
	public void setDetailsMembersId(int detailsMembersId) {
		this.detailsMembersId = detailsMembersId;
	}
	public Timestamp getDetailsTime() {
		return detailsTime;
	}
	public void setDetailsTime(Timestamp detailsTime) {
		this.detailsTime = detailsTime;
	}
	
	
	
	

}
