package com.lagoqu.worldplay.service;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.OrderInfo;

@Service
public class AppOrderInfoService extends BaseDao<OrderInfo>{
	
	/**方法名称: CrowdfundReleaseReward<br>
	 * 描述：心愿或足迹打赏创建订单
	 * 作者: 王小欢
	 * 修改日期：2015年11月4日下午1:48:22
	 * @param orderInfo
	 * @return
	 */
	public JSONObject CrowdfundReleaseReward(OrderInfo orderInfo){
		JSONObject backJsonObject=new JSONObject();
		int orderID=super.insertBackID(orderInfo);
		if(orderID!=-1){
			backJsonObject.put("data", orderID);
			backJsonObject.put("state", true);
			return backJsonObject; 
		}else{
			backJsonObject.put("message","创建打赏订单失败");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
}
