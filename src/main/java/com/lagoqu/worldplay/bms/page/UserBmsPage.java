package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.bms.service.RoleBmsService;
import com.lagoqu.worldplay.bms.service.UsersBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.User;
@Controller
@Scope("prototype")
@RequestMapping("/bms/user")
public class UserBmsPage extends BMSController{
	
	@Resource
	private RoleBmsService roleBmsService;
	
	@Resource 
	private UsersBmsService usersBmsService;
	
	@RequestMapping("user")
	public String showUser() throws Exception{
		return "bms/user/user.html";
	}
	
	@RequestMapping("adduser")
	public String addUser() throws Exception{
		String sql = "select * from Role";
		JSONArray jsonArray = roleBmsService.findJsonArray(sql, null);
		super.setRequestAttibute("roles", jsonArray);
		return "bms/user/user_add.html";
	}
	
	@RequestMapping("{id}/edituser")
	public String editUser(@PathVariable("id") int id) throws Exception{
		User user =usersBmsService.get(id);
		super.setRequestAttibute("users", user);		
		String sql = "select * from Role ";		
		JSONArray jsonArray = roleBmsService.findJsonArray(sql, null);
		
		String[] user_role_id = user.getRoleID().split(",");
		for(int i=0;i<jsonArray.size();i++){
			for(int j=0;j<user_role_id.length;j++){
				if(jsonArray.getJSONObject(i).getInt("roleID") == Long.parseLong(user_role_id[j])){
					jsonArray.getJSONObject(i).put("isCheck", 1);
					break;
				}
			}
		}
		
		super.setRequestAttibute("roles", jsonArray);
		return "bms/user/user_edit.html";
	}
}
