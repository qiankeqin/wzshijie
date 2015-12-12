package com.lagoqu.worldplay.bms.ajax;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.lagoqu.worldplay.entity.Menu;



@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/menu")
public class MenuBmsAjax extends BMSController{
	
	@Resource
	private MenuBmsService menuBmsService;
	
	@Resource
	private RoleBmsService roleBmsService;
	
	@ResponseBody
	@RequestMapping(value = "tree",method = RequestMethod.GET)
	public void tree() throws Exception {
		JSONObject s =(JSONObject) super.getSession().getAttribute("session_user");
		JSONArray tree = this.menuBmsService.findChildrenForEasyUI(0);
		String[] roles = s.getString("roleID").split(",");
		String temp ="";
		for(int i=0;i<roles.length;i++){
			temp+= roleBmsService.get(Integer.parseInt(roles[i])).getMenuID()+",";
		}
		temp = temp.substring(0, temp.length()-1);
		String[] tempArr = temp.split(",");
		List<Integer> tempList=new ArrayList<Integer>();
		for(String str :tempArr){
			tempList.add(Integer.parseInt(str));
		}
		for(int k =tree.size()-1;k>=0;k--){
			JSONObject js=tree.getJSONObject(k);
			if(isExits(tempList,js.getInt("id"))){
				if(js.get("children")!=null){
					JSONArray ja = js.getJSONArray("children");
					for(int p =ja.size()-1;p>=0;p--){
						JSONObject js1=ja.getJSONObject(p);
						if(isExits(tempList,js1.getInt("id"))){
							
						}else{
							ja.remove(p);
						}
					}
				}
			}else
			{
				tree.remove(k);
			}
		}
		returnSuccessJson(tree.toString());
	}
	
	@ResponseBody
	@RequestMapping(value = "addTree",method = RequestMethod.GET)
	public void addTree() throws Exception {
		JSONArray tree = this.menuBmsService.findChildrenForEasyUI(0);		
		JSONArray result = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("id",0);
		jo.put("text", "顶级菜单");
		jo.put("children", tree);
		result.add(jo);		
		writeJSON(response, result.toString(), true);
	}	
	
	@ResponseBody
	@RequestMapping(value = "add_edit_Tree",method = RequestMethod.GET)
	public void addTre() throws Exception {
		JSONArray tree = this.menuBmsService.findChildrenForEasyUI(0);		
		writeJSON(response, tree.toString(), true);
	}	
	
	@ResponseBody
	@RequestMapping(value="add",method = RequestMethod.POST)	
	public void addMenuEntity() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();			
		int parentId = Integer.parseInt(map.get("parentId")[0]);		
		if(parentId == 0){
			String url = map.get("url")[0];
			String description = map.get("description")[0];
			int isFinal=Integer.parseInt(map.get("isFinal")[0]);
			String name =  map.get("name")[0];
			Menu menu = new Menu();
			menu.setUrl(url);
			menu.setMenuName(name);
			menu.setIsFinal(isFinal);
			menu.setParentId(parentId);
			menu.setDescription(description);	
			menuBmsService.addMenuEntity(menu);	
		}else{
			String url = map.get("url")[0];
			String description = map.get("description")[0];
			int isFinal=Integer.parseInt(map.get("isFinal")[0]);
			String name =  map.get("name")[0];
			//System.out.println(name);	
			Menu menu = new Menu();
			menu.setUrl(url);
			menu.setMenuName(name);
			menu.setIsFinal(isFinal);
			menu.setParentId(parentId);
			menu.setDescription(description);	
			menuBmsService.addMenuEntity(menu);	
		}				
	}
	
	
	@ResponseBody
	@RequestMapping(value="list",method = RequestMethod.GET)
	public void findMenuPageData() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);		
		Pagination<JSONArray> pagination=menuBmsService.findMenuDetails(page,size,0);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "editmenu", method = RequestMethod.PUT)
	public void edituser() throws Exception {		
		Map<String,String[]> map =super.getRequestParamsMap();
		boolean a = menuBmsService.updateMenu(map);	
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
			menuBmsService.deleteMenu(id);
		}
		returnSuccessJson("true");
	}
	private boolean isExits(List<Integer> str,Integer s){
		for(Integer temps:str){
			if(temps.equals(s)){
				return true;
			}
		}
		return false;
	}
}
