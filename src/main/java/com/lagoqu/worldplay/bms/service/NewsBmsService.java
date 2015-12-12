package com.lagoqu.worldplay.bms.service;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.exception.DBException;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.News;

@Service
public class NewsBmsService extends BaseDao<News>{
	/**
	 * 方法名称: list<br>
	 * 描述：新闻列表
	 * 作者: 邢留杰
	 * 修改日期：2015年12月7日下午3:51:56
	 * @param page
	 * @param rows
	 * @param newsTitle
	 * @return
	 * @throws Exception
	 */
	public Pagination<JSONArray> list(String page,String rows,String newsTitle) throws Exception {
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(Integer.parseInt(page));
		pagination.setPageSize(Integer.parseInt(rows));
		String sql  = "select newsId,id,funTypeID,newsWriter,newsTitle,newsImg,newsDetailImg,newsSource,newsBrief,newsContent,newsSort,areaID,provicesID,destinationID,destinationName,areaType,apdID,createTime from news";
		String countSql="select count(newsId) from news";
		if(StringUtils.isNotBlank(newsTitle)){
			sql+=" where newsTitle like '%" + newsTitle + "%'";
			countSql+=" where newsTitle like '%" + newsTitle + "%'";
		}
		super.findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	/**
	 * 方法名称: updateNews<br>
	 * 描述：新闻修改
	 * 作者: 邢留杰
	 * 修改日期：2015年12月7日下午4:33:49
	 * @param news
	 * @param jso
	 * @throws Exception
	 */
	public void updateNews(News news) throws Exception{
		News news2=get(news.getNewsId());
		news2.setNewsWriter(news.getNewsWriter());
		news2.setNewsTitle(news.getNewsTitle());
		if(news.getNewsImg()!=null){
			news2.setNewsImg(news.getNewsImg());
		}
		news2.setNewsBrief(news.getNewsBrief());
		news2.setNewsContent(news.getNewsContent());
		String sql = "Update news Set newsWriter=?,newsImg=?,newsBrief=?,newsTitle=?,newsContent=? where newsId=?";
		super.execSql(sql, news.getNewsWriter(),news.getNewsImg(),news.getNewsBrief(),news.getNewsTitle(),news.getNewsContent(),news.getNewsId());
	}
	/**
	 * 方法名称: interfaceUpdateNews<br>
	 * 描述：编辑接口
	 * 作者: 邢留杰
	 * 修改日期：2015年12月10日下午1:51:28
	 * @param news
	 * @throws Exception
	 */
	public void interfaceUpdateNews(News news) throws Exception{
		String sql = "select newsId,id,funTypeID,newsWriter,newsTitle,newsImg,newsDetailImg,newsSource,newsBrief,newsContent,newsSort,areaID,provicesID,destinationID,destinationName,areaType,apdID,newsTimes,createTime from news where id=?";
		JSONObject jo = super.findJson(sql, null, news.getId());
		if(jo!=null){
			news.setNewsId(jo.getInt("newsId"));
			super.update(news);
		}
	}
	/**
	 * 方法名称: interfaceDelete<br>
	 * 描述：删除
	 * 作者: 邢留杰
	 * 修改日期：2015年12月10日下午3:29:24
	 * @param id
	 * @throws Exception
	 */
	public void interfaceDelete(long id) throws Exception{
		String sql = "delete from news where id=?";
		super.execSql(sql,id);
	}
}
