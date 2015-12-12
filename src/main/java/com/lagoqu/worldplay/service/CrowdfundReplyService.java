package com.lagoqu.worldplay.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.CrowdfundReply;

@Service
public class CrowdfundReplyService extends BaseDao<CrowdfundReply>{
	/**
	 * 方法名称: addCrowfundReply<br>
	 * 描述：新增一条评论
	 * 作者: 邢留杰
	 * 修改日期：2015年8月28日下午4:25:52
	 * @param crowdfundReply
	 * @return
	 */
	public int addCrowfundReply(CrowdfundReply crowdfundReply){
		int backId = insertBackID(crowdfundReply);
		return backId;
	}
	/**
	 * 方法名称: findByMarkId<br>
	 * 描述：根据打赏id查找评论记录
	 * 作者: 邢留杰
	 * 修改日期：2015年8月28日下午4:56:53
	 * @param crowdfundMarkID
	 * @return
	 */
	public JSONArray findByMarkId(int crowdfundMarkID){
		String sql = "select cr.crowdfundReplyID,cr.membersID,ms1.membersNickName,cr.replyMembersID,ms2.membersNickName as replyMembersNickName,cr.crowdfundID,cr.crowdfundMarkID,cr.crowdfundReply,cr.createTime from CrowdfundReply cr INNER JOIN Members ms1 on ms1.membersID=cr.membersID INNER JOIN Members ms2 on ms2.membersID=cr.replyMembersID  where crowdfundMarkID=?";
		JSONArray listReply =super.findJsonArray(sql, null, crowdfundMarkID);
		return listReply;
	}
	/**
	 * 方法名称: findByMarkId<br>
	 * 描述：根据助力Id查找评论记录
	 * 作者: 邢留杰
	 * 修改日期：2015年8月28日下午4:56:53
	 * @param crowdfundMarkID
	 * @return
	 */
	public List<CrowdfundReply> findByCrowdfundId(int crowdfundID){
		String sql = "select crowdfundReplyID,membersID,replyMembersID,crowdfundID,crowdfundMarkID,crowdfundReply,createTime from CrowdfundReply where crowdfundID=?";
		List <CrowdfundReply> listReply = super.get(sql, crowdfundID);
		return listReply;
	}
}
