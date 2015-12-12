package com.lagoqu.worldplay.bms.service;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.FunCarousel;



@Service
public class FunCarouselBmsService extends BaseDao<FunCarousel>{
	/**
	 * 方法名称: FunCarouselList<br>
	 * 描述：
	 * 作者: 邢留杰
	 * 修改日期：2015年12月8日下午2:29:28
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	public Pagination<FunCarousel> FunCarouselList(String page ,String rows) throws Exception {
		Pagination<FunCarousel> p = new Pagination<FunCarousel>(Integer.parseInt(page),Integer.parseInt(rows));
		String sql = "select funCarouselId,funID,recommendImg,description,createTime from FunCarousel order by createTime desc";
		String countSql = "select count(funCarouselId) from FunCarousel order by createTime desc";
		super.findListByPages(p, sql, countSql);
		return p;
	}
}
