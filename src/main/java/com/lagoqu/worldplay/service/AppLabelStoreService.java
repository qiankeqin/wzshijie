package com.lagoqu.worldplay.service;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.LabelStore;
/**描述：标签库<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月29日上午11:20:39 <br>
 * E-mail:  <br> 
 */
@Service
public class AppLabelStoreService extends BaseDao<LabelStore>{

	
	
	/**方法名称: LabelList<br>
	 * 描述：标签库列表
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日上午10:51:45
	 * @param page
	 * @param size
	 * @return
	 */
	public Pagination<JSONArray> LabelList(int page,int size){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select * from LabelStore";
		String countSql="select count(labelStoreID) from LabelStore";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
}
