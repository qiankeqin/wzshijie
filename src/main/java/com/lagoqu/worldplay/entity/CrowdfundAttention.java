package com.lagoqu.worldplay.entity;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**描述：助理关注<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月5日上午10:28:16 <br>
 * E-mail:  <br> 
 */
@ATable(name = "CrowdfundAttention", pkname = "caID")
public class CrowdfundAttention extends BaseEntity{
	private int caID;            //关注编号
	private int crowdfundID;     //助力编号
	private int membersID;       //会员编号
	public int getCaID() {
		return caID;
	}
	public void setCaID(int caID) {
		this.caID = caID;
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
	

}
