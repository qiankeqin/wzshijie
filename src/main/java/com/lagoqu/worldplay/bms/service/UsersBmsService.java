package com.lagoqu.worldplay.bms.service;

import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.security.Base64MD5;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.User;


@Service
public class UsersBmsService extends BaseDao<User> {
	
	public JSONObject queryUsersLogin(String userName, String password)
			throws SQLException {
		String sql = "select t.userID,t.userName,t.realName,t.password ,t.roleID  from User t where t.userName=? and t.password=?";	
		password = Base64MD5.encrypt(password);
		JSONObject jso = super.findJson(sql, null,userName,password);	
		if (jso != null) {
			return jso;
		} else {
			return null;
		}
	}
	
	public Pagination<JSONArray> findUser(int page,int size,String userName){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql = "select * from User where 1=1";
		String countSql="select count(*) from User where 1=1";
		if(userName!=null && userName!=""){
			sql+=" and userName like '%"+userName+"%'";
			countSql+=" and userName like '%"+userName+"%'";
		}
		System.out.println(sql);
		System.out.println(countSql);
		super.findListJsonByPages(pagination,sql,countSql);
		return pagination;

	}
	
	public Pagination<JSONArray> findUserList(int page,int size,String userName,String isExist){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		//userName = "admin1";
		String sql = "select * from User where 1=1";
		String countSql="select count(*) from User where 1=1";
		if(userName!=null && userName!=""){
			sql+=" and userName like '%"+userName+"%'";
			countSql+=" and userName like '%"+userName+"%'";
			if(isExist !=null && isExist !=""){				
			}
		}
		System.out.println(sql);
		System.out.println(countSql);
		super.findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	public boolean addUserEntity(User user){						
		return insert(user);
	}
		
	public boolean updateUser(Map<String,String[]> map){
 		int userID=Integer.parseInt(map.get("userID")[0]);
		String userName=map.get("userName")[0];
		String[] roleID = map.get("roleID");
		String tempRoleID = StringUtils.join(roleID, ",");
		String[] roleName= map.get("roleName");
		String tempRoleName = StringUtils.join(roleName, ",");
		/*String isExist=map.get("isExist")[0];*/
		String email = map.get("email")[0];
		String telephone = map.get("telephone")[0];
		String realName = map.get("realName")[0];
		String sex = map.get("sex")[0];
		String sql="update User set userName='"+userName+"' ,roleName='"+tempRoleName+"',roleID='"+tempRoleID+"' ,realName='"+realName+"' ,sex='"+sex+"' ,email='"+email+"' ,telephone='"+telephone+"' where userID='"+userID+"'";
		boolean updatestate=super.execSql(sql);
		return updatestate;
	}
	
	public int  deleteUser(int userID) throws SQLException{
		String sql = "delete from User where userID ="+userID;
		boolean delstate=super.execSql(sql);
		if(delstate){
			return 1;
		}else{
			return 0;
		}
	}
	
	public String findUserName(){
		String sql ="select userName from User";
		super.execSql(sql);		
		return null;
	}
}
