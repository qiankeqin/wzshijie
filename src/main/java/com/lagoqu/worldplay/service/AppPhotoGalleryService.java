package com.lagoqu.worldplay.service;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.PhotoGallery;
/**描述：图片库<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月29日上午11:21:29 <br>
 * E-mail:  <br> 
 */
@Service
public class AppPhotoGalleryService extends BaseDao<PhotoGallery>{

	
	/**方法名称: PhotoGalleryList<br>
	 * 描述：图片库列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月30日下午2:52:16
	 * @param page
	 * @param size
	 * @return
	 */
	public Pagination<JSONArray> PhotoGalleryList(int page,int size){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select * from PhotoGallery where photoGalleryState=0 order by createTime desc";
		String countSql="select count(photoGalleryID) from PhotoGallery where photoGalleryState=0 ";
		super.findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
}
