package com.lagoqu.worldplay.bms.service;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.ReportRecord;

/**描述：举报记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月5日下午4:22:28 <br>
 * E-mail:  <br> 
 */
@Service
public class ReportRecordBmsService extends BaseDao<ReportRecord>{

	
	/**方法名称: reportRecordList<br>
	 * 描述：举报记录查询
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午4:24:53
	 * @param page
	 * @param size
	 * @return
	 */
	public Pagination<JSONArray> reportRecordList(int page,int size){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select * from ReportRecord order by createTime desc";
		String countSql="select count(reportRecordID) from ReportRecord";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
}
