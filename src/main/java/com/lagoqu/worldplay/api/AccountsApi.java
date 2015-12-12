package com.lagoqu.worldplay.api;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;






import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.constants.Constants;
import com.lagoqu.worldplay.service.AccountsService;
import com.lagoqu.worldplay.util.YunpianResult;
import com.lagoqu.worldplay.util.YunpianSms;
@Controller
@Scope("prototype")
@RequestMapping("/account")
public class AccountsApi extends APIController{

	
	@Resource
	AccountsService accountsService;
	
	/**方法名称: findByIdAccounts<br>
	 * 描述：个人账户信息
	 * 作者: 王小欢
	 * 修改日期：2015年7月6日下午7:04:06
	 * @throws SQLException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="my",method = RequestMethod.POST)
	public void findByIdAccounts() throws SQLException, IOException{
		JSONObject jObject=super.getRequestJsonParams();
		int membersID=jObject.getInt("membersID");
		JSONObject ms=accountsService.findByIdAccounts(membersID);
		JSONObject jb=new JSONObject();
		jb.put("ms", ms);
        if(ms!=null){
        	returnSuccessJson(jb.toString());
        }else {
        	returnSuccessJson("获取个人账户信息失败，请重试");
		}
	}
	
	
	/**方法名称: withdraw<br>
	 * 描述：用户提交提现
	 * 作者: 王小欢
	 * 修改日期：2015年7月7日上午10:32:27
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="withdraw",method = RequestMethod.POST)
	public void withdraw() throws IOException{
		JSONObject jObject=super.getRequestJsonParams();
		int membersID=jObject.getInt("membersID");
		String username=jObject.getString("username");
		int type=jObject.getInt("type");
		String name=jObject.getString("name");
		int accont=jObject.getInt("accont");
		boolean withdrawState=accountsService.withdraw(membersID,username,type,name,accont);
		if(withdrawState==true){
			String tpl_value = "#membersID#="+membersID+"&#accont#="+(double)accont/100+"&#username#="+username;
			 YunpianResult yr = YunpianSms.tplSendSms(Constants.sms_tpl_withdraw,tpl_value, Constants.tpl_withdraw_Phone);
        	returnSuccessJson("true");
        }else {
        	returnFailJson("支付宝账户 与身份证信息不匹配");
		}
	}
}
