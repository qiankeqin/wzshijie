package com.lagoqu.worldplay.service;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.constants.Constants;
import com.lagoqu.worldplay.entity.FunCarousel;
@Service
public class AppFunCarouselService extends BaseDao<FunCarousel>{

	
	/**方法名称: funCarouselList<br>
	 * 描述：哪好玩轮播图查询
	 * 作者: 王小欢
	 * 修改日期：2015年12月8日下午6:22:11
	 * @param page
	 * @param size
	 * @return
	 */
	public Pagination<JSONArray> funCarouselList(int page,int size){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select funCarouselId,funID,recommendImg,description , CONCAT('"+Constants.fun_link+"',funID) as link from FunCarousel";
		String countSql="select count(funCarouselId) from FunCarousel";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
}
