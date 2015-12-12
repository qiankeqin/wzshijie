package com.lagoqu.worldplay.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.constants.Constants;
import com.lagoqu.worldplay.entity.News;
@Service
public class AppNewsService extends BaseDao<News>{

	
	
	/**方法名称: newsList<br>
	 * 描述：哪好玩列表
	 * 作者: 王小欢
	 * 修改日期：2015年12月8日下午6:25:20
	 * @param page
	 * @param size
	 * @return
	 */
	public Pagination<JSONArray> newsList(int page,int size){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select newsId,id,funTypeID,newsWriter,newsTitle,newsImg,newsDetailImg,newsSource,newsBrief,newsContent,newsSort,newsTimes,createTime,CONCAT('"+Constants.fun_link+"',id) as link from News";
		String countSql="select count(newsId) from News";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	
	
	/**方法名称: newsDetail<br>
	 * 描述：查看哪好玩详情
	 * 作者: 王小欢
	 * 修改日期：2015年12月9日上午9:55:15
	 * @param newsId
	 * @return
	 */
	public JSONObject newsDetail(int newsId){
		JSONObject backJsonObject=new JSONObject();
		//修改访问次数
		String sqlupdate = "update News set newsTimes=newsTimes+1 where newsId=?";
		super.execSql(sqlupdate,newsId);	
		String sqlselect="select * from News ns where ns.newsId=?";
		JSONObject jsonObject = findJson(sqlselect,null,newsId);
		if(jsonObject!=null){
			backJsonObject.put("data",jsonObject);
			backJsonObject.put("state", true);
			return backJsonObject;
		}else {
			backJsonObject.put("message","获取哪好玩详情信息失败，请重试");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
		
	}
}
