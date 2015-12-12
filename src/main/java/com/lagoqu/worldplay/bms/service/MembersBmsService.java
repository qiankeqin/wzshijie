package com.lagoqu.worldplay.bms.service;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Members;
@Service
public class MembersBmsService extends BaseDao<Members>{

	
	
	/**方法名称: membersList<br>
	 * 描述：玩赚世界用户查询
	 * 作者: 王小欢
	 * 修改日期：2015年8月20日上午11:22:26
	 * @param page
	 * @param size
	 * @param membersPhone
	 * @return
	 */
	public Pagination<JSONArray> membersList(int page,int size,String membersPhone,String membersNickName){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select ms.membersID,ms.membersNickName,ms.membersPhone,ms.membersSex,ms.membersLocation,ms.membersWxID,ms.identifyState,ms.createTime from Members ms where ms.membersState=0";
		String countSql="select count(ms.membersID) from Members ms where ms.membersState=0";
		if(membersPhone!=null && membersPhone!=""){
			sql+=" and ms.membersPhone like'%"+membersPhone+"%'";
			countSql+=" and ms.membersPhone like'%"+membersPhone+"%'";
		}
		if(membersNickName!=null && membersNickName!=""){
			sql+=" and ms.membersNickName like'%"+membersNickName+"%'";
			countSql+=" and ms.membersNickName like'%"+membersNickName+"%'";
		}
		sql+= " order by ms.createTime desc ";
		findListJsonByPages(pagination,sql,countSql);
		System.out.println(sql);
		System.out.println(countSql);
		return pagination;
	}
}
