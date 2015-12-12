package com.lagoqu.worldplay.entity;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**描述：助力记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月5日上午10:46:22 <br>
 * E-mail:  <br> 
 */
@ATable(name = "CrowdfundMark", pkname = "crowdfundmarkID")
public class CrowdfundMark extends BaseEntity{

	private int crowdfundmarkID;           //助力记录编号
	private int membersID;                 //会员编号
	private int orderID;                   //订单号
	private int crowdfundID;               //助力编号
	private String crowdfundmarkMessage;   //助力留言
	private int crowdfundmarkAccount;   //赞助金额
	public int getCrowdfundmarkID() {
		return crowdfundmarkID;
	}
	public void setCrowdfundmarkID(int crowdfundmarkID) {
		this.crowdfundmarkID = crowdfundmarkID;
	}
	public int getMembersID() {
		return membersID;
	}
	public void setMembersID(int membersID) {
		this.membersID = membersID;
	}
	public int getCrowdfundID() {
		return crowdfundID;
	}
	public void setCrowdfundID(int crowdfundID) {
		this.crowdfundID = crowdfundID;
	}
	public String getCrowdfundmarkMessage() {
		return crowdfundmarkMessage;
	}
	public void setCrowdfundmarkMessage(String crowdfundmarkMessage) {
		this.crowdfundmarkMessage = crowdfundmarkMessage;
	}
	
	public int getCrowdfundmarkAccount() {
		return crowdfundmarkAccount;
	}
	public void setCrowdfundmarkAccount(int crowdfundmarkAccount) {
		this.crowdfundmarkAccount = crowdfundmarkAccount;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	
}
