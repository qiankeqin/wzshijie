package com.lagoqu.worldplay.entity;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**
 * 描述：助力留言评论<br>
 * 作者：邢留杰 <br>
 * 修改日期：2015年8月28日上午11:12:06 <br>
 * E-mail:  <br>
 */
@ATable(name = "CrowdfundReply", pkname = "crowdfundReplyID")
public class CrowdfundReply extends BaseEntity{
	private int crowdfundReplyID;//回复id
	private int membersID;//发表评论的人的id
	private int replyMembersID;//被回复人id
	private int crowdfundID;//助力id
	private int crowdfundMarkID;//打赏id
	private String crowdfundReply;//回复内容
	public int getCrowdfundReplyID() {
		return crowdfundReplyID;
	}
	public void setCrowdfundReplyID(int crowdfundReplyID) {
		this.crowdfundReplyID = crowdfundReplyID;
	}
	public int getMembersID() {
		return membersID;
	}
	public void setMembersID(int membersID) {
		this.membersID = membersID;
	}
	public int getReplyMembersID() {
		return replyMembersID;
	}
	public void setReplyMembersID(int replyMembersID) {
		this.replyMembersID = replyMembersID;
	}
	public int getCrowdfundID() {
		return crowdfundID;
	}
	public void setCrowdfundID(int crowdfundID) {
		this.crowdfundID = crowdfundID;
	}
	public int getCrowdfundMarkID() {
		return crowdfundMarkID;
	}
	public void setCrowdfundMarkID(int crowdfundMarkID) {
		this.crowdfundMarkID = crowdfundMarkID;
	}
	public String getCrowdfundReply() {
		return crowdfundReply;
	}
	public void setCrowdfundReply(String crowdfundReply) {
		this.crowdfundReply = crowdfundReply;
	}
}
