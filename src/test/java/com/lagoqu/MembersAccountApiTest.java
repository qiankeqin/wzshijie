package com.lagoqu;

import java.io.IOException;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.spring.test.BaseSpringTest;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.service.AppAccountDetailsService;
import com.lagoqu.worldplay.service.AppAccountsService;
/**描述：用户账户Api测试<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月27日上午11:22:24 <br>
 * E-mail:  <br> 
 */
public class MembersAccountApiTest extends BaseSpringTest{

	@Resource
	AppAccountsService appAccountsService;
	
	@Resource
	AppAccountDetailsService appAccountDetailsService;
	
	/**方法名称: myAccount<br>
	 * 描述：用户账户
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午1:49:05
	 * @throws IOException
	 */
	@Test
	public void myAccount() throws IOException{
		int membersID=2;
		JSONObject backJsonObject=appAccountsService.myAccount(membersID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println(backJsonObject.get("message").toString());
		}
	}
	
	
	/**方法名称: myAccountDetails<br>
	 * 描述：用户账户详情
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午1:49:05
	 * @throws IOException
	 */
	@Test
	public void myAccountDetails() throws IOException{
		int page=1;
		int size=5;
		int membersID=2;
		Pagination<JSONArray> pagination=appAccountDetailsService.myAccountDetails(page,size,membersID);
		System.out.println(pagination.toString());
	}
	
	
	/**方法名称: myApplyWithdraw<br>
	 * 描述：用户申请提现
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午8:32:33
	 * @throws IOException
	 */
	@Test
	public void myApplyWithdraw() throws IOException{
		int membersID=2;
		String username="xxxx";
		int type=0;
		String name="王二小";
		int accont=1000;
		JSONObject backJsonObject=appAccountsService.myApplyWithdraw(membersID,username,type,name,accont);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println(backJsonObject.get("message").toString());
		}
	}
}
