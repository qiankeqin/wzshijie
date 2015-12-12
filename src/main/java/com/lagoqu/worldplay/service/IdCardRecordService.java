package com.lagoqu.worldplay.service;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.IdCardRecord;

@Service
public class IdCardRecordService extends BaseDao<IdCardRecord>{
	/**
	 * 方法名称: findPageData<br>
	 * 描述：待审核列表
	 * 作者: 邢留杰
	 * 修改日期：2015年7月19日上午11:54:22
	 * @param page
	 * @param size
	 * @return
	 */
	public Pagination<JSONArray> findPageData(int page,int size,String membersPhone){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		StringBuffer sql = new StringBuffer();
		sql.append("select ms.membersPhone,ms.membersNickName,ic.idCardRecordId,ic.membersID,ic.idCardName,ic.correctSideImage,ic.oppositeSideImage,ic.idCardNum,ic.createTime,ic.idCardRecordState,ic.updateTime from IdCardRecord ic inner join Members ms on ic.membersID =ms.membersID ");
		StringBuffer countSql = new StringBuffer();
		countSql.append("select count(ic.idCardRecordId) from IdCardRecord ic inner join Members ms on ic.membersID =ms.membersID ");
		if(membersPhone!=null&&!"".equals(membersPhone)){
			sql.append(" and ms.membersPhone='"+membersPhone+"'");
			countSql.append(" and ms.membersPhone='"+membersPhone+"'");
		}
		sql.append(" order by ic.createTime desc");
		return super.findListJsonByPages(pagination, sql.toString(), countSql.toString());
	}
}
