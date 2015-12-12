package com.lagoqu.worldplay.entity;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;
/**描述：短信发送记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年8月11日上午10:10:38 <br>
 * E-mail:  <br> 
 */
@ATable(name = "MessageRecord", pkname = "MessageRecordID")
public class MessageRecord extends BaseEntity{

	public int messageRecordID;            //ID
	public String messageRecordContent;    //发送短信内容
	public String messageRecordCode;          //接收手机号
	public String messageRecordRrid;       //返回内容
	public int messageRecordType;          //发送类型       1：云片短信      2：三基时代
	public int getMessageRecordID() {
		return messageRecordID;
	}
	public void setMessageRecordID(int messageRecordID) {
		this.messageRecordID = messageRecordID;
	}
	public String getMessageRecordContent() {
		return messageRecordContent;
	}
	public void setMessageRecordContent(String messageRecordContent) {
		this.messageRecordContent = messageRecordContent;
	}
	public String getMessageRecordCode() {
		return messageRecordCode;
	}
	public void setMessageRecordCode(String messageRecordCode) {
		this.messageRecordCode = messageRecordCode;
	}
	public String getMessageRecordRrid() {
		return messageRecordRrid;
	}
	public void setMessageRecordRrid(String messageRecordRrid) {
		this.messageRecordRrid = messageRecordRrid;
	}
	public int getMessageRecordType() {
		return messageRecordType;
	}
	public void setMessageRecordType(int messageRecordType) {
		this.messageRecordType = messageRecordType;
	}
	
	
}
