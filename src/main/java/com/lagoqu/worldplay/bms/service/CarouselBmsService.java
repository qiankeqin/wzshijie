package com.lagoqu.worldplay.bms.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Carousel;

@Service
public class CarouselBmsService  extends BaseDao<Carousel>{

	
	/**方法名称: carouselList<br>
	 * 描述：轮播图查询
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日上午11:15:50
	 * @param page
	 * @param size
	 * @param carouselState
	 * @return
	 */
	public Pagination<JSONArray> carouselList(int page,int size,int carouselState){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select * from Carousel where carouselState=? order by createTime desc";
		String countSql="select count(carouselID) from Carousel where carouselState=?";
		findListJsonByPages(pagination,sql,countSql,carouselState);
		return pagination;
	}
	
	
	
	/**方法名称: carouseladd<br>
	 * 描述：轮播图添加
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午2:54:32
	 * @param map
	 * @return
	 */
	public int carouseladd(Map<String,String[]> map){
		Carousel carousel=new Carousel();
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		startTime = Timestamp.valueOf(map.get("startTime")[0]);
		Timestamp endTime = new Timestamp(System.currentTimeMillis());
		endTime = Timestamp.valueOf(map.get("endTime")[0]);
		carousel.setCarouselName(map.get("carouselName")[0]);
		carousel.setCarouselPosition(map.get("carouselPosition")[0]);
		carousel.setCarouselPath(map.get("carouselPath")[0]);
		carousel.setCarouselType(Integer.parseInt(map.get("carouselType")[0]));
		carousel.setCarouselContent(map.get("carouselContent")[0]);
		carousel.setCarouselState(0);
		carousel.setCarouselSort(0);
		carousel.setStartTime(startTime);
		carousel.setEndTime(endTime);
		int id=insertBackID(carousel);
		return id;
	}

	
	/**方法名称: updateCarousel<br>
	 * 描述：轮播图修改
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:12:24
	 * @param map
	 * @param operationName
	 * @return
	 */
	public boolean updateCarousel(Map<String,String[]> map,String operationName){
		int carouselID=Integer.parseInt(map.get("carouselID")[0]);
		String carouselName=map.get("carouselName")[0];
		String carouselPosition=map.get("carouselPosition")[0];
		String carouselPath=map.get("carouselPath")[0];
		int carouselType=Integer.parseInt(map.get("carouselType")[0]);
		String carouselContent=map.get("carouselContent")[0];
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		startTime = Timestamp.valueOf(map.get("startTime")[0]);
		Timestamp endTime = new Timestamp(System.currentTimeMillis());
		endTime = Timestamp.valueOf(map.get("endTime")[0]);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql = "update Carousel set carouselName = '"+carouselName+"',carouselPosition = '"+carouselPosition+"',carouselPath = '"+carouselPath+"',carouselType ="+carouselType+",carouselContent= '"+carouselContent+"',startTime = '"+startTime+"',endTime = '"+endTime+"',operationTime= '"+ts+"',operationName = '"+operationName+"' where carouselID="+carouselID;
		boolean updatestate=super.execSql(sql);
		return updatestate;
	}
	
	
	
	
	/**方法名称: deleteCarousel<br>
	 * 描述：轮播图删除
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:23:01
	 * @param carouselID
	 * @param carouselState
	 * @param operationName
	 * @return
	 * @throws SQLException
	 */
	public boolean  deleteCarousel(int carouselID,int carouselState,String operationName) throws SQLException{
	    Timestamp ts = new Timestamp(System.currentTimeMillis());
		String sql = "update Carousel set carouselState = "+carouselState+",operationTime= '"+ts+"',operationName='"+operationName+"' where carouselID="+carouselID;
		boolean delstate=super.execSql(sql);
		return delstate;
}
}
