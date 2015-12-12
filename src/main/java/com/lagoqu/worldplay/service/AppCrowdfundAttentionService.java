package com.lagoqu.worldplay.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.CrowdfundAttention;
/**描述：心愿或足迹关注<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月2日下午2:48:03 <br>
 * E-mail:  <br> 
 */
@Service
public class AppCrowdfundAttentionService extends BaseDao<CrowdfundAttention>{

	
	/**方法名称: CrowdfundReleaseAttention<br>
	 * 描述：心愿或足迹关注
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日下午3:08:39
	 * @param crowdfundID
	 * @param membersID
	 * @return
	 */
	public JSONObject CrowdfundReleaseAttention(int crowdfundID,int membersID){
		JSONObject backJsonObject=new JSONObject();
		String sql="select ca.caID from CrowdfundAttention  ca where ca.crowdfundID=? and  ca.membersID=?";
		JSONObject jso = super.findJson(sql, null,crowdfundID,membersID);
		if(jso!=null){
			int caID = jso.getInt("caID");
			boolean bl = super.delete(caID);
			if(bl==true){
				backJsonObject.put("data","取消关注成功");
				backJsonObject.put("state", true);
				return backJsonObject;
			}else {
				backJsonObject.put("message","取消关注失败");
				backJsonObject.put("state", false);
				return backJsonObject; 
			}
		}else{
			CrowdfundAttention crowdfundAttention=new CrowdfundAttention();
			crowdfundAttention.setMembersID(membersID);
			crowdfundAttention.setCrowdfundID(crowdfundID);
			int caID=insertBackID(crowdfundAttention);
			if(caID!=-1){
				backJsonObject.put("data","关注成功");
				backJsonObject.put("state", true);
				return backJsonObject;
			}else{
				backJsonObject.put("message","关注失败");
				backJsonObject.put("state", false);
				return backJsonObject; 
			}
		}
	}
	
	
	
	/**方法名称: CrowdfundAttentionList<br>
	 * 描述：心愿或足迹用户关注详情
	 * 作者: 王小欢
	 * 修改日期：2015年11月4日上午10:27:56
	 * @param crowdfundID
	 * @return
	 */
	public JSONObject CrowdfundAttentionList(int crowdfundID){
		JSONObject backJsonObject=new JSONObject();
		String sql="select ms.membersID,ms.membersImage,ms.membersNickName,cfa.createTime from CrowdfundAttention cfa inner join Members ms on cfa.membersID =ms.membersID where  cfa.crowdfundID =? order by cfa.createTime desc";
		JSONArray jsonArray  = findJsonArray(sql,null,crowdfundID);
		if(jsonArray!=null){
			backJsonObject.put("data",jsonArray);
			backJsonObject.put("state", true);
			return backJsonObject;
		}else {
			backJsonObject.put("message","获取关注列表失败");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
}
