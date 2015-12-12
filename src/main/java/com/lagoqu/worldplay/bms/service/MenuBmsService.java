package com.lagoqu.worldplay.bms.service;

import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Menu;


@Service
public class MenuBmsService extends BaseDao<Menu>{
	
	public Pagination<JSONArray> findMenuDetails(int page,int size,int menuID){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql = "select * from Menu";
		String countSql = "select count(Menu.menuID) from Menu";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	public JSONArray findChildrenForEasyUI(int id)
			throws Exception {
		int maxRank = 5;
		Integer currentRank = 1;
		return this.findJChildrenForEasyUI(id, maxRank, currentRank);
	}
	
	private JSONArray findJChildrenForEasyUI(int id,int maxRank, int currentRank) throws Exception {
		if (currentRank > maxRank) {
			return null;
		}
		currentRank++;
		JSONArray result = new JSONArray();
		JSONArray list = this.findSubitem(id);
		for (int i = 0; i < list.size(); i++) {
			JSONObject jp = list.getJSONObject(i);
			JSONObject jo = new JSONObject();
			jo.put("id", jp.get("menuID"));
			jo.put("text",jp.get("menuName"));
			jo.put("parentId", jp.get("parentId"));
			JSONObject js = new JSONObject();
			js.put("url", jp.get("url"));
			jo.put("attributes", js);
			if (jp.getInt("isFinal")==1) {
				JSONArray subJa = this.findJChildrenForEasyUI(jp.getInt("menuID"),maxRank,currentRank);
				jo.put("children", subJa);
			}
			result.add(jo);
		}
		System.out.print(result);
		return result;
	}
	
	private JSONArray findSubitem(int id) throws Exception {
		String sql = "select menuID,menuName,parentId,isFinal,url from Menu where parentId =?";
		JSONArray jn = super.findJsonArray(sql, null, id);
		
		return jn;
	}
	
	public boolean addMenuEntity(Menu menu){				
		return super.insert(menu);
	}
	
	public boolean updateMenu(Map<String,String[]> map){
 		int menuID=Integer.parseInt(map.get("menuID")[0]);		
 		int parentId=Integer.parseInt(map.get("parentId")[0]);
		String url=map.get("url")[0];
		String menuName=map.get("menuName")[0];
		String isFinal=map.get("isFinal")[0];
		String sql="update Menu set url='"+url+"' ,parentId='"+parentId+"',menuName='"+menuName+"',isFinal='"+isFinal+"' where menuID='"+menuID+"'";
		boolean updatestate=super.execSql(sql);
		return updatestate;
	}
	
	public int  deleteMenu(int menuID) throws SQLException{
		String sql = "delete from Menu where menuID ="+menuID;
		boolean delstate=super.execSql(sql);
		if(delstate){
			return 1;
		}else{
			return 0;
		}
	}
	
	public String findByID(int menuID){
		Menu menu = new Menu();
		String menuName="" ;
		try {
			menu = super.get(menuID);
			menuName = menu.getMenuName();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return menuName;
	}
}
