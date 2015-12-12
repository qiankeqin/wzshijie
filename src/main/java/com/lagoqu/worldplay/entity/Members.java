package com.lagoqu.worldplay.entity;


import java.sql.Timestamp;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**描述：会员管理<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月5日上午10:28:27 <br>
 * E-mail:  <br> 
 */
@ATable(name = "Members", pkname = "membersID")
public class Members extends BaseEntity{
	private int membersID;           //会员编号
	private String membersNickName;  //会员昵称
	private String membersImage;     //会员头像
	private String membersEmail;     //会员Email
	private String membersPhone;     //会员手机号
	private String membersLoginName; //会员登陆账号
	private String membersPassword;  //登陆密码
	private String membersName;      //会员姓名
	private String membersQQ;        //会员QQ号
	private int membersSex;          //会员性别       0:女    1：男
	private String membersLocation;  //会员所在地
	private String membersSignature; //会员宣言
	private String membersWxID;      //微信绑定ID
	private String membersAndroidWxID;//安卓端三方登陆微信登陆
	private String membersQQID;      //QQ绑定ID
	private String membersWbID;      //微博绑定ID
	private String membersNamePY;    //昵称拼音
	private Timestamp updateTime;     //修改时间
	private int identifyState=0;       //认证状态 0未认证 1待审核 2审核通过 3审核失败
	private int membersState;          //用户状态   0真是用户   1 虚拟用户
	private int membersSource;         //用户来源    
	public int getMembersState() {
		return membersState;
	}
	public void setMembersState(int membersState) {
		this.membersState = membersState;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getMembersNamePY() {
		return membersNamePY;
	}
	public void setMembersNamePY(String membersNamePY) {
		this.membersNamePY = membersNamePY;
	}
	public int getMembersID() {
		return membersID;
	}
	public void setMembersID(int membersID) {
		this.membersID = membersID;
	}
	public String getMembersNickName() {
		return membersNickName;
	}
	public void setMembersNickName(String membersNickName) {
		this.membersNickName = membersNickName;
	}
	public String getMembersImage() {
		return membersImage;
	}
	public void setMembersImage(String membersImage) {
		this.membersImage = membersImage;
	}
	public String getMembersEmail() {
		return membersEmail;
	}
	public void setMembersEmail(String membersEmail) {
		this.membersEmail = membersEmail;
	}
	public String getMembersPhone() {
		return membersPhone;
	}
	public void setMembersPhone(String membersPhone) {
		this.membersPhone = membersPhone;
	}
	public String getMembersLoginName() {
		return membersLoginName;
	}
	public void setMembersLoginName(String membersLoginName) {
		this.membersLoginName = membersLoginName;
	}
	public String getMembersPassword() {
		return membersPassword;
	}
	public void setMembersPassword(String membersPassword) {
		this.membersPassword = membersPassword;
	}
	public String getMembersName() {
		return membersName;
	}
	public void setMembersName(String membersName) {
		this.membersName = membersName;
	}
	public String getMembersQQ() {
		return membersQQ;
	}
	public void setMembersQQ(String membersQQ) {
		this.membersQQ = membersQQ;
	}
	public int getMembersSex() {
		return membersSex;
	}
	public void setMembersSex(int membersSex) {
		this.membersSex = membersSex;
	}
	public String getMembersLocation() {
		return membersLocation;
	}
	public void setMembersLocation(String membersLocation) {
		this.membersLocation = membersLocation;
	}
	public String getMembersSignature() {
		return membersSignature;
	}
	public void setMembersSignature(String membersSignature) {
		this.membersSignature = membersSignature;
	}
	public String getMembersWxID() {
		return membersWxID;
	}
	public void setMembersWxID(String membersWxID) {
		this.membersWxID = membersWxID;
	}
	public String getMembersQQID() {
		return membersQQID;
	}
	public void setMembersQQID(String membersQQID) {
		this.membersQQID = membersQQID;
	}
	public String getMembersWbID() {
		return membersWbID;
	}
	public void setMembersWbID(String membersWbID) {
		this.membersWbID = membersWbID;
	}
	public int getIdentifyState() {
		return identifyState;
	}
	public void setIdentifyState(int identifyState) {
		this.identifyState = identifyState;
	}
	public String getMembersAndroidWxID() {
		return membersAndroidWxID;
	}
	public void setMembersAndroidWxID(String membersAndroidWxID) {
		this.membersAndroidWxID = membersAndroidWxID;
	}
	public int getMembersSource() {
		return membersSource;
	}
	public void setMembersSource(int membersSource) {
		this.membersSource = membersSource;
	}
	
}
