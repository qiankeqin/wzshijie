package com.lagoqu.worldplay.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Crowdfund;
import com.lagoqu.worldplay.entity.CrowdfundReply;
import com.lagoqu.worldplay.util.DateUtil;
/**描述：旅游助力<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月4日下午6:04:23 <br>
 * E-mail:  <br> 
 */
@Service
public class CrowdfundService extends BaseDao<Crowdfund>{

	
	@Resource
	CrowdfundMarkService crowdfundMarkService;
	@Resource
	CrowdfundReplyService crowdfundReplyService;
	/**方法名称: saveCrowdfund<br>
	 * 描述：用户发布助力,助理保存
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日上午11:43:40
	 * @param crowdfund
	 * @return
	 * @throws ParseException 
	 */
	public int saveCrowdfund(JSONObject jObject) throws ParseException{
		Crowdfund crowdfund=(Crowdfund) jObject.toBean(jObject, Crowdfund.class);
		//时间转化start
		Date d = new Date();
		DateUtil du = new DateUtil();
		String s = du.addDay(du.convertDateToString(d),crowdfund.getCrowdfundDays());
		s= s+" 00:00:00";
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		ts = Timestamp.valueOf(s);
		//时间转化end
		crowdfund.setCrowdfundType(0);
		crowdfund.setEndTime(ts);
		crowdfund.setCrowdfundcentont(crowdfund.getCrowdfundTitle()+crowdfund.getCrowdfundDesc()+crowdfund.getCrowdfundRepay());
		int crowdfundID=insertBackID(crowdfund);
		return crowdfundID;
	}
	
	
	/**方法名称: deleteCrowdfund<br>
	 * 描述：：删除助力
	 * 用于删除助力信息，如果已经有人助力过，不能删除
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日上午11:47:05
	 * @param crowdfundID
	 * @throws SQLException 
	 */
	public String  deleteCrowdfund(int crowdfundID) throws SQLException{
		JSONObject crowdfundMarks= crowdfundMarkService.findJson("select crowdfundID from CrowdfundMark where crowdfundID=?", " id desc",crowdfundID);
		if(crowdfundMarks!=null){
			return "false";
		}else{
			String sql = "update Crowdfund set crowdfundIsDel = 1 where crowdfundID=?";
			super.execSql(sql,crowdfundID);
			return "true";
		}
	}
	
	
	/**方法名称: listCrowdfund<br>
	 * 描述：用于获取助力列表
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日下午2:22:34
	 * @param page
	 * @param size
	 * @param memberID
	 * @return
	 */
	public Pagination<JSONArray> listCrowdfund(int page,int size,int memberID,String updateTime){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql="select cf.crowdfundID,cf.crowdfundAccount,cf.crowdfundTitle,cf.crowdfundDesc,cf.crowdfundVoice,cf.crowdfundVoiceSec,cf.crowdfundPath,cf.createTime,cf.crowdfundTimes,cf.crowdfundaccTimes,cf.endTime,case when cf.endTime>NOW() then '0' ELSE '1' END as isdateline,ms.membersID,ms.membersNickName,ms.membersImage,count(cfa.crowdfundID) as cfacount,cf.crowdfundState from Crowdfund cf inner join Members ms on cf.membersID =ms.membersID left join CrowdfundAttention cfa on cfa.crowdfundID=cf.crowdfundID where cf.crowdfundType=0 and cf.crowdfundRepay!='' and cf.crowdfundIsDel=0 ";
		String countSql="select count(cf.crowdfundID) from Crowdfund cf where cf.crowdfundType=0 and cf.crowdfundRepay!='' and cf.crowdfundIsDel=0 ";
		if(memberID!=0){
		    sql+=" and ms.membersID="+memberID;
		    countSql+=" and cf.membersID="+memberID;
		}else{
			 sql+=" and cf.endTime>'"+ts+"'";
			 countSql+="  and cf.endTime>'"+ts+"'";
		}
		if(updateTime!=null){
			Timestamp tp = new Timestamp(System.currentTimeMillis());
			tp = Timestamp.valueOf(updateTime);
			sql+=" and cf.createTime>'"+tp+"'";
			countSql+="  and cf.createTime>'"+tp+"'";
		}
		sql+=" and cf.createTime<'"+ts+"'";
		countSql+="  and cf.createTime<'"+ts+"'";
		sql+= " group by cf.crowdfundID order by cf.crowdfundSort desc,cf.createTime desc ";
		System.out.println(sql);
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
		
	}
	/**
	 * 方法名称: updateCrowdfundById<br>
	 * 描述：更新浏览该条助力的次数
	 * 作者: 邢留杰
	 * 修改日期：2015年6月16日下午1:36:18
	 * @param crowdfundID
	 * @throws SQLException
	 */
	public void updateCrowdfundById(int crowdfundID) throws SQLException{
		String sql = "update Crowdfund set crowdfundaccTimes=crowdfundaccTimes+1 where crowdfundID=?";
		super.execSql(sql,crowdfundID);
	}
	/**
	 * 方法名称: updateCrowdfundTimes<br>
	 * 描述：跟新次数和金额
	 * 作者: 邢留杰
	 * 修改日期：2015年6月23日下午2:03:49
	 * @param crowdfundID
	 * @param crowdfundAccount
	 * @throws SQLException
	 */
	public void updateCrowdfundTimes(int crowdfundID,int crowdfundAccount) throws SQLException{
		String sql = "update Crowdfund set crowdfundAccount=crowdfundAccount+?,crowdfundTimes=crowdfundTimes+1 where crowdfundID=?";
		super.execSql(sql,crowdfundAccount,crowdfundID);
	}
	/**
	 * 方法名称: getCrowdfundById<br>
	 * 描述：根据crowdfundID获取助力详情
	 * 作者: 邢留杰
	 * 修改日期：2015年6月15日下午3:37:56
	 * @param crowdfundID
	 * @return
	 */
	public JSONArray getCrowdfundById(int crowdfundID){
		String sql="select cf.createTime,cf.crowdfundAccount,cf.crowdfundDays,cf.crowdfundID,cf.crowdfundTitle,cf.crowdfundDesc,cf.crowdfundVoice,cf.crowdfundVoiceSec,cf.crowdfundPath,cf.crowdfundPic,cf.crowdfundTimes,cf.crowdfundaccTimes,cf.endTime,cf.crowdfundRepay,cf.crowdfundGap,ms.membersID,ms.membersNickName,ms.membersImage from Crowdfund cf inner join Members ms on cf.membersID =ms.membersID where cf.crowdfundIsDel=0 and cf.crowdfundID =?";
		JSONArray jsonArray = new JSONArray();
		jsonArray = findJsonArray(sql,null,crowdfundID);
		return jsonArray;
		
	}
	/**
	 * 方法名称: getCrowdfundById<br>
	 * 描述：根据crowdfundID获取助力详情
	 * 作者: 邢留杰
	 * 修改日期：2015年6月15日下午3:37:56
	 * @param crowdfundID
	 * @return
	 */
	public JSONObject getWxCrowdfundById(int crowdfundID){
		String sql="select ms.membersNickName from Crowdfund cf inner join Members ms on cf.membersID =ms.membersID where cf.crowdfundIsDel=0 and cf.crowdfundID =?";
		JSONObject jn = new JSONObject();
		jn = findJson(sql, null,crowdfundID);
		return jn;
		
	}
	/**
	 * 方法名称: getAttentionByCrowdfundID<br>
	 * 描述：据crowdfundID获取关注列表
	 * 作者: 邢留杰
	 * 修改日期：2015年6月15日下午7:15:48
	 * @param crowdfundID
	 * @return
	 */
	public JSONArray getAttentionByCrowdfundID(int crowdfundID){
		String sql="select ms.membersID,ms.membersImage,ms.membersNickName from CrowdfundAttention cfa inner join Members ms on cfa.membersID =ms.membersID where  cfa.crowdfundID =? order by cfa.createTime desc";
		JSONArray jsonArray = new JSONArray();
		jsonArray = findJsonArray(sql,null,crowdfundID);
		return jsonArray;
	}
	/**
	 * 方法名称: findMembersByCrowdfundID<br>
	 * 描述：用于获取赞助助力的用户列表
	 * 作者: 邢留杰
	 * 修改日期：2015年6月8日下午2:40:24
	 * @param page
	 * @param size
	 * @param crowdfundID
	 * @return
	 */
	public Pagination<JSONArray> findMembersByCrowdfundID(int page,int size,int crowdfundID){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select ms.membersID,ms.membersNickName,ms.membersImage,cfm.crowdfundmarkID,cfm.crowdfundmarkMessage,cfm.crowdfundmarkAccount,cfm.createTime from CrowdfundMark cfm left join Members ms on cfm.membersID =ms.membersID where cfm.crowdfundID=?";
		String countSql="select count(crowdfundmarkID) from CrowdfundMark cfm where cfm.crowdfundID=?";
		super.findListJsonByPages(pagination, sql, countSql,crowdfundID);
		if(pagination.getList().size()>0){
			for(int i=0;i<pagination.getList().size();i++){
				int markId = pagination.getList().getJSONObject(i).getInt("crowdfundmarkID");
				JSONArray crowdfundReplys = crowdfundReplyService.findByMarkId(markId);
				pagination.getList().getJSONObject(i).put("replyList", crowdfundReplys);
			}
		}
		return pagination;
	}
}
