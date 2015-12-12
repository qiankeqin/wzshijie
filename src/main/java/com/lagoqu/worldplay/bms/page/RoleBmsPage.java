package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.bms.service.RoleBmsService;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.entity.Role;

@Controller
@Scope("prototype")
@RequestMapping("/bms/role")
public class RoleBmsPage extends APIController{
	
	@Resource
	private RoleBmsService roleBmsService;
	
	@RequestMapping("role")
	public String showRole() throws Exception{
		return "bms/role/role.html";
	}
	
	@RequestMapping("addrole")
	public String addRole() throws Exception{
		
		String sql = "select * from Menu Where parentId = 0";
		JSONArray jsonArray = roleBmsService.findJsonArray(sql, null);
		super.setRequestAttibute("menus", jsonArray);
		return "bms/role/role_add.html";
	}
	
	@RequestMapping("{id}/editrole")
	public String editRole(@PathVariable("id") int id) throws Exception{
		Role role = roleBmsService.get(id);
		super.setRequestAttibute("roles", role);
		return "bms/role/role_edit.html";
	}
}
