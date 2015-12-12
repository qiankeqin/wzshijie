package com.lagoqu.worldplay.bms.service;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Crowdfund;
import com.lagoqu.worldplay.service.AccountsService;
import com.lagoqu.worldplay.service.MembersService;
@Service
public class CfBmsService  extends BaseDao<Crowdfund>{

	@Resource
	AccountsService accountsService;
	
	@Resource
	MembersService membersService ;
	
	
	/**方法名称: crowdfundList<br>
	 * 描述：（后台发布心愿）心愿记录查询
	 * 作者: 王小欢
	 * 修改日期：2015年7月20日上午11:22:26
	 * @param page
	 * @param size
	 * @param membersPhone
	 * @return
	 */
	public Pagination<JSONArray> crowdfundList(int page,int size,String membersPhone,int crowdfundIsDel,int crowdfundState){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select cf.crowdfundID,cf.crowdfundDesc,cf.createTime,cf.operationName,cf.operationTime,ms.membersID,ms.membersPhone,ms.membersNickName,ms.identifyState from Crowdfund cf inner join Members ms on cf.membersID=ms.membersID where  cf.crowdfundIsDel=? and cf.crowdfundState=? and cf.crowdfundType=0";
		String countSql="select count(cf.crowdfundID) from Crowdfund cf inner join Members ms on cf.membersID=ms.membersID where cf.crowdfundIsDel=? and cf.crowdfundState=? and cf.crowdfundType=0";
		if(membersPhone!=null && membersPhone!=""){
			sql+=" and ms.membersPhone=?";
			countSql+=" and ms.membersPhone=?";
		}
		sql+= " order by cf.createTime desc ";
		if(membersPhone!=null && membersPhone!=""){
		     findListJsonByPages(pagination,sql,countSql,crowdfundIsDel,crowdfundState,membersPhone);
		}else{
			findListJsonByPages(pagination,sql,countSql,crowdfundIsDel,crowdfundState);
		}
		return pagination;
	}
	
	
	
	
	
	/**方法名称: crowdfundListZuji<br>
	 * 描述：（后台发布足迹）足迹记录查询
	 * 作者: 王小欢
	 * 修改日期：2015年11月14日上午11:04:36
	 * @param page
	 * @param size
	 * @param membersPhone
	 * @param crowdfundIsDel
	 * @param crowdfundState
	 * @return
	 */
	public Pagination<JSONArray> crowdfundListZuji(int page,int size,String membersPhone,int crowdfundIsDel,int crowdfundState){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select cf.crowdfundID,cf.crowdfundTitle,cf.crowdfundDesc,cf.createTime,cf.operationName,cf.operationTime,ms.membersID,ms.membersPhone,ms.membersNickName,ms.identifyState from Crowdfund cf inner join Members ms on cf.membersID=ms.membersID where  cf.crowdfundIsDel=? and cf.crowdfundState=? and cf.crowdfundType=1";
		String countSql="select count(cf.crowdfundID) from Crowdfund cf inner join Members ms on cf.membersID=ms.membersID where cf.crowdfundIsDel=? and cf.crowdfundState=? and cf.crowdfundType=1";
		if(membersPhone!=null && membersPhone!=""){
			sql+=" and ms.membersPhone=?";
			countSql+=" and ms.membersPhone=?";
		}
		sql+= " order by cf.createTime desc ";
		if(membersPhone!=null && membersPhone!=""){
		     findListJsonByPages(pagination,sql,countSql,crowdfundIsDel,crowdfundState,membersPhone);
		}else{
			findListJsonByPages(pagination,sql,countSql,crowdfundIsDel,crowdfundState);
		}
		return pagination;
	}
	
	
	
		

	public JSONArray userList(){
		JSONArray jsonArray=null;
		String sql="select ms.membersID,ms.membersNickName,ms.membersPhone from Members ms where ms.membersState=1";
		jsonArray=membersService.findJsonArray(sql, null, null);
		return jsonArray;
		}
	
}
