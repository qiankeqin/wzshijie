package com.lagoqu.worldplay.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.CrowdfundMark;

/**描述：CrowdfundMark<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月5日上午10:46:59 <br>
 * E-mail:  <br> 
 */
@Service
public class CrowdfundMarkService extends BaseDao<CrowdfundMark>{

	
	
	/**方法名称: saveCrowdfundMark<br>
	 * 描述：保存用户助力记录
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日下午4:53:07
	 * @param crowdfundMark
	 * @return
	 */
	public int saveCrowdfundMark(JSONObject jObject){
		CrowdfundMark crowdfundMark=(CrowdfundMark) jObject.toBean(jObject, CrowdfundMark.class);
		int crowdfundmarkID=insertBackID(crowdfundMark);
		return crowdfundmarkID;
	}
	/**
	 * 根据用户id查询我参与的助力
	 * @param page
	 * @param size
	 * @param memberID
	 * @return
	 */
	public Pagination<JSONArray> findCrowdfundMarkByMembersID(int page,int size,int memberID){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select ms.membersNickName,ms.membersImage,ms.membersID,cfm.crowdfundmarkID,cfm.membersID,cfm.crowdfundID,cfm.crowdfundmarkMessage,cfm.crowdfundmarkAccount,cfm.crowdfundmarkAccount,cfm.createTime,cf.crowdfundTitle,cf.crowdfundDesc,cf.crowdfundVoice,cf.crowdfundPath,cf.crowdfundPic,cf.crowdfundDays,cf.crowdfundIsDel,cf.crowdfundaccTimes,cf.crowdfundTimes,cf.crowdfundAccount,cf.endTime,cf.createTime as cf_createTime,cf.updateTime,cf.deleteTime from CrowdfundMark cfm left join Crowdfund cf on cfm.crowdfundID =cf.crowdfundID left join  Members ms on cf.membersID = ms.membersID where cf.crowdfundType=0 and cf.crowdfundRepay!='' and cf.crowdfundIsDel=0 and cfm.membersID=? order by cfm.createTime desc";
		String countSql="select count(cfm.crowdfundmarkID) from CrowdfundMark cfm left join Crowdfund cf on cfm.crowdfundID =cf.crowdfundID  where cf.crowdfundType=0 and cf.crowdfundRepay!='' and cf.crowdfundIsDel=0 and cfm.membersID=?";
		super.findListJsonByPages(pagination,sql,countSql,memberID);
		return pagination;
	}
	/**
	 * 方法名称: findCrowdfundMarkListByOrderId<br>
	 * 描述：查询助力记录表中是否包含该订单号的记录
	 * 作者: 邢留杰
	 * 修改日期：2015年7月24日上午9:57:11
	 * @param orderId
	 * @return
	 */
	public JSONArray findCrowdfundMarkListByOrderId(int orderId){
		String sql = "select crowdfundmarkID from CrowdfundMark where orderID =?";
		JSONArray jsonArray = super.findJsonArray(sql, null,orderId);
		return jsonArray;
	}
}
