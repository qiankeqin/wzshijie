package com.lagoqu.worldplay.entity;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**描述：我的账户<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年7月6日下午3:32:05 <br>
 * E-mail:  <br> 
 */
@ATable(name = "Accounts", pkname = "accountID")
public class Accounts extends BaseEntity{

	private int accountID;         //账户编号
	private int membersID;         //会员编号
	private int accountBalance;    //账户余额
	private int accountAvailable;  //可提现金额
	private int accountWithdraw;   //提现金额
	private int accountPay;        //支出金额
	private int accountGet;        //收到金额
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public int getMembersID() {
		return membersID;
	}
	public void setMembersID(int membersID) {
		this.membersID = membersID;
	}
	public int getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}
	public int getAccountAvailable() {
		return accountAvailable;
	}
	public void setAccountAvailable(int accountAvailable) {
		this.accountAvailable = accountAvailable;
	}
	public int getAccountWithdraw() {
		return accountWithdraw;
	}
	public void setAccountWithdraw(int accountWithdraw) {
		this.accountWithdraw = accountWithdraw;
	}
	public int getAccountPay() {
		return accountPay;
	}
	public void setAccountPay(int accountPay) {
		this.accountPay = accountPay;
	}
	public int getAccountGet() {
		return accountGet;
	}
	public void setAccountGet(int accountGet) {
		this.accountGet = accountGet;
	}
	
	
}
