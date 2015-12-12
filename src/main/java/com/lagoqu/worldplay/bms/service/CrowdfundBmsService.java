package com.lagoqu.worldplay.bms.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Crowdfund;
import com.lagoqu.worldplay.util.DateUtil;
@Service
public class CrowdfundBmsService extends BaseDao<Crowdfund>{

	
	
	/**方法名称: crowdfundList<br>
	 * 描述：助理查询
	 * 作者: 王小欢
	 * 修改日期：2015年7月20日上午11:22:26
	 * @param page
	 * @param size
	 * @param membersPhone
	 * @return
	 */
	public Pagination<JSONArray> crowdfundList(int page,int size,String membersPhone,int crowdfundIsDel,int crowdfundType){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select cf.crowdfundID,cf.crowdfundDesc,cf.crowdfundType,cf.createTime,cf.operationName,cf.operationTime,cf.crowdfundSort,ms.membersID,ms.membersPhone,ms.membersNickName,ms.identifyState from Crowdfund cf inner join Members ms on cf.membersID=ms.membersID where cf.crowdfundState=0 and cf.crowdfundIsDel="+crowdfundIsDel;
		String countSql="select count(cf.crowdfundID) from Crowdfund cf inner join Members ms on cf.membersID=ms.membersID where cf.crowdfundState=0 and cf.crowdfundIsDel="+crowdfundIsDel;
		if(membersPhone!=null && membersPhone!=""){
			sql+=" and ms.membersPhone='"+membersPhone+"'";
			countSql+=" and ms.membersPhone='"+membersPhone+"'";
		}
		if(crowdfundType!=2){
			sql+=" and cf.crowdfundType="+crowdfundType+"";
			countSql+=" and cf.crowdfundType="+crowdfundType+"";
		}
		sql+= " order by cf.crowdfundSort desc,cf.createTime desc ";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	
	
	/**方法名称: crowdfundRdList<br>
	 * 描述：推荐的心愿或足迹
	 * 作者: 王小欢
	 * 修改日期：2015年12月8日上午10:22:25
	 * @param page
	 * @param size
	 * @param membersPhone
	 * @param crowdfundIsDel
	 * @param crowdfundType
	 * @return
	 */
	public Pagination<JSONArray> crowdfundRdList(int page,int size,String membersPhone,int crowdfundIsDel,int crowdfundType){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select cf.crowdfundID,cf.crowdfundDesc,cf.crowdfundType,cf.crowdfundState,cf.createTime,cf.operationName,cf.operationTime,cf.crowdfundSort,ms.membersID,ms.membersPhone,ms.membersNickName,ms.identifyState from Crowdfund cf inner join Members ms on cf.membersID=ms.membersID where cf.crowdfundRdState=1 and cf.crowdfundIsDel="+crowdfundIsDel;
		String countSql="select count(cf.crowdfundID) from Crowdfund cf inner join Members ms on cf.membersID=ms.membersID where cf.crowdfundRdState=1 and cf.crowdfundIsDel="+crowdfundIsDel;
		if(membersPhone!=null && membersPhone!=""){
			sql+=" and ms.membersPhone='"+membersPhone+"'";
			countSql+=" and ms.membersPhone='"+membersPhone+"'";
		}
		if(crowdfundType!=2){
			sql+=" and cf.crowdfundType="+crowdfundType+"";
			countSql+=" and cf.crowdfundType="+crowdfundType+"";
		}
		sql+= " order by cf.crowdfundSort desc,cf.createTime desc ";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	/**方法名称: deleteCrowdfund<br>
	 * 描述：：删除助力
	 * 作者: 王小欢
	 * 修改日期：2015年7月20日上午11:47:05
	 * @param crowdfundID
	 * @throws SQLException 
	 */
	public boolean  deleteCrowdfund(int crowdfundID,int crowdfundIsDel,String operationName) throws SQLException{
		    Timestamp ts = new Timestamp(System.currentTimeMillis());
			String sql = "update Crowdfund set crowdfundIsDel = "+crowdfundIsDel+",operationTime= '"+ts+"',operationName='"+operationName+"' where crowdfundID="+crowdfundID;
			boolean delstate=super.execSql(sql);
			return delstate;
	}
	
	
	
	/**方法名称: updateRd<br>
	 * 描述：修改心愿或足迹推荐状态
	 * 作者: 王小欢
	 * 修改日期：2015年12月8日上午10:35:55
	 * @param crowdfundID
	 * @param crowdfundRdState
	 * @param operationName
	 * @return
	 * @throws SQLException
	 */
	public boolean  updateRd(int crowdfundID,int crowdfundRdState,String operationName) throws SQLException{
	    Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql = "update Crowdfund set crowdfundRdState = "+crowdfundRdState+",operationTime= '"+ts+"',operationName='"+operationName+"' where crowdfundID="+crowdfundID;
		boolean updateRdstate=super.execSql(sql);
		return updateRdstate;
}
	
	
	/**方法名称: saveCrowdfund<br>
	 * 描述：后台发布助力增加
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日上午9:38:26
	 * @param map
	 * @return
	 */
	public int saveCrowdfund(Map<String,String[]> map){
		Crowdfund crowdfund=new Crowdfund();
		crowdfund.setMembersID(Integer.parseInt(map.get("membersID")[0]));
		crowdfund.setCrowdfundTitle(map.get("crowdfundTitle")[0]);
		crowdfund.setCrowdfundDesc(map.get("crowdfundDesc")[0]);
		crowdfund.setCrowdfundRepay(map.get("crowdfundRepay")[0]);
		crowdfund.setCrowdfundDays(Integer.parseInt(map.get("crowdfundDays")[0]));
		crowdfund.setCrowdfundPath(map.get("crowdfundPath")[0]);
		crowdfund.setCrowdfundPic(map.get("crowdfundPic")[0]);
		crowdfund.setCrowdfundcentont(map.get("crowdfundTitle")[0]+map.get("crowdfundDesc")[0]+map.get("crowdfundRepay")[0]);
		crowdfund.setCrowdfundSort(0);
		crowdfund.setCrowdfundType(Integer.parseInt(map.get("crowdfundType")[0]));
		crowdfund.setCrowdfundState(Integer.parseInt(map.get("crowdfundState")[0]));
		//时间转化start
		Date d = new Date();
		DateUtil du = new DateUtil();
		String s = du.addDay(du.convertDateToString(d),Integer.parseInt(map.get("crowdfundDays")[0]));
		s= s+" 00:00:00";
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		ts = Timestamp.valueOf(s);
		//时间转化end
		crowdfund.setEndTime(ts);
		String createTime=map.get("createTime")[0];
		if(createTime!=null && createTime!=""){
			Timestamp crts = new Timestamp(System.currentTimeMillis());
			crts = Timestamp.valueOf(createTime);
			crowdfund.setCreateTime(crts);
		}
		int crowdfundID=insertBackID(crowdfund);
		return crowdfundID;
	}
	
	
	/**方法名称: updateCrowdfund<br>
	 * 描述：后台发布助力修改
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日上午9:38:24
	 * @param map
	 * @param operationName
	 * @return
	 */
	public boolean updateCrowdfund(Map<String,String[]> map,String operationName){
		int crowdfundID=Integer.parseInt(map.get("crowdfundID")[0]);
		String crowdfundTitle=map.get("crowdfundTitle")[0];
		String crowdfundDesc=map.get("crowdfundDesc")[0];
		String crowdfundRepay=map.get("crowdfundRepay")[0];
		int crowdfundSort=Integer.parseInt(map.get("crowdfundSort")[0]);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql = "update Crowdfund set crowdfundTitle = '"+crowdfundTitle+"',crowdfundDesc = '"+crowdfundDesc+"',crowdfundRepay = '"+crowdfundRepay+"',operationTime= '"+ts+"',operationName = '"+operationName+"',crowdfundSort="+crowdfundSort+",crowdfundcentont='"+crowdfundTitle+crowdfundDesc+crowdfundRepay+"' where crowdfundID="+crowdfundID;
		boolean updatestate=super.execSql(sql);
		return updatestate;
	}
	
	
	
	
	
	/**方法名称: crowdfundCountRmb<br>
	 * 描述：打赏记录大于3000RMB的订单信息
	 * 作者: 王小欢
	 * 修改日期：2015年11月19日上午10:55:10
	 * @param page
	 * @param size
	 * @param crowdfundIsDel
	 * @return
	 */
	public Pagination<JSONArray> crowdfundCountRmb(int page,int size){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select ms.membersID,ms.membersNickName,ms.membersPhone,ms.identifyState,cf.crowdfundID,cm.crowdfundmarkAccount,cm.createTime,oif.orderPayType,oif.orderSource from CrowdfundMark cm inner join Crowdfund cf on cm.crowdfundID=cf.crowdfundID inner join Members ms on cm.membersID=ms.membersID inner join OrderInfo oif on oif.orderID=cm.orderID where cm.crowdfundmarkAccount>=300000 order by cm.createTime desc";
		String countSql="select count(*) from CrowdfundMark cm inner join Crowdfund cf on cm.crowdfundID=cf.crowdfundID inner join Members ms on cm.membersID=ms.membersID where cm.crowdfundmarkAccount>=300000";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	
	/**方法名称: crowdfundCountRmbSum<br>
	 * 描述：打赏记录大于3000RMB的订单总额
	 * 作者: 王小欢
	 * 修改日期：2015年12月11日下午4:05:00
	 * @return
	 */
	public JSONArray crowdfundCountRmbSum(){
		String sumSqlSql="select sum(cm.crowdfundmarkAccount) as sumAccountPrice from CrowdfundMark cm where cm.crowdfundmarkAccount>=300000";
		JSONArray jsonArray=super.findJsonArray(sumSqlSql,null);
		return jsonArray;
	}
}
