package com.lagoqu.worldplay.bms.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Carousel;
import com.lagoqu.worldplay.entity.LabelStore;
/**描述：标签库管理<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月5日下午5:04:15 <br>
 * E-mail:  <br> 
 */
@Service
public class LabelStoreBmsService extends BaseDao<LabelStore>{

	
	/**方法名称: labelStoreList<br>
	 * 描述：标签库查询
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午5:25:00
	 * @param page
	 * @param size
	 * @return
	 */
	public Pagination<JSONArray> labelStoreList(int page,int size){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select * from LabelStore where labelStoreState=0 order by createTime desc";
		String countSql="select count(labelStoreID) from LabelStore where labelStoreState=0";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	
	/**方法名称: labelStoreadd<br>
	 * 描述：标签库添加
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午2:54:32
	 * @param map
	 * @return
	 */
	public int labelStoreadd(Map<String,String[]> map){
		LabelStore labelStore=new LabelStore();
		labelStore.setLabelStoreName(map.get("labelStoreName")[0]);
		labelStore.setLabelStoreState(0);
		labelStore.setLabelStoreSort(0);
		int id=insertBackID(labelStore);
		return id;
	}

	
	/**方法名称: updateLabelStore<br>
	 * 描述：标签库修改
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:12:24
	 * @param map
	 * @param operationName
	 * @return
	 */
	public boolean updateLabelStore(Map<String,String[]> map,String operationName){
		int labelStoreID=Integer.parseInt(map.get("labelStoreID")[0]);
		String labelStoreName=map.get("labelStoreName")[0];
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql = "update LabelStore set labelStoreName = '"+labelStoreName+"',operationTime= '"+ts+"',operationName = '"+operationName+"' where labelStoreID="+labelStoreID;
		boolean updatestate=super.execSql(sql);
		return updatestate;
	}
	
	
	
	
	/**方法名称: deleteLabelStore<br>
	 * 描述：标签库删除
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:23:01
	 * @param labelStoreID
	 * @param labelStoreState
	 * @param operationName
	 * @return
	 * @throws SQLException
	 */
	public boolean  deleteLabelStore(int labelStoreID,int labelStoreState,String operationName) throws SQLException{
	    Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql = "update LabelStore set labelStoreState = "+labelStoreState+",operationTime= '"+ts+"',operationName='"+operationName+"' where labelStoreID="+labelStoreID;
		boolean delstate=super.execSql(sql);
		return delstate;
}
}
