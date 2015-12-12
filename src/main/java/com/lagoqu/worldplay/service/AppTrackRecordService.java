package com.lagoqu.worldplay.service;

import java.sql.Timestamp;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.TrackRecord;
/**描述：足迹记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月29日上午11:23:13 <br>
 * E-mail:  <br> 
 */
@Service
public class AppTrackRecordService extends BaseDao<TrackRecord>{

	
	
	/**方法名称: SaveTrackRecord<br>
	 * 描述：足迹详情添加
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日下午2:22:17
	 * @param jo
	 * @param crowdfundID
	 * @return
	 */
	public int SaveTrackRecord(JSONObject jo,int crowdfundID){
		TrackRecord trackRecord=new TrackRecord();
		trackRecord.setCrowdfundID(crowdfundID);
		trackRecord.setTrackRecordTitle(jo.getString("trackRecordTitle"));
		trackRecord.setTrackRecordImage(jo.getString("trackRecordImage"));
		trackRecord.setTrackRecordLocation(jo.getString("trackRecordLocation"));
		Timestamp trackRecordCreateTime = new Timestamp(System.currentTimeMillis());
		trackRecordCreateTime = Timestamp.valueOf(jo.getString("trackRecordCreateTime"));
		trackRecord.setTrackRecordCreateTime(trackRecordCreateTime);
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		trackRecord.setCreateTime(createTime);
		int trackRecordID=insertBackID(trackRecord);
		return trackRecordID;
	}
}
