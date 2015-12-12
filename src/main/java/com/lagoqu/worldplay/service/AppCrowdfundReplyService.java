package com.lagoqu.worldplay.service;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.CrowdfundReply;

@Service
public class AppCrowdfundReplyService extends BaseDao<CrowdfundReply>{

	
	
	/**方法名称: CrowdfundReleaseComment<br>
	 * 描述：心愿或足迹评论
	 * 作者: 王小欢
	 * 修改日期：2015年11月3日上午10:35:54
	 * @param crowdfundReply
	 * @return
	 */
	public JSONObject CrowdfundReleaseComment(CrowdfundReply crowdfundReply){
		JSONObject backJsonObject=new JSONObject();
		int crowdfundReplyID = insertBackID(crowdfundReply);
		if(crowdfundReplyID!=-1){
			backJsonObject.put("data",crowdfundReplyID);
			backJsonObject.put("state", true);
			return backJsonObject;
		}else {
			backJsonObject.put("message","评论失败，请重试");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
	
	
	/**方法名称: CrowdfundReleaseCommentDel<br>
	 * 描述：打赏评论删除
	 * 作者: 王小欢
	 * 修改日期：2015年11月3日上午11:06:44
	 * @param crowdfundReplyID
	 * @return
	 */
	public JSONObject CrowdfundReleaseCommentDel(int crowdfundReplyID){
		JSONObject backJsonObject=new JSONObject();
		boolean state=super.delete(crowdfundReplyID);
		if(state==true){
			backJsonObject.put("data","评论删除成功");
			backJsonObject.put("state", true);
			return backJsonObject;
		}else {
			backJsonObject.put("message","评论删除失败");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
}
