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

import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.bms.service.MenuBmsService;
import com.lagoqu.worldplay.bms.service.RoleBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.Role;

/**描述：<br>
 * 作者：杨天明 <br>
 * 修改日期：2015年9月16日下午2:58:40 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/role")
public class RoleBmsAjax extends BMSController{
	
	@Resource
	private RoleBmsService roleBmsService;
	
	@Resource
	private MenuBmsService menuBmsService;
	
	@ResponseBody
	@RequestMapping(value="add",method = RequestMethod.POST)	
	public void addRoleEntity() throws Exception {
		String str ="";
		String str_name="";
		
		Map<String,String[]> map =super.getRequestParamsMap();			
		String roleName = map.get("roleName")[0];
		String description = map.get("description")[0];
		for(int i=0 ;i<map.get("menuID").length ;i++){
			 str += map.get("menuID")[i]+",";					 						
		}
		String menuID = str.substring(0,str.length()-1);
		String []arr = menuID.split(",");
		for(int a=0 ;a<arr.length ;a++){
			str_name +=menuBmsService.findByID(Integer.parseInt(arr[a]))+",";
		}
		String menuName = str_name.substring(0,str_name.length()-1);	
		Role role = new Role();		
		role.setRoleName(roleName);
		role.setMenuID(menuID);
		role.setMenuName(menuName);		
		role.setDescription(description);
		roleBmsService.addRoleEntity(role);
	}
	
	@ResponseBody
	@RequestMapping(value="list",method = RequestMethod.POST)
	public void findUserPageData() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);		
		Pagination<JSONArray> pagination=roleBmsService.findRoleDetails(page,size,"1");
		returnJSONEasyUISuccess(pagination);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "editrole", method = RequestMethod.POST)
	public void edituser() throws Exception {		
		Map<String,String[]> map =super.getRequestParamsMap();
		boolean a = roleBmsService.updateRole(map);	
		if(a){
			returnSuccessJson("true");
		}else{
			returnFailJson("error");
		}
		
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestParam(value = "ids[]", required = true) int[] ids) 
			throws SQLException, IOException {
		for (int i = 0; i < ids.length; i++) {
			int id = ids[i];
			roleBmsService.deleteRole(id);
		}
		returnSuccessJson("true");
	}
}
