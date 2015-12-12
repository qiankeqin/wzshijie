package com.lagoqu.worldplay.service;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.WithdrawDetails;

/**描述：提现记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月28日下午4:04:57 <br>
 * E-mail:  <br> 
 */
@Service
public class AppWithdrawDetailsService extends BaseDao<WithdrawDetails>{

	
	/**方法名称: WithdrawDetailsInsert<br>
	 * 描述：增加提现记录
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午4:10:41
	 * @param wDetails
	 * @return
	 */
	public int WithdrawDetailsInsert(WithdrawDetails wDetails){
		WithdrawDetails withdrawDetails=new WithdrawDetails();
		withdrawDetails.setMembersID(wDetails.getMembersID());
		withdrawDetails.setWDetailsAccount(wDetails.getWDetailsAccount());
		withdrawDetails.setWDetailsUserName(wDetails.getWDetailsUserName());
		withdrawDetails.setWDetailsType(wDetails.getWDetailsType());
		withdrawDetails.setWDetailsName(wDetails.getWDetailsName());
		withdrawDetails.setWDetailsStatus(wDetails.getWDetailsStatus());
		int wDetailsID=insertBackID(withdrawDetails);
		return wDetailsID;
	}
}
