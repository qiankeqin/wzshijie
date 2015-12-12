package com.lagoqu.worldplay.bms.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.LabelStore;
import com.lagoqu.worldplay.entity.PhotoGallery;
/**描述：图片库管理<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月6日上午11:04:56 <br>
 * E-mail:  <br> 
 */
@Service
public class PhotoGalleryBmsService extends BaseDao<PhotoGallery>{

	
	/**方法名称: photoGalleryList<br>
	 * 描述：图片库查询
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午5:25:00
	 * @param page
	 * @param size
	 * @return
	 */
	public Pagination<JSONArray> photoGalleryList(int page,int size){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select * from PhotoGallery where photoGalleryState=0 order by createTime desc";
		String countSql="select count(photoGalleryID) from PhotoGallery where photoGalleryState=0";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	
	/**方法名称: photoGalleryadd<br>
	 * 描述：图片库添加
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午2:54:32
	 * @param map
	 * @return
	 */
	public int photoGalleryadd(Map<String,String[]> map){
		PhotoGallery photoGallery=new PhotoGallery();
		photoGallery.setPhotoGalleryName(map.get("photoGalleryName")[0]);
		photoGallery.setPhotoGalleryPath(map.get("photoGalleryPath")[0]);
		photoGallery.setPhotoGalleryState(0);
		photoGallery.setPhotoGallerySort(0);
		int id=insertBackID(photoGallery);
		return id;
	}

	
	/**方法名称: updatePhotoGallery<br>
	 * 描述：图片库修改
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:12:24
	 * @param map
	 * @param operationName
	 * @return
	 */
	public boolean updatePhotoGallery(Map<String,String[]> map,String operationName){
		int photoGalleryID=Integer.parseInt(map.get("photoGalleryID")[0]);
		String photoGalleryName=map.get("photoGalleryName")[0];
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql = "update PhotoGallery set photoGalleryName = '"+photoGalleryName+"',operationTime= '"+ts+"',operationName = '"+operationName+"' where photoGalleryID="+photoGalleryID;
		boolean updatestate=super.execSql(sql);
		return updatestate;
	}
	
	
	
	
	/**方法名称: deletePhotoGallery<br>
	 * 描述：图片库删除
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:23:01
	 * @param photoGalleryID
	 * @param photoGalleryState
	 * @param operationName
	 * @return
	 * @throws SQLException
	 */
	public boolean  deletePhotoGallery(int photoGalleryID,int photoGalleryState,String operationName) throws SQLException{
	    Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql = "update PhotoGallery set photoGalleryState = "+photoGalleryState+",operationTime= '"+ts+"',operationName='"+operationName+"' where photoGalleryID="+photoGalleryID;
		boolean delstate=super.execSql(sql);
		return delstate;
}
}
