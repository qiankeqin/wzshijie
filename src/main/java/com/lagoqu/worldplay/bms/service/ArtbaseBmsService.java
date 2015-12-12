package com.lagoqu.worldplay.bms.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Artbase;
import com.lagoqu.worldplay.entity.ArtbaseType;
/**描述：文字库管理<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月5日下午5:04:15 <br>
 * E-mail:  <br> 
 */
@Service
public class ArtbaseBmsService extends BaseDao<Artbase>{

	
	/**方法名称: artbaseList<br>
	 * 描述：文字库查询
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午5:25:00
	 * @param page
	 * @param size
	 * @return
	 */
	public Pagination<JSONArray> artbaseList(int page,int size){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select * from Artbase as ab inner join ArtbaseType as abt on ab.artbaseType=abt.artbaseTypeID where ab.artbaseState=0 order by ab.createTime desc";
		String countSql="select count(artbaseID) from Artbase where artbaseState=0";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	
	/**方法名称: artbaseadd<br>
	 * 描述：文字库添加
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午2:54:32
	 * @param map
	 * @return
	 */
	public int artbaseadd(Map<String,String[]> map){
		Artbase artbase=new Artbase();
		artbase.setArtbaseContent(map.get("artbaseContent")[0]);
		artbase.setArtbaseMarkPath(map.get("artbaseMarkPath")[0]);
		artbase.setArtbaseType(Integer.parseInt(map.get("artbaseType")[0]));
		artbase.setArtbaseState(0);
		int id=insertBackID(artbase);
		return id;
	}

	
	/**方法名称: updateArtbase<br>
	 * 描述：文字库修改
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:12:24
	 * @param map
	 * @param operationName
	 * @return
	 */
	public boolean updateArtbase(Map<String,String[]> map,String operationName){
		int artbaseID=Integer.parseInt(map.get("artbaseID")[0]);
		String artbaseContent=map.get("artbaseContent")[0];
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql = "update Artbase set artbaseContent = '"+artbaseContent+"',operationTime= '"+ts+"',operationName = '"+operationName+"' where artbaseID="+artbaseID;
		System.out.println(sql);
		boolean updatestate=super.execSql(sql);
		return updatestate;
	}
	
	
	
	
	/**方法名称: deleteArtbase<br>
	 * 描述：文字库删除
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:23:01
	 * @param labelStoreID
	 * @param labelStoreState
	 * @param operationName
	 * @return
	 * @throws SQLException
	 */
	public boolean  deleteArtbase(int artbaseID,int artbaseState,String operationName) throws SQLException{
	    Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql = "update Artbase set artbaseState = "+artbaseState+",operationTime= '"+ts+"',operationName='"+operationName+"' where artbaseID="+artbaseID;
		boolean delstate=super.execSql(sql);
		return delstate;
}
}
