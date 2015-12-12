package com.lagoqu.worldplay.service;

import java.sql.Timestamp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.Friends;

/**
 * 描述：用户添加好友<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月28日上午10:00:33 <br>
 * E-mail: <br>
 */
@Service
public class AppFriendsService extends BaseDao<Friends> {

	/**
	 * 方法名称: addfriend<br>
	 * 描述：用户进行好友添加 作者: 王小欢 修改日期：2015年10月28日上午10:09:41
	 * 
	 * @param membersID
	 *            用户id
	 * @param friendUserID
	 *            好友id
	 * @return
	 */
	public JSONObject addfriend(int membersID, int friendUserID) {
		JSONObject backJsonObject = new JSONObject();
		String sql = "select * from Friends where membersID=? and friendUserID=?";
		JSONArray friendsArray = findJsonArray(sql, " id desc", membersID,
				friendUserID);
		if (friendsArray.size() > 0 || friendsArray == null) {
			backJsonObject.put("message", "用户不存在,添加好友失败");
			backJsonObject.put("state", false);
			return backJsonObject;
		} else {
			// 添加好友关系，需要添加两条记录，自己的好友和对方的好友
			Friends friends = new Friends();
			friends.setMembersID(membersID);
			friends.setFriendUserID(friendUserID);
			boolean MIfriendstate = insert(friends);// 自己的好友关系添加
			Friends f = new Friends();
			f.setMembersID(friendUserID);
			f.setFriendUserID(membersID);
			boolean YUfriendstate = insert(f);// 对方的好友关系添加

			if (MIfriendstate == true && YUfriendstate == true) {
				backJsonObject.put("data", "添加好友成功");
				backJsonObject.put("state", true);
				return backJsonObject;
			} else {
				backJsonObject.put("message", "用户不存在,添加好友失败");
				backJsonObject.put("state", false);
				return backJsonObject;
			}
		}
	}

	/**
	 * 方法名称: getfriendList<br>
	 * 描述：用户获取好友列表 作者: 王小欢 修改日期：2015年10月28日上午10:45:26
	 * 
	 * @param membersID
	 * @return
	 */
	public JSONObject getfriendList(int membersID) {
		JSONObject backJsonObject = new JSONObject();
		String sql = "select ms.membersID,ms.membersNickName,ms.membersImage,ms.membersPhone,ms.membersNamePY from Friends fs inner join Members ms on fs.friendUserID =ms.membersID where fs.membersID=? order by ms.membersNamePY asc";
		JSONArray getfriends = super.findJsonArray(sql, null, membersID);
		if (getfriends != null) {
				backJsonObject.put("data", getfriends);
				backJsonObject.put("state", true);
				return backJsonObject;
		} else {
			backJsonObject.put("message", "查询好友失败");
			backJsonObject.put("state", false);
			return backJsonObject;
		}
	}

	/**
	 * 方法名称: seachUserfriendList<br>
	 * 描述：用户搜索本人好友列表 作者: 王小欢 修改日期：2015年10月28日下午1:43:19
	 * 
	 * @param membersID
	 * @param membersNickName
	 * @return
	 */
	public JSONObject seachUserfriendList(int membersID, String membersNickName) {
		JSONObject backJsonObject = new JSONObject();
		String sql = "select ms.membersID,ms.membersNickName,ms.membersImage,ms.membersPhone,ms.membersNamePY from Friends fs left join Members ms on fs.friendUserID =ms.membersID where fs.membersID="
				+ membersID;
		if (membersNickName != null && membersNickName != "") {
			sql += " and ms.membersNickName like'%" + membersNickName + "%'";
		}
		sql += " order by ms.membersNamePY asc";
		JSONArray getfriends = super.findJsonArray(sql, null);
		if (getfriends!=null) {
			backJsonObject.put("data", getfriends);
			backJsonObject.put("state", true);
			return backJsonObject;
		} else {
			backJsonObject.put("message", "暂无好友");
			backJsonObject.put("state", false);
			return backJsonObject;
		}
	}

	/**
	 * 方法名称: refurbishUserfriendList<br>
	 * 描述：刷新用户好友列表 作者: 王小欢 修改日期：2015年10月28日下午1:51:01
	 * 
	 * @param membersID
	 * @param updateTime
	 * @return
	 */
	public JSONObject refurbishUserfriendList(int membersID,
			Timestamp updateTime) {
		JSONObject backJsonObject = new JSONObject();
		String sql = "select ms.membersID,ms.membersNickName,ms.membersImage,ms.membersPhone,ms.membersNamePY from Friends fs left join Members ms on fs.friendUserID =ms.membersID where fs.membersID=? and ms.updateTime>? order by ms.membersNamePY asc";
		JSONArray getfriends = super.findJsonArray(sql, null, membersID,
				updateTime);
		if (getfriends != null) {
			backJsonObject.put("data", getfriends);
			backJsonObject.put("state", true);
			return backJsonObject;
		} else {
			backJsonObject.put("message", "没有新添加的好友");
			backJsonObject.put("state", false);
			return backJsonObject;
		}
	}

	/**
	 * 方法名称: seachfriend<br>
	 * 描述：用户搜索好友列表 作者: 王小欢 修改日期：2015年10月28日下午1:51:01
	 * 
	 * @param membersNickName
	 * @return
	 */
	public JSONObject seachfriend(String membersNickName) {
		JSONObject backJsonObject = new JSONObject();
		String sql = "select ms.membersID,ms.membersNickName,ms.membersImage from  Members ms  where ms.membersNickName like ?";
		JSONArray getfriends = findJsonArray(sql, null, "%" + membersNickName
				+ "%");
		if (getfriends != null) {
			backJsonObject.put("data", getfriends);
			backJsonObject.put("state", true);
			return backJsonObject;
		} else {
			backJsonObject.put("message", "没有此用户");
			backJsonObject.put("state", false);
			return backJsonObject;
		}
	}
}
