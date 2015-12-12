package com.lagoqu.worldplay.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Artbase;
/**描述：文字库<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月29日上午11:19:02 <br>
 * E-mail:  <br> 
 */
@Service
public class AppArtbaseService extends BaseDao<Artbase>{

	
	/**方法名称: ArtbaseList<br>
	 * 描述：文字库列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月31日上午11:35:05
	 * @param page
	 * @param size
	 * @param artbaseType
	 * @return
	 */
	public Pagination<JSONArray> ArtbaseList(int page,int size,int artbaseType){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select * from Artbase where artbaseType=? and artbaseState=0 order by createTime desc";
		String countSql="select count(artbaseID) from Artbase where artbaseType=? and artbaseState=0";
		findListJsonByPages(pagination,sql,countSql,artbaseType);
		return pagination;
	}
	

}
