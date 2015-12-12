package com.lagoqu.worldplay.service;

import java.sql.SQLException;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.AccountDetails;
import com.lagoqu.worldplay.entity.Accounts;
import com.lagoqu.worldplay.entity.WithdrawDetails;

/**描述：我的账户<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年7月6日下午3:36:42 <br>
 * E-mail:  <br> 
 */
@Service
public class AccountsService extends BaseDao<Accounts>{

	
	@Resource
	AccountDetailsService accountDetailsService;
	@Resource
	WithdrawDetailsService withdrawDetailsService;
	/**方法名称: insertAccounts<br>
	 * 描述：创建用户账户
	 * 作者: 王小欢
	 * 修改日期：2015年7月6日下午3:40:09
	 * @param membersID
	 * @return
	 */
	public int insertAccounts(int membersID){
		Accounts accounts=new Accounts();
		accounts.setMembersID(membersID);
		int accountID=insertBackID(accounts);
		return accountID;
	} 
	
	
	/**方法名称: updateAccountsByMembersID<br>
	 * 描述：提现 更改账户总额变化
	 * 作者: 王小欢
	 * 修改日期：2015年7月6日下午4:35:13
	 * @param wDetailsAccount
	 * @param membersID
	 */
	public void updateAccountsByMembersID(int wDetailsAccount,int membersID){
		String sql = "update Accounts set accountBalance=accountBalance-?,accountAvailable=accountAvailable-?,accountWithdraw=accountWithdraw+? where membersID=?";
		super.execSql(sql,wDetailsAccount,wDetailsAccount,wDetailsAccount,membersID);
	}
	
	
	
	
	/**方法名称: findByIdAccounts<br>
	 * 描述：个人账户信息
	 * 作者: 王小欢
	 * 修改日期：2015年7月6日下午7:03:58
	 * @param membersID
	 * @return
	 * @throws SQLException
	 */
	public JSONObject findByIdAccounts(int membersID) throws SQLException{
		String sql="select accountBalance as  balance,accountAvailable as available,accountGet as 'get',accountWithdraw as withdraw from Accounts where membersID=?";
		JSONObject js = super.findJson(sql, null,membersID);
		return js;
	}
	
	
	/**方法名称: withdraw<br>
	 * 描述：用户提交提现
	 * 提现流程
	 * 用户提交提现---- 账户明细+提现记录+更改账户总额变化（余额+可提现金额+提现金额）
	 * 作者: 王小欢
	 * 修改日期：2015年7月7日上午10:46:39
	 * @param membersID
	 * @param username
	 * @param type
	 * @param name
	 * @param accont
	 * @return
	 */
	public boolean withdraw(int membersID,String username,int type,String name,int accont){
		String sql1="select accountAvailable from  Accounts where membersID=?";
		JSONObject js = super.findJson(sql1, null,membersID);
		String sql2="select idCardName from IdCard where membersID=?";
		JSONObject js2 = super.findJson(sql2, null,membersID);
		int accountAvailable=js.getInt("accountAvailable");
		String idCardName=js2.getString("idCardName");
		if(name.equals(idCardName)==false){
			return false;
		}
		else if(accont>accountAvailable){
			return false;
		}else if(accont<=1){
			return false;
		}else{
			//增加 账户明细
			AccountDetails accountDetails=new AccountDetails();
			accountDetails.setMembersID(membersID);
			accountDetails.setDetailsAccount(accont);
			accountDetails.setDetailsType("2");
			accountDetails.setDetailsMembersId(membersID);
			accountDetailsService.AccountDetailsInsert(accountDetails);
			//增加 提现记录
			WithdrawDetails withdrawDetails=new WithdrawDetails();
			withdrawDetails.setMembersID(membersID);
			withdrawDetails.setWDetailsAccount(accont);
			withdrawDetails.setWDetailsUserName(username);
			withdrawDetails.setWDetailsType(type);
			withdrawDetails.setWDetailsName(name);
			withdrawDetails.setWDetailsStatus(1);
			withdrawDetailsService.WithdrawDetailsInsert(withdrawDetails);
			String sql = "update Accounts set accountBalance=accountBalance-?,accountAvailable=accountAvailable-?,accountWithdraw=accountWithdraw+? where membersID=?";
			super.execSql(sql,accont,accont,accont,membersID);
			return true;
		}
		
		
	}
	/**
	 * 方法名称: updateAccountsAfterOrderDone<br>
	 * 描述：我要助力支付完成后修改用户账户信息
	 * 作者: 邢留杰
	 * 修改日期：2015年7月7日上午11:08:00
	 * @param account
	 * @param membersID
	 */
	public void updateAccountsAfterOrderDone(int account,int membersID){
			String sql = "update Accounts set accountBalance=accountBalance+?,accountGet=accountGet+? where membersID=?";
			super.execSql(sql,account,account,membersID);
	}
}
