package com.lagoqu.worldplay.bms.ajax;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.security.Base64MD5;
//import com.lagoqu.common.security.Base64MD5;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.bms.service.UsersBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.User;
@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/user")
public class UserBmsAjax extends BMSController{

	@Resource
	private UsersBmsService userBmsService;
	
	@ResponseBody
	@RequestMapping(value="list",method = RequestMethod.POST)
	public void findUserPageData() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		String userName=map.get("userName")[0];
		//String isExist=map.get("isExist")[0];
		Pagination<JSONArray> pagination=userBmsService.findUser(page,size,userName);
		returnJSONEasyUISuccess(pagination);
	}
		
	@ResponseBody
	@RequestMapping(value="add",method = RequestMethod.POST)
	public void addUserEntity() throws Exception {					
//		String [] str_userName= userBmsService.findUserName().split(",");		
		Map<String,String[]> map =super.getRequestParamsMap();	
		
		String userName = map.get("userName")[0];
		String password = map.get("password")[0];
		password=Base64MD5.encrypt(password);
		
		String roleName = map.get("roleName")[0];
		String sex = map.get("sex")[0];
		String roleID = map.get("roleID")[0];
		String email = map.get("email")[0];
		String telephone = map.get("telephone")[0];
		String realName = map.get("realName")[0];
		
		User user = new User();		
		user.setUserName(userName);
		user.setPassword(password);
		user.setRoleName(roleName);	
		user.setSex(sex);
		user.setRoleID(roleID);
		user.setEmail(email);
		user.setTelephone(telephone);
		user.setRealName(realName);
		
		userBmsService.addUserEntity(user);
	}
		
	@ResponseBody
	@RequestMapping(value = "edituser", method = RequestMethod.GET)
	public void edituser() throws Exception {		
		Map<String,String[]> map =super.getRequestParamsMap();
		boolean a = userBmsService.updateUser(map);	
		if(a){
			returnSuccessJson("true");
		}else{
			returnFailJson("error");
		}
		
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.PUT)
	public void delete(@RequestParam(value = "ids[]", required = true) int[] ids) 
			throws SQLException, IOException {
		for (int i = 0; i < ids.length; i++) {
			int id = ids[i];
			userBmsService.deleteUser(id);
		}
		returnSuccessJson("true");
	}
}
