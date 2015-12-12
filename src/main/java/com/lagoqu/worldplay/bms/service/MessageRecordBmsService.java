package com.lagoqu.worldplay.bms.service;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.MessageRecord;
/**描述：短信日志记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年8月11日上午10:12:29 <br>
 * E-mail:  <br> 
 */
@Service
public class MessageRecordBmsService extends BaseDao<MessageRecord>{

	
	
	/**方法名称: saveMessageRecord<br>
	 * 描述：保存短信日志记录
	 * 作者: 王小欢
	 * 修改日期：2015年8月11日上午10:18:20
	 * @param messageRecordCode
	 * @param messageRecordContent
	 * @param messageRecordType
	 * @param messageRecordRrid
	 * @return
	 */
	public int saveMessageRecord(String messageRecordCode,String messageRecordContent,int messageRecordType,String messageRecordRrid){
		MessageRecord messageRecord=new MessageRecord();
		messageRecord.setMessageRecordCode(messageRecordCode);
		messageRecord.setMessageRecordContent(messageRecordContent);
		messageRecord.setMessageRecordType(messageRecordType);
		messageRecord.setMessageRecordRrid(messageRecordRrid);
		int messageRecordID=insertBackID(messageRecord);
		return messageRecordID;
	}
}
