package com.lagoqu.worldplay.service;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.db.entity.CompareType;
import com.lagoqu.common.db.entity.SearchCondition;
import com.lagoqu.worldplay.entity.Friends;
/**描述：好友关系<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月8日上午11:10:25 <br>
 * E-mail:  <br> 
 */
@Service
public class FriendsService extends BaseDao<Friends>{

	
	
	public boolean insertAddfriend(JSONObject jObject){
		boolean addfriendstate=false;
		Friends friends=(Friends) jObject.toBean(jObject, Friends.class);
		JSONArray friendsArray= findJsonArray("select * from Friends where membersID=? and friendUserID=?", " id desc",friends.getMembersID(),friends.getFriendUserID());
		if(friendsArray.size()>0 || friendsArray==null){
			return false;
		}else{
			//添加好友关系，需要添加两条记录，自己的好友和对方的好友
			boolean MIfriendstate=insert(friends);//自己的好友关系添加
			Friends f=new Friends();
			f.setMembersID(friends.getFriendUserID());
			f.setFriendUserID(friends.getMembersID());
			boolean YUfriendstate=insert(f);//对方的好友关系添加
			
			if(MIfriendstate==true && YUfriendstate==true){
				addfriendstate=true;
			}else {
				addfriendstate=false;
			}
			return addfriendstate;
		}
	}
}
