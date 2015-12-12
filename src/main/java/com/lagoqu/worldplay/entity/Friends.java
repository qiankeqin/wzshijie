package com.lagoqu.worldplay.entity;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**描述：好友关系<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月8日上午11:28:27 <br>
 * E-mail:  <br> 
 */
@ATable(name = "Friends", pkname = "friendsID")
public class Friends extends BaseEntity{

	private int friendsID;     //好友编号
	private int membersID;     //用户id
	private int friendUserID;  //好友id
	
	public int getFriendsID() {
		return friendsID;
	}
	public void setFriendsID(int friendsID) {
		this.friendsID = friendsID;
	}
	public int getMembersID() {
		return membersID;
	}
	public void setMembersID(int membersID) {
		this.membersID = membersID;
	}
	public int getFriendUserID() {
		return friendUserID;
	}
	public void setFriendUserID(int friendUserID) {
		this.friendUserID = friendUserID;
	}
	
	
}
