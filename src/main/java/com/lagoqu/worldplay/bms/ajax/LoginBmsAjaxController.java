package com.lagoqu.worldplay.bms.ajax;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.worldplay.bms.service.UsersBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;


@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms")
public class LoginBmsAjaxController extends BMSController {
	@Resource
	UsersBmsService usersBmsService;

	@ResponseBody
	@RequestMapping(value="/userlogin",method = RequestMethod.POST)
	public void Bmslogin() throws Exception {		
		
		String userName=getRequestParameter("userName");
		String password=getRequestParameter("password");
		JSONObject jso=usersBmsService.queryUsersLogin(userName, password);	
		if(jso!=null){
			super.getSession().setAttribute("session_user", jso);
			saveUser(jso.getString("userName"));
			returnSuccessJson("ok");
		}else {
			returnFailJson("用户名或者密码不正确");
		}
	}
}
