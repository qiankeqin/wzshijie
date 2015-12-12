package com.lagoqu.worldplay.api.app;

import java.io.IOException;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.service.AppAccountDetailsService;
import com.lagoqu.worldplay.service.AppAccountsService;
/**描述：用户账户Api<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月27日上午11:22:24 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/MembersAccount")
public class MembersAccountApi extends APIController{

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
	@ResponseBody
	@RequestMapping(value="myAccount",method = RequestMethod.POST)
	public void myAccount() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int membersID=receivejJsonObject.getInt("membersID");
		JSONObject backJsonObject=appAccountsService.myAccount(membersID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	/**方法名称: myAccountDetails<br>
	 * 描述：用户账户详情
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午1:49:05
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="myAccountDetails",method = RequestMethod.POST)
	public void myAccountDetails() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int page=receivejJsonObject.getInt("page");
		int size=receivejJsonObject.getInt("size");
		int membersID=receivejJsonObject.getInt("membersID");
		Pagination<JSONArray> pagination=appAccountDetailsService.myAccountDetails(page,size,membersID);
		returnSuccessJson(pagination.toString());
	}
	
	
	
	/**方法名称: myApplyWithdraw<br>
	 * 描述：用户申请提现
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午1:49:05
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="myApplyWithdraw",method = RequestMethod.POST)
	public void myApplyWithdraw() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int membersID=receivejJsonObject.getInt("membersID");
		String username=receivejJsonObject.getString("username");
		int type=receivejJsonObject.getInt("type");
		String name=receivejJsonObject.getString("name");
		int accont=receivejJsonObject.getInt("accont");
		JSONObject backJsonObject=appAccountsService.myApplyWithdraw(membersID,username,type,name,accont);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
}
