package com.lagoqu.worldplay.service;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.OrderInfo;
import com.lagoqu.worldplay.util.EmojiFilter;

@Service
public class OrderInfoService extends BaseDao<OrderInfo>{
	@Resource
	MembersService membersService;
	/**
	 * 方法名称: saveOrder<br>
	 * 描述：微信我要助力
	 * 作者: 邢留杰
	 * 修改日期：2015年6月18日下午4:35:51
	 * @param jObject
	 * @return
	 */
	public int saveOrderWx(JSONObject jObject){
		String openId = jObject.getString("openId");
		JSONObject jb = membersService.findBymembersWxID(openId);
		int membersID = jb.getInt("membersID");
		String orderMemo = EmojiFilter.filterEmoji(jObject.getString("orderMemo").trim());
		jObject.put("orderMemo", orderMemo);
		OrderInfo orderInfo=(OrderInfo) jObject.toBean(jObject, OrderInfo.class);
		orderInfo.setMembersID(membersID);
		orderInfo.setOrderPayType(1);
		int bl=super.insertBackID(orderInfo);
		return bl;
	}
	/**
	 * 方法名称: saveOrder<br>
	 * 描述：我要助力
	 * 作者: 邢留杰
	 * 修改日期：2015年6月18日下午4:35:51
	 * @param jObject
	 * @return
	 */
	public int saveOrder(JSONObject jObject){
		OrderInfo orderInfo=(OrderInfo) jObject.toBean(jObject, OrderInfo.class);
		int bl=super.insertBackID(orderInfo);
		return bl;
	}
	/**
	 * 方法名称: updateOrder<br>
	 * 描述：订单提交支付成功后修改状态
	 * 作者: 邢留杰
	 * 修改日期：2015年6月18日下午4:59:01
	 * @param orderID
	 * @return
	 */
	public boolean updateOrder(int orderID){
		String sql = "update OrderInfo set orderIsOk = 1,orderPayedTime = CURRENT_TIMESTAMP where orderID=? and orderIsOk=0";
		boolean bl = super.execSql(sql,orderID);
		return bl;
	}
}
