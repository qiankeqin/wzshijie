package com.lagoqu.worldplay.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.CrowdfundAttention;
/**描述：助理关注<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月5日上午10:27:48 <br>
 * E-mail:  <br> 
 */
@Service
public class CrowdfundAttentionService extends BaseDao<CrowdfundAttention>{

	
	
	/**方法名称: insertAttention<br>
	 * 描述：添加的助力信息
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日上午11:12:30
	 * @param membersID
	 * @param crowdfundID
	 * @return
	 */
	public int insertAttention(int membersID,int crowdfundID){
		CrowdfundAttention crowdfundAttention=new CrowdfundAttention();
		crowdfundAttention.setMembersID(membersID);
		crowdfundAttention.setCrowdfundID(crowdfundID);
		int caID=insertBackID(crowdfundAttention);
		return caID;
	}
	/**
	 * 方法名称: ifAttention<br>
	 * 描述：判断登录用户是否关注过
	 * 作者: 邢留杰
	 * 修改日期：2015年6月16日下午7:20:28
	 * @param membersID
	 * @param crowdfundID
	 * @return
	 */
	public JSONObject ifAttention(int membersID,int crowdfundID){
		String sql="select ca.caID from CrowdfundAttention  ca where ca.crowdfundID=? and  ca.membersID=?";
		JSONObject jso = super.findJson(sql, null,crowdfundID,membersID);
		return jso;
	}
	
	
	/**方法名称: listCrowdfundAttention<br>
	 * 描述：用户个人关注助力列表
	 * 作者: 王小欢
	 * 修改日期：2015年6月8日上午11:59:13
	 * @param page
	 * @param size
	 * @param memberID
	 * @return
	 */
	public Pagination<JSONArray> listCrowdfundAttention(int page,int size,int memberID){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select cfa.caID,ms.membersID,ms.membersNickName,ms.membersImage,cf.crowdfundID,cf.crowdfundTitle,cf.createTime,cf.crowdfundDesc,cf.crowdfundVoice,cf.crowdfundPath,cf.crowdfundPic,cf.crowdfundDays,cf.endTime,cf.crowdfundIsDel,cf.crowdfundaccTimes,cf.crowdfundTimes,cf.crowdfundAccount,cf.updateTime,cf.deleteTime,cfa.createTime as cfa_createTime ,case when cf.endTime>NOW() then '0' ELSE '1' END as isdateline from CrowdfundAttention as cfa inner join Crowdfund as cf on cfa.crowdfundID=cf.crowdfundID inner join Members as ms on cf.membersID=ms.membersID where cf.crowdfundType=0 and cf.crowdfundRepay!='' and cf.crowdfundIsDel=0 and cfa.membersID=? order by cfa.createTime desc";
		String countSql="select count(cfa.caID) from CrowdfundAttention as cfa inner join Crowdfund as cf on cfa.crowdfundID=cf.crowdfundID where cf.crowdfundType=0 and cf.crowdfundRepay!='' and cf.crowdfundIsDel=0 and cfa.membersID=?";
		findListJsonByPages(pagination,sql,countSql,memberID);
		return pagination;
	}
}
