package com.lagoqu.worldplay.service;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Carousel;
/**描述：轮播图<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月29日上午11:19:48 <br>
 * E-mail:  <br> 
 */
@Service
public class AppCarouselService extends BaseDao<Carousel>{

	/**方法名称: carouselList<br>
	 * 描述：App首页轮播图查询
	 * 作者: 王小欢
	 * 修改日期：2015年10月29日下午1:36:35
	 * @param page
	 * @param size
	 * @return
	 */
	public Pagination<JSONArray> carouselList(int page,int size){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select cs.carouselPath,cs.carouselType,cs.carouselContent from Carousel cs where cs.carouselState=0";
		String countSql="select count(cs.carouselID) from Carousel cs where cs.carouselState=0";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
}
