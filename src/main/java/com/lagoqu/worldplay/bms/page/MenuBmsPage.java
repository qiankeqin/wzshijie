package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.bms.service.MenuBmsService;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.entity.Menu;
@Controller
@Scope("prototype")
@RequestMapping("/bms/menu")
public class MenuBmsPage extends APIController{
	
	@Resource
	private MenuBmsService menuBmsService;
	
	@RequestMapping("menu")
	public String showMenu() throws Exception{
		return "bms/menu/menu.html";
	}
	
	@RequestMapping("addmenu")
	public String addMenu() throws Exception{
		return "bms/menu/menu_add.html";
	}
	
	@RequestMapping("{id}/editmenu")
	public String editUser(@PathVariable("id") int id) throws Exception{
		Menu menu =menuBmsService.get(id);
	/*	if(menu.getIsFinal()==1){
			menu.
		}*/
		super.setRequestAttibute("menus", menu);
		return "bms/menu/menu_edit.html";
	}
}
