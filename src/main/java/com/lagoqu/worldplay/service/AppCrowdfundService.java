package com.lagoqu.worldplay.service;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Crowdfund;
import com.lagoqu.worldplay.entity.TrackRecord;
import com.lagoqu.worldplay.util.DateUtil;
/**描述：旅游助力<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月4日下午6:04:23 <br>
 * E-mail:  <br> 
 */
@Service
public class AppCrowdfundService extends BaseDao<Crowdfund>{

	
	@Resource
	AppTrackRecordService appTrackRecordService;
	
	
	/**方法名称: crowdfundList<br>
	 * 描述：App首页列表查询
	 * 作者: 王小欢
	 * 修改日期：2015年10月29日下午3:33:35
	 * @param page
	 * @param size
	 * @param memberID
	 * @param updateTime
	 * @return
	 */
	public Pagination<JSONArray> crowdfundList(int page,int size,String updateTime){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql="select cf.crowdfundID,cf.crowdfundType,cf.crowdfundAccount,cf.crowdfundTitle,cf.crowdfundDesc,cf.crowdfundPath,cf.createTime,cf.crowdfundTimes,cf.crowdfundaccTimes,cf.endTime,case when cf.endTime>NOW() then '0' ELSE '1' END as isdateline,ms.membersID,ms.membersNickName,ms.membersImage,count(cfa.crowdfundID) as cfacount,cf.crowdfundState from Crowdfund cf inner join Members ms on cf.membersID =ms.membersID left join CrowdfundAttention cfa on cfa.crowdfundID=cf.crowdfundID where cf.crowdfundIsDel=0 ";
		String countSql="select count(cf.crowdfundID) from Crowdfund cf where cf.crowdfundIsDel=0 ";
		sql+=" and cf.endTime>'"+ts+"'";
		countSql+="  and cf.endTime>'"+ts+"'";
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
	
	
	
	/**方法名称: crowdfundRdList<br>
	 * 描述：App推荐列表查询
	 * 作者: 王小欢
	 * 修改日期：2015年12月7日下午5:33:00
	 * @param page
	 * @param size
	 * @param updateTime
	 * @param crowdfundType
	 * @return
	 */
	public Pagination<JSONArray> crowdfundRdList(int page,int size,String updateTime,int crowdfundType){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql="select cf.crowdfundID,cf.crowdfundType,cf.crowdfundAccount,cf.crowdfundTitle,cf.crowdfundDesc,cf.crowdfundPath,cf.createTime,cf.crowdfundTimes,cf.crowdfundaccTimes,cf.endTime,case when cf.endTime>NOW() then '0' ELSE '1' END as isdateline,ms.membersID,ms.membersNickName,ms.membersImage,count(cfa.crowdfundID) as cfacount,cf.crowdfundState from Crowdfund cf inner join Members ms on cf.membersID =ms.membersID left join CrowdfundAttention cfa on cfa.crowdfundID=cf.crowdfundID where cf.crowdfundIsDel=0 and cf.crowdfundRdState=1 ";
		String countSql="select count(cf.crowdfundID) from Crowdfund cf where cf.crowdfundIsDel=0 and cf.crowdfundRdState=1 ";
		sql+=" and cf.endTime>'"+ts+"'";
		countSql+="  and cf.endTime>'"+ts+"'";
		sql+=" and cf.crowdfundType="+crowdfundType+"";
		countSql+="  and cf.crowdfundType="+crowdfundType+"";
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
	
	
	
	
	/**方法名称: CrowdfundMembersList<br>
	 * 描述：个人发布的心愿或足迹
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日下午3:12:02
	 * @param page
	 * @param size
	 * @param crowdfundType
	 * @param membersID
	 * @return
	 */
	public Pagination<JSONArray> CrowdfundMembersList(int page,int size,int crowdfundType,int membersID){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql="select cf.crowdfundID,cf.membersID,cf.crowdfundType,cf.crowdfundDesc,cf.crowdfundPath,cf.createTime,case when cf.endTime>NOW() then '0' ELSE '1' END as isdateline from Crowdfund cf where cf.crowdfundIsDel=0 and cf.crowdfundType=? and cf.membersID=?";
		String countSql="select count(cf.crowdfundID) from Crowdfund cf where cf.crowdfundIsDel=0 and cf.crowdfundType=? and cf.membersID=?";
		sql+= " order by cf.createTime desc ";
		System.out.println(sql);
		findListJsonByPages(pagination,sql,countSql,crowdfundType,membersID);
		return pagination;
		
	}
	
	
	
	
	/**方法名称: CrowdfundMembersAttentionList<br>
	 * 描述：个人关注的心愿或足迹
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日下午3:47:23
	 * @param page
	 * @param size
	 * @param crowdfundType
	 * @param membersID
	 * @return
	 */
	public Pagination<JSONArray> CrowdfundMembersAttentionList(int page,int size,int crowdfundType,int membersID){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql="select cf.crowdfundID,cf.crowdfundType,cf.crowdfundDesc,cf.crowdfundPath,cf.createTime,case when cf.endTime>NOW() then '0' ELSE '1' END as isdateline,ms.membersID,ms.membersNickName,ms.membersImage from CrowdfundAttention as cfa inner join Crowdfund as cf on cfa.crowdfundID=cf.crowdfundID inner join Members as ms on cf.membersID=ms.membersID where cf.crowdfundIsDel=0 and cf.crowdfundType=? and cfa.membersID=?";
		String countSql="select count(cfa.caID) from CrowdfundAttention as cfa inner join Crowdfund as cf on cfa.crowdfundID=cf.crowdfundID where cf.crowdfundIsDel=0 and cf.crowdfundType=? and cfa.membersID=?";
		sql+= " order by cfa.createTime desc ";
		System.out.println(sql);
		findListJsonByPages(pagination,sql,countSql,crowdfundType,membersID);
		return pagination;
		
	}
	
	
	
	
	
	/**方法名称: CrowdfundMembersMarkList<br>
	 * 描述：个人打赏的心愿或足迹
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日下午3:47:23
	 * @param page
	 * @param size
	 * @param crowdfundType
	 * @param membersID
	 * @return
	 */
	public Pagination<JSONArray> CrowdfundMembersMarkList(int page,int size,int crowdfundType,int membersID){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql="select cf.crowdfundID,cf.crowdfundType,cf.crowdfundDesc,cf.crowdfundPath,cf.createTime,case when cf.endTime>NOW() then '0' ELSE '1' END as isdateline,ms.membersID,ms.membersNickName,ms.membersImage,cfm.crowdfundmarkAccount as markAccount,cfm.createTime as cfmCreateTime from CrowdfundMark as cfm inner join Crowdfund as cf on cfm.crowdfundID=cf.crowdfundID inner join Members as ms on cf.membersID=ms.membersID where cf.crowdfundIsDel=0 and cf.crowdfundType=? and cfm.membersID=?";
		String countSql="select count(cfm.crowdfundmarkID) from CrowdfundMark as cfm inner join Crowdfund as cf on cfm.crowdfundID=cf.crowdfundID where cf.crowdfundIsDel=0 and cf.crowdfundType=? and cfm.membersID=?";
		sql+= " order by cfm.createTime desc ";
		System.out.println(sql);
		findListJsonByPages(pagination,sql,countSql,crowdfundType,membersID);
		return pagination;
		
	}
	
	
	
	
	/**方法名称: crowdfundDetail<br>
	 * 描述：心愿或足迹详情
	 * 作者: 王小欢
	 * 修改日期：2015年10月29日下午6:23:15
	 * @param crowdfundID
	 * @param membersID
	 * @param crowdfundType
	 * @return
	 */
	public JSONObject crowdfundDetail(int crowdfundID,int crowdfundType,int membersID){
		JSONObject backJsonObject=new JSONObject();
		//修改访问次数
		String sqlupdate = "update Crowdfund set crowdfundaccTimes=crowdfundaccTimes+1 where crowdfundID=?";
		super.execSql(sqlupdate,crowdfundID);
		boolean bl = false;
		if(membersID!=0){
			String sql="select ca.caID from CrowdfundAttention ca where ca.crowdfundID=? and  ca.membersID=?";
			JSONObject jsonObject = super.findJson(sql, null,crowdfundID,membersID);
			if(jsonObject!=null){
				bl=true;
			}else{
				bl=false;
			}
		}else{
			bl = false;
		}
		
		//查看心愿详情
		if(crowdfundType==0){	
			String sqlselect="select cf.crowdfundID,cf.crowdfundType,cf.crowdfundAccount,cf.crowdfundcentont as crowdfundDesc,cf.crowdfundPath,cf.crowdfundPic,cf.createTime,cf.crowdfundTimes,cf.crowdfundaccTimes,cf.endTime,case when cf.endTime>NOW() then '0' ELSE '1' END as isdateline,ms.membersID,ms.membersNickName,ms.membersImage,count(cfa.crowdfundID) as cfacount,cf.crowdfundState from Crowdfund cf inner join Members ms on cf.membersID =ms.membersID left join CrowdfundAttention cfa on cfa.crowdfundID=cf.crowdfundID where cf.crowdfundIsDel=0 and cf.crowdfundID =?";
			JSONObject jsonObject = findJson(sqlselect,null,crowdfundID);
			jsonObject.element("ifAttention", bl);
			if(jsonObject!=null){
				backJsonObject.put("data",jsonObject);
				backJsonObject.put("state", true);
				return backJsonObject;
			}else {
				backJsonObject.put("message","获取心愿详情信息失败，请重试");
				backJsonObject.put("state", false);
				return backJsonObject; 
			}
		}else{   //查看足迹详情
			String sqlselect="select cf.crowdfundID,cf.crowdfundType,cf.crowdfundAccount,cf.crowdfundTitle,cf.createTime,cf.crowdfundTimes,cf.crowdfundaccTimes,cf.endTime,case when cf.endTime>NOW() then '0' ELSE '1' END as isdateline,ms.membersID,ms.membersNickName,ms.membersImage,count(cfa.crowdfundID) as cfacount,cf.crowdfundState from Crowdfund cf inner join Members ms on cf.membersID =ms.membersID left join CrowdfundAttention cfa on cfa.crowdfundID=cf.crowdfundID where cf.crowdfundIsDel=0 and cf.crowdfundID =?";
			JSONObject jsonObject = findJson(sqlselect, null,crowdfundID);
			String sqlSelectDa="select * from TrackRecord where crowdfundID=?";
			JSONArray jsonArray=findJsonArray(sqlSelectDa, null, crowdfundID);
			jsonObject.element("detail", jsonArray);
			jsonObject.element("ifAttention", bl);
			if(jsonObject!=null){
				backJsonObject.put("data",jsonObject);
				backJsonObject.put("state", true);
				return backJsonObject;
			}else {
				backJsonObject.put("message","获取足迹详情信息失败，请重试");
				backJsonObject.put("state", false);
				return backJsonObject; 
			}
		}
	}
	
	
	
	/**方法名称: crowdfundMarkList<br>
	 * 描述：心愿或足迹打赏记录(包括评论)
	 * 作者: 王小欢
	 * 修改日期：2015年10月29日下午8:43:07
	 * @param page
	 * @param size
	 * @param crowdfundID
	 * @return
	 */
	public Pagination<JSONArray> crowdfundMarkList(int page,int size,int crowdfundID){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select ms.membersID,ms.membersNickName,ms.membersImage,cfm.crowdfundmarkID,cfm.crowdfundmarkMessage,cfm.crowdfundmarkAccount,cfm.createTime from CrowdfundMark cfm left join Members ms on cfm.membersID =ms.membersID where cfm.crowdfundID=? order by cfm.createTime desc";
		String countSql="select count(crowdfundmarkID) from CrowdfundMark cfm where cfm.crowdfundID=?";
		super.findListJsonByPages(pagination, sql, countSql,crowdfundID);
		if(pagination.getList().size()>0){
			for(int i=0;i<pagination.getList().size();i++){
				int markId = pagination.getList().getJSONObject(i).getInt("crowdfundmarkID");
				String sqlselect = "select cr.crowdfundReplyID,cr.membersID,ms1.membersNickName,cr.replyMembersID,ms2.membersNickName as replyMembersNickName,cr.crowdfundID,cr.crowdfundMarkID,cr.crowdfundReply,cr.createTime from CrowdfundReply cr INNER JOIN Members ms1 on ms1.membersID=cr.membersID INNER JOIN Members ms2 on ms2.membersID=cr.replyMembersID  where crowdfundMarkID=?";
				JSONArray crowdfundReplys =super.findJsonArray(sqlselect, null, markId);
				pagination.getList().getJSONObject(i).put("replyList", crowdfundReplys);
			}
		}
		return pagination;
	}
	
	
	
	
	
	
	/**方法名称: CrowdfundReleaseWish<br>
	 * 描述：心愿发布
	 * 作者: 王小欢
	 * 修改日期：2015年10月30日下午2:11:15
	 * @param membersID
	 * @param crowdfundDesc
	 * @param crowdfundPath
	 * @param crowdfundPic
	 * @param crowdfundDays
	 * @return
	 */
	public JSONObject CrowdfundReleaseWish(int membersID,String crowdfundDesc,String crowdfundPath,String crowdfundPic,int crowdfundDays){
		JSONObject backJsonObject=new JSONObject();
		Crowdfund crowdfund=new Crowdfund();
		crowdfund.setMembersID(membersID);
		crowdfund.setCrowdfundType(0);
		crowdfund.setCrowdfundDesc(crowdfundDesc);
		crowdfund.setCrowdfundPath(crowdfundPath);
		crowdfund.setCrowdfundPic(crowdfundPic);
		crowdfund.setCrowdfundDays(crowdfundDays);
		Date d = new Date();
		DateUtil du = new DateUtil();
		String s = du.addDay(du.convertDateToString(d),crowdfund.getCrowdfundDays());
		s= s+" 00:00:00";
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		ts = Timestamp.valueOf(s);
		//时间转化end
		crowdfund.setEndTime(ts);
		crowdfund.setCrowdfundIsDel(0);
		crowdfund.setCrowdfundState(0);
		crowdfund.setCrowdfundcentont(crowdfundDesc);
		int crowdfundID=insertBackID(crowdfund);
		if(crowdfundID!=-1){
			backJsonObject.put("data",crowdfundID);
			backJsonObject.put("state", true);
			return backJsonObject;
		}else {
			backJsonObject.put("message","发布心愿信息失败，请重试");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
	
	
	
	/**方法名称: CrowdfundWishLabel<br>
	 * 描述：心愿添加标签
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日上午10:27:18
	 * @param crowdfundID
	 * @param crowdfundLabel
	 * @return
	 */
	public JSONObject CrowdfundWishLabel(int crowdfundID,String crowdfundLabel){
		JSONObject backJsonObject=new JSONObject();
		//修改访问次数
		String sqlupdate = "update Crowdfund set crowdfundLabel=? where crowdfundID=?";
		boolean state=super.execSql(sqlupdate,crowdfundLabel,crowdfundID);
		if(state==true){
			backJsonObject.put("data",crowdfundID);
			backJsonObject.put("state", true);
			return backJsonObject;
		}else {
			backJsonObject.put("message","添加标签失败");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
	
	
	
	/**方法名称: CrowdfundReleaseSpoor<br>
	 * 描述：足迹发布
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日下午1:33:39
	 * @param membersID
	 * @param crowdfundTitle
	 * @param crowdfundPath
	 * @param details
	 * @return
	 */
	public JSONObject CrowdfundReleaseSpoor(int membersID,String crowdfundTitle,String crowdfundDesc,String crowdfundPath,JSONArray details){
		JSONObject backJsonObject=new JSONObject();
		Crowdfund crowdfund=new Crowdfund();
		crowdfund.setMembersID(membersID);
		crowdfund.setCrowdfundType(1);
		crowdfund.setCrowdfundTitle(crowdfundTitle);
		crowdfund.setCrowdfundDesc(crowdfundDesc);
		crowdfund.setCrowdfundPath(crowdfundPath);
		Date d = new Date();
		DateUtil du = new DateUtil();
		String s = du.addDay(du.convertDateToString(d),6666);
		s= s+" 00:00:00";
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		ts = Timestamp.valueOf(s);
		//时间转化end
		crowdfund.setEndTime(ts);
		crowdfund.setCrowdfundIsDel(0);
		crowdfund.setCrowdfundState(0);
		crowdfund.setCrowdfundcentont(crowdfundDesc);
		int crowdfundID=insertBackID(crowdfund);
		 
		if(crowdfundID!=-1){
			for (int i = 0; i < details.size(); i++) {
				JSONObject jo = (JSONObject) details.get(i);
				appTrackRecordService.SaveTrackRecord(jo, crowdfundID);
			}
			backJsonObject.put("data",crowdfundID);
			backJsonObject.put("state", true);
			return backJsonObject;
		}else {
			backJsonObject.put("message","发布足迹信息失败，请重试");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
	
	
	/**方法名称: CrowdfundReleaseDelete<br>
	 * 描述：删除信息
	 * * 用于删除信息，如果已经有人打赏过，不能删除
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日下午5:37:53
	 * @param crowdfundID
	 * @return
	 */
	public JSONObject CrowdfundReleaseDelete(int crowdfundID){
		JSONObject backJsonObject=new JSONObject();
		String sql="select crowdfundID from CrowdfundMark where crowdfundID=?";
		JSONObject crowdfundMarks= findJson(sql, " id desc",crowdfundID);
		if(crowdfundMarks!=null){
			backJsonObject.put("message","已经有人打赏，不能删除");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}else{
			String sqlupdate = "update Crowdfund set crowdfundIsDel = 1 where crowdfundID=?";
			boolean state=super.execSql(sqlupdate,crowdfundID);
			if(state==true){
				backJsonObject.put("data","删除成功");
				backJsonObject.put("state", true);
				return backJsonObject;
			}else{
				backJsonObject.put("message","删除失败");
				backJsonObject.put("state", false);
				return backJsonObject; 
			}
			
		}
	}
	
	
	
	
	
	/**方法名称: CrowdfundReleaseUpdate<br>
	 * 描述：：修改心愿或足迹
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日下午5:09:50
	 * @param crowdfundID
	 * @param crowdfundType
	 * @param crowdfundDesc
	 * @param crowdfundPath
	 * @param crowdfundPic
	 * @param crowdfundTitle
	 * @param detail
	 * @return
	 */
	public JSONObject CrowdfundReleaseUpdate(int crowdfundID,int crowdfundType,String crowdfundDesc,String crowdfundPath,String crowdfundPic,String crowdfundTitle,JSONArray detail){
		JSONObject backJsonObject=new JSONObject();
		if(crowdfundType==0){
			String sqlupdate = "update Crowdfund set crowdfundDesc=?,crowdfundcentont=? where crowdfundID=?";
			boolean state=super.execSql(sqlupdate,crowdfundDesc,crowdfundDesc,crowdfundID);
			if(state==true){
				backJsonObject.put("data","修改心愿成功");
				backJsonObject.put("state", true);
				return backJsonObject;
			}else{
				backJsonObject.put("message","修改心愿失败");
				backJsonObject.put("state", false);
				return backJsonObject; 
			}
		}else{
			String sqlupdate = "update Crowdfund set crowdfundTitle=?,crowdfundDesc=?,crowdfundPath=? where crowdfundID=?";
			String sqlDel="delete from TrackRecord where crowdfundID=?";
			boolean state=super.execSql(sqlupdate,crowdfundTitle,crowdfundDesc,crowdfundPath,crowdfundID);
			boolean delstate=super.execSql(sqlDel,crowdfundID);
			if(state==true){
				
				for (int i = 0; i < detail.size(); i++) {
					JSONObject jo = (JSONObject) detail.get(i);
					appTrackRecordService.SaveTrackRecord(jo, crowdfundID);
				}
				backJsonObject.put("data","修改足迹成功");
				backJsonObject.put("state", true);
				return backJsonObject;
			}else{
				backJsonObject.put("message","修改足迹失败");
				backJsonObject.put("state", false);
				return backJsonObject; 
			}
		}
		
	}
	
	
	
	/**方法名称: otherMemberSpace<br>
	 * 描述：他的空间信息展示
	 * 作者: 王小欢
	 * 修改日期：2015年11月11日下午3:31:07
	 * @param membersID
	 * @return
	 */
	public JSONObject otherMemberSpace(int membersID){
		JSONObject backJsonObject=new JSONObject();
		String sql1="select ms.membersID,ms.membersNickName,ms.membersImage from Members as ms where ms.membersID=?";
		JSONObject jsonObject= findJson(sql1, null, membersID);
		String sql2="select count(*) as countNumber from Crowdfund as cf  where cf.crowdfundIsDel=0 and cf.crowdfundType=0 and cf.membersID=?";
		JSONObject jsonObject1= findJson(sql2, null, membersID);
		String sql3="select count(*) as countNumber from Crowdfund as cf  where cf.crowdfundIsDel=0 and cf.crowdfundType=1 and cf.membersID=?";
		JSONObject jsonObject2= findJson(sql3, null, membersID);
		jsonObject.element("xinyuan", jsonObject1.get("countNumber"));
		jsonObject.element("zuji", jsonObject2.get("countNumber"));
		if(jsonObject!=null){
			backJsonObject.put("data",jsonObject);
			backJsonObject.put("state", true);
			return backJsonObject;
		}else{
			backJsonObject.put("message","获取空间信息失败");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
}
