package com.lagoqu.worldplay.service;

import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.RandomUtil;
import com.lagoqu.worldplay.entity.PhoneLaunch;
import com.lagoqu.worldplay.util.MemCachedManager;
/**描述：手机启动记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月8日下午3:50:53 <br>
 * E-mail:  <br> 
 */
@Service
public class PhoneLaunchService extends BaseDao<PhoneLaunch>{

	
	
	/**方法名称: insertPhoneLaunch<br>
	 * 描述：手机启动
	 * 应用第一次启动时候，发送到服务端，服务端为该设备注册登记
	 * 作者: 王小欢
	 * 修改日期：2015年6月8日下午3:57:13
	 * @param phoneLaunch
	 * @return
	 */
	public int insertPhoneLaunch(JSONObject jObject){
		PhoneLaunch phoneLaunch=(PhoneLaunch)jObject.toBean(jObject, PhoneLaunch.class);
		int plID=insertBackID(phoneLaunch);
		return plID;
	}
	/**
	 * 方法名称: getCheckCode<br>
	 * 描述：客服获取验证码
	 * 作者: 邢留杰
	 * 修改日期：2015年8月21日下午6:29:22
	 * @param phone
	 * @return
	 */
	public String getCheckCode(String phone){
		String num = RandomUtil.getNum(6);
		MemCachedManager cache = MemCachedManager.getInstance();
		boolean flag = cache.set(phone, num);
		if(flag){
			return num;
		}else{
			return "0";
		}
	}
}
