package com.lagoqu.worldplay.service;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.ReportRecord;
/**描述：举报记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月29日上午11:22:16 <br>
 * E-mail:  <br> 
 */
@Service
public class AppReportRecordService extends BaseDao<ReportRecord>{

	
	/**方法名称: CrowdfundReleaseReport<br>
	 * 描述：心愿或足迹举报
	 * 作者: 王小欢
	 * 修改日期：2015年11月3日下午7:57:18
	 * @param reportRecord
	 * @return
	 */
	public JSONObject CrowdfundReleaseReport(ReportRecord reportRecord){
		JSONObject backJsonObject=new JSONObject();
		int reportRecordID=super.insertBackID(reportRecord);
		if(reportRecordID!=-1){
			backJsonObject.put("data",reportRecordID);
			backJsonObject.put("state", true);
			return backJsonObject;
		}else {
			backJsonObject.put("message","举报信息失败");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
}
