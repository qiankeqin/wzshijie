package com.lagoqu.worldplay.bms.page;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.common.controller.BMSController;
/**描述：用户管理<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年8月6日下午1:17:54 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/bms/members")
public class MembersBmsPage extends BMSController{

	
	@RequestMapping("list")
	public String membersList() throws Exception{
		return "bms/members/membersList.html";
	}
}
