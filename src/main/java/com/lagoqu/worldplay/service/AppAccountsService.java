package com.lagoqu.worldplay.service;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.AccountDetails;
import com.lagoqu.worldplay.entity.Accounts;
import com.lagoqu.worldplay.entity.WithdrawDetails;
/**描述：个人账户<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月27日下午3:46:41 <br>
 * E-mail:  <br> 
 */
@Service
public class AppAccountsService extends BaseDao<Accounts>{

	@Resource
	AppAccountDetailsService appAccountDetailsService;
	
	@Resource
	AppWithdrawDetailsService appWithdrawDetailsService;
	
	
	/**方法名称: myAccount<br>
	 * 描述：用户账户
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午8:11:03
	 * @param membersID
	 * @return
	 */
	public JSONObject myAccount(int membersID){
		JSONObject backJsonObject=new JSONObject();
		String sql="select accountBalance as  balance,accountAvailable as available,accountGet as 'get',accountWithdraw as withdraw from Accounts where membersID=?";
		JSONObject js = super.findJson(sql, null,membersID);
		if(js!=null){
			backJsonObject.put("data",js);
			backJsonObject.put("state", true);
			return backJsonObject;
		}else {
			backJsonObject.put("message","获取账户信息失败，请重试");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
	
	
	
	
	
	/**方法名称: myAccount<br>
	 * 描述：用户申请提现
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午8:11:03
	 * @param membersID
	 * @return
	 */
	public JSONObject myApplyWithdraw(int membersID,String username,int type,String name,int accont){
		JSONObject backJsonObject=new JSONObject();
		String sql1="select accountAvailable from Accounts where membersID=?";
		JSONObject js = super.findJson(sql1, null,membersID);
		String sql2="select idCardName from IdCard where membersID=?";
		JSONObject js2 = super.findJson(sql2, null,membersID);
		int accountAvailable=js.getInt("accountAvailable");
		String idCardName=js2.getString("idCardName");
		if(name.equals(idCardName)==false){
			backJsonObject.put("message","支付宝账户与身份证信息不匹配");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}else if(accont>accountAvailable){
			backJsonObject.put("message","提现金额大于可提现金额");
			backJsonObject.put("state", false);
			return backJsonObject;
		}else if(accont<1000){
			backJsonObject.put("message","转出金额最少为10元");
			backJsonObject.put("state", false);
			return backJsonObject;
		}else{
			//增加 账户明细
			AccountDetails accountDetails=new AccountDetails();
			accountDetails.setMembersID(membersID);
			accountDetails.setDetailsAccount(accont);
			accountDetails.setDetailsType("2");
			accountDetails.setDetailsMembersId(membersID);
			appAccountDetailsService.AccountDetailsInsert(accountDetails);
			//增加 提现记录
			WithdrawDetails withdrawDetails=new WithdrawDetails();
			withdrawDetails.setMembersID(membersID);
			withdrawDetails.setWDetailsAccount(accont);
			withdrawDetails.setWDetailsUserName(username);
			withdrawDetails.setWDetailsType(type);
			withdrawDetails.setWDetailsName(name);
			withdrawDetails.setWDetailsStatus(1);
			appWithdrawDetailsService.WithdrawDetailsInsert(withdrawDetails);
			String sql = "update Accounts set accountBalance=accountBalance-?,accountAvailable=accountAvailable-?,accountWithdraw=accountWithdraw+? where membersID=?";
			super.execSql(sql,accont,accont,accont,membersID);
			backJsonObject.put("data",withdrawDetails);
			backJsonObject.put("state", true);
			return backJsonObject;
		}
	}
	
	
	/**方法名称: insertAccounts<br>
	 * 描述：创建用户账户
	 * 作者: 王小欢
	 * 修改日期：2015年10月27日下午3:40:09
	 * @param membersID
	 * @return
	 */
	public int insertAccounts(int membersID){
		Accounts accounts=new Accounts();
		accounts.setMembersID(membersID);
		int accountID=insertBackID(accounts);
		return accountID;
	}
}
