package com.lagoqu.worldplay.bms.service;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.CrowdfundMark;
@Service
public class CrowdfundMarkBmsService extends BaseDao<CrowdfundMark>{

	/**方法名称: crowdfundMarkList<br>
	 * 描述：收到助力详情明细查询
	 * 作者: 王小欢
	 * 修改日期：2015年7月20日下午3:24:52
	 * @param page
	 * @param size
	 * @param crowdfundID
	 * @return
	 */
	public Pagination<JSONArray> crowdfundMarkList(int page,int size,int crowdfundID){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select cfm.crowdfundmarkAccount,cfm.crowdfundmarkMessage,cfm.createTime,ms.membersNickName,oif.orderPayType from CrowdfundMark cfm inner join Members ms on ms.membersID=cfm.membersID inner join orderInfo oif on oif.orderID=cfm.orderID where cfm.crowdfundID="+crowdfundID+" order by cfm.createTime desc";
		String countSql="select count(cfm.crowdfundmarkID) from CrowdfundMark cfm where cfm.crowdfundID="+crowdfundID;
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
}
