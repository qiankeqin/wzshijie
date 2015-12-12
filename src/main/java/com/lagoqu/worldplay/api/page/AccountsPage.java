package com.lagoqu.worldplay.api.page;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.constants.Constants;
import com.lagoqu.worldplay.entity.Members;
import com.lagoqu.worldplay.service.MembersService;
import com.mysql.fabric.xmlrpc.base.Member;
/**描述：个人账户<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年7月29日上午10:31:34 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/accountsPage")
public class AccountsPage extends APIController{

	@Resource
	MembersService membersService;
	/**方法名称: toMy<br>
	 * 描述：个人账户
	 * 作者: 王小欢
	 * 修改日期：2015年7月29日上午10:31:37
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "myAccounts", method = RequestMethod.GET)
	public String toMy() throws Exception{
		if(!super.isMemberLogin()){
			return "redirect:" + Constants.WEIXIN_ACCOUNT_USERINFO; 
		}
		Members member=membersService.get(super.getMember().getMembersID());
		super.setRequestAttibute("crrrentMember", member);
		return "my/my_accounts.html";
	}
}
