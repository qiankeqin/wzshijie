package com.lagoqu.worldplay.bms.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.ArtbaseType;
import com.lagoqu.worldplay.entity.LabelStore;
/**描述：文字库分类管理<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月5日下午5:04:15 <br>
 * E-mail:  <br> 
 */
@Service
public class ArtbaseTypeBmsService extends BaseDao<ArtbaseType>{

	
	/**方法名称: artbaseTypeList<br>
	 * 描述：文字库分类查询
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午5:25:00
	 * @param page
	 * @param size
	 * @return
	 */
	public Pagination<JSONArray> artbaseTypeList(int page,int size){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select * from ArtbaseType where artbaseTypeState=0 order by createTime desc";
		String countSql="select count(artbaseTypeID) from ArtbaseType where artbaseTypeState=0";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	
	/**方法名称: artbaseTypeadd<br>
	 * 描述：文字库分类添加
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午2:54:32
	 * @param map
	 * @return
	 */
	public int artbaseTypeadd(Map<String,String[]> map){
		ArtbaseType artbaseType=new ArtbaseType();
		artbaseType.setArtbaseTypeName(map.get("artbaseTypeName")[0]);
		artbaseType.setArtbaseTypeState(0);
		artbaseType.setArtbaseTypeSort(0);
		int id=insertBackID(artbaseType);
		return id;
	}

	
	/**方法名称: updateArtbaseType<br>
	 * 描述：文字库分类修改
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:12:24
	 * @param map
	 * @param operationName
	 * @return
	 */
	public boolean updateArtbaseType(Map<String,String[]> map,String operationName){
		int artbaseTypeID=Integer.parseInt(map.get("artbaseTypeID")[0]);
		String artbaseTypeName=map.get("artbaseTypeName")[0];
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql = "update ArtbaseType set artbaseTypeName = '"+artbaseTypeName+"',operationTime= '"+ts+"',operationName = '"+operationName+"' where artbaseTypeID="+artbaseTypeID;
		boolean updatestate=super.execSql(sql);
		return updatestate;
	}
	
	
	
	
	/**方法名称: deleteArtbaseType<br>
	 * 描述：文字库分类删除
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:23:01
	 * @param labelStoreID
	 * @param labelStoreState
	 * @param operationName
	 * @return
	 * @throws SQLException
	 */
	public boolean  deleteArtbaseType(int artbaseTypeID,int artbaseTypeState,String operationName) throws SQLException{
	    Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql = "update ArtbaseType set artbaseTypeState = "+artbaseTypeState+",operationTime= '"+ts+"',operationName='"+operationName+"' where artbaseTypeID="+artbaseTypeID;
		boolean delstate=super.execSql(sql);
		return delstate;
}
}
