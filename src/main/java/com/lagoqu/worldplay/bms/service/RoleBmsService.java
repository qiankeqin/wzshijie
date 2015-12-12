package com.lagoqu.worldplay.bms.service;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Role;
@Service
public class RoleBmsService extends BaseDao<Role>{
	
	@Resource
	private MenuBmsService menuBmsService;
	
	public boolean addRoleEntity(Role role){				
		return super.insert(role);
	}
	
	public Pagination<JSONArray> findRoleDetails(int page,int size,String userName){		
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		//userName = "admin1";
		String sql = "select * from Role";
		String countSql="select count(role.roleID) from Role role where role.roleID='1'";
		System.out.println(sql);
		System.out.println(countSql);
		super.findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	public boolean updateRole(Map<String,String[]> map){
		String str ="";
		String str_name="";
		String description = map.get("description")[0];
		
 		int roleID=Integer.parseInt(map.get("roleID")[0]);
 		String roleName=map.get("roleName")[0];
 		String[] str123 = map.get("menuID");
 		
		for(int i=0 ;i<map.get("menuID").length ;i++){
			str += map.get("menuID")[i]+",";
		}
		
		String tmp = str.substring(0, str.length()-1);
		String menuID = tmp;
		
		String []arr = menuID.split(",");
		for(int a=0 ;a<arr.length ;a++){
			str_name +=menuBmsService.findByID(Integer.parseInt(arr[a]))+",";			
		}
		
		String menuName = str_name.substring(0,str_name.length()-1);
				
		String sql="update Role set menuName='"+menuName+"' ,description='"+description+"' ,roleName='"+roleName+"',menuID='"+menuID+"' where roleID='"+roleID+"'";
		boolean updatestate=super.execSql(sql);
		return updatestate;
	}
	
	public int  deleteRole(int roleID) throws SQLException{
		String sql = "delete from Role where roleID ="+roleID;
		boolean delstate=super.execSql(sql);
		if(delstate){
			return 1;
		}else{
			return 0;
		}
	}
	
}
