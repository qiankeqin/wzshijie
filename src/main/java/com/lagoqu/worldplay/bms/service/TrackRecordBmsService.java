package com.lagoqu.worldplay.bms.service;

import java.sql.Timestamp;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.TrackRecord;
@Service
public class TrackRecordBmsService extends BaseDao<TrackRecord>{

	
	
	/**方法名称: TrackRecordlist<br>
	 * 描述：足迹详情查询
	 * 作者: 王小欢
	 * 修改日期：2015年11月17日下午2:19:34
	 * @param page
	 * @param size
	 * @param crowdfundID
	 * @return
	 */
	public Pagination<JSONArray> TrackRecordlist(int page,int size,int crowdfundID){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select * from TrackRecord tr where tr.crowdfundID=?";
		String countSql="select count(tr.trackRecordID) from TrackRecord tr where tr.crowdfundID=?";
		sql+= " order by tr.createTime desc ";
		findListJsonByPages(pagination,sql,countSql,crowdfundID);
		return pagination;
	}
	
	

	/**方法名称: saveTrackRecord<br>
	 * 描述：后台发布足迹增加
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日上午9:38:26
	 * @param map
	 * @return
	 */
	public int saveTrackRecord(Map<String,String[]> map){
		TrackRecord trackRecord=new TrackRecord();
		trackRecord.setCrowdfundID(Integer.parseInt(map.get("crowdfundID")[0]));
		trackRecord.setTrackRecordTitle(map.get("trackRecordTitle")[0]);
		trackRecord.setTrackRecordImage(map.get("trackRecordImage")[0]);
		trackRecord.setTrackRecordLocation(map.get("trackRecordLocation")[0]);
		String trackRecordCreateTime=map.get("trackRecordCreateTime")[0];
			Timestamp crts = new Timestamp(System.currentTimeMillis());
			crts = Timestamp.valueOf(trackRecordCreateTime);
			trackRecord.setTrackRecordCreateTime(crts);
		int trackRecordID=insertBackID(trackRecord);
		return trackRecordID;
	}

}
