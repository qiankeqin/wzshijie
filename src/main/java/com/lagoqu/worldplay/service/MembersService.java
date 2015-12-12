package com.lagoqu.worldplay.service;

import java.sql.SQLException;
import java.sql.Timestamp;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.security.Base64MD5;
import com.lagoqu.common.security.MD5;
import com.lagoqu.common.util.RandomUtil;
import com.lagoqu.worldplay.constants.Constants;
import com.lagoqu.worldplay.entity.Members;
import com.lagoqu.worldplay.util.Cn2Spell;
import com.lagoqu.worldplay.util.SendPost;

/**
 * 描述：会员管理<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月3日下午1:23:58 <br>
 * E-mail: <br>
 */
@Service
public class MembersService extends BaseDao<Members> {

	@Resource
	AccountsService accountsService;

	/**
	 * 方法名称: insertMembers<br>
	 * 描述：添加用户 作者: 王小欢 修改日期：2015年6月4日下午1:33:02
	 * 
	 * @param pNum
	 * @return
	 */
	public Members insertMembers(String pNum, String membersWxID,
			String membersImage, String membersNickName, int membersSex,
			int membersState) {
		RandomUtil randomUtil = new RandomUtil();
		String mName = randomUtil.getChar(6);
		String mNick = randomUtil.getNum(6);
		Cn2Spell cn = new Cn2Spell();
		Members members = new Members();
		members.setMembersPhone(pNum);
		if (membersNickName == null) {
			members.setMembersNickName(mName + mNick);
		} else {
			members.setMembersNickName(membersNickName);
		}
		if (membersImage == null) {
			members.setMembersImage("/images/wzsjusertouxiang.jpg");
		} else {
			members.setMembersImage(membersImage);
		}
		members.setMembersSex(membersSex);
		members.setMembersWxID(membersWxID);
		members.setMembersNamePY(cn.converterToFirstSpell(mName + mNick));

		members.setMembersPassword(Base64MD5.encrypt(MD5.convertMD5("444444")
				+ Constants.ENCRYP_STRING));
		members.setMembersState(membersState);
		Integer id = insertBackID(members);
		members.setMembersID(id);
		return members;
	}
	
	/**
	 * 方法名称: insertThirdMembers<br>
	 * 描述：手机端三方登陆登录
	 * 作者: 邢留杰
	 * 修改日期：2015年8月26日下午2:02:39
	 * @param openId
	 * @param membersPhone
	 * @param membersImage
	 * @param membersNickName
	 * @param type
	 * @return
	 */
	public Members insertThirdMembers(String openId,String membersPhone,String membersImage, String membersNickName,String type) {
		Members members = new Members();
		if("1".equals(type)){
			members.setMembersAndroidWxID(openId);
		}else if("2".equals(type)){
			members.setMembersWbID(openId);
		}else if("3".equals(type)){
			members.setMembersQQID(openId);
		}
		members.setMembersNickName(membersNickName);
		members.setMembersPassword(Base64MD5.encrypt(MD5.convertMD5("444444")
				+ Constants.ENCRYP_STRING));
		members.setMembersImage(membersImage);
		members.setMembersPhone(membersPhone);
		Integer id = insertBackID(members);
		members.setMembersID(id);
		return members;
	}

	/**
	 * 方法名称: updateMembers<br>
	 * 描述：修改用户信息 作者: 王小欢 修改日期：2015年6月4日下午5:33:05
	 * 
	 * @param members
	 * @throws SQLException
	 */
	public Members updateMembers(JSONObject jObject) throws SQLException {
		Cn2Spell cn = new Cn2Spell();
		String membersNamePY = "#";
		if (jObject.getString("membersNickName").isEmpty()) {
			membersNamePY = "#";
		} else {
			membersNamePY = cn.converterToFirstSpell(jObject
					.getString("membersNickName"));
		}
		String sql = "update Members set membersNickName='"
				+ jObject.getString("membersNickName") + "',membersNamePY='"
				+ membersNamePY
				+ "',updateTime=CURRENT_TIMESTAMP,membersImage='"
				+ jObject.getString("membersImage") + "',membersLocation='"
				+ jObject.getString("membersLocation") + "',membersSex="+jObject.getInt("membersSex")+" where membersID = "
				+ jObject.getInt("membersID");
		boolean state = super.execSql(sql);
		if (state == true) {
			return super.get(jObject.getInt("membersID"));
		} else {
			return null;
		}
	}

	/**
	 * 方法名称: findById<br>
	 * 描述：根据用户id查询用户信息 作者: 王小欢 修改日期：2015年6月11日上午10:33:17
	 * 
	 * @return
	 */
	public JSONObject findById(String pNum) {
		String sql = "select membersID,membersNickName,membersImage,membersPhone,membersLoginName,membersPassword,membersSex,membersLocation,identifyState from Members where membersPhone=?";
		JSONObject members = null;
		members = findJson(sql, " id desc", pNum);
		// 判读数据库里面是否有该用户
		if (members == null) {// 无该用户，创建新用户，并返回用户信息
			Members m = new Members();// 创建新用户
			m = this.insertMembers(pNum, null, null, null, 1, 0);
			// 失败
			if (m.getMembersID() == -1) {
				return null;
			} else {
				// 创建个人账户
				accountsService.insertAccounts(m.getMembersID());
				// 注册环信用户
				loginhuanxin(m);
				m.setMembersID(m.getMembersID());
				members = JSONObject.fromObject(m);
				return members;
			}
		} else {// 有该用户，返回用户信息
			return members;
		}
	}

	/**
	 * 方法名称: findBymembersWxID<br>
	 * 描述：根据微信号查询用户信息 作者: 王小欢 修改日期：2015年6月30日上午11:52:32
	 * 
	 * @param membersWxID
	 * @return
	 */
	public JSONObject findBymembersWxID(String membersWxID) {
		String sql = "select membersID,membersNickName,membersImage,membersPhone,membersLoginName,membersPassword,membersSex,membersLocation,membersWxID from Members where membersWxID='"+ membersWxID + "'";
		JSONObject members = null;
		members = findJson(sql, " id desc");
		return members;
	}
    /**
     * 方法名称: findByAndroidLoginType<br>
     * 描述：app三方登陆
     * 作者: 邢留杰
     * 修改日期：2015年8月26日下午3:43:59
     * @param type
     * @param openId
     * @return
     */
	public JSONObject findByAndroidLoginType(String type,String openId){
		JSONObject result=new JSONObject();
		if("1".equals(type)){
			String sql = "select membersID,membersPassword from Members where membersAndroidWxID=?";
			JSONObject jsonObj = null;
			jsonObj = findJson(sql, null, openId);
			if(jsonObj==null){
				result.put("flag", 0);
				result.put("ms","");
				result.put("data", jsonObj);
			}else{
				result.put("flag", 1);
				result.put("ms","");
				result.put("data", jsonObj);
			}
		}else if("2".equals(type)){
			String sql = "select membersID,membersPassword from Members where membersWbID=?";
			JSONObject jsonObj = null;
			jsonObj = findJson(sql, null, openId);
			if(jsonObj==null){
				result.put("flag", 0);
				result.put("ms","");
				result.put("data", jsonObj);
			}else{
				result.put("flag", 1);
				result.put("ms","");
				result.put("data", jsonObj);
			}
		}else if("3".equals(type)){
			String sql = "select membersID,membersPassword from Members where membersQQID=?";
			JSONObject jsonObj = null;
			jsonObj = findJson(sql, null, openId);
			if(jsonObj==null){
				result.put("flag", 0);
				result.put("ms","");
				result.put("data", jsonObj);
			}else{
				result.put("flag", 1);
				result.put("ms","");
				result.put("data", jsonObj);
			}
		}else{
			result.put("flag", -1);
			result.put("ms","非法操作");
			result.put("data", null);
		}
		return result;
	}
	
	/**
	 * 方法名称: bindingkik<br>
	 * 描述：绑定微信和手机号 作者: 王小欢 修改日期：2015年6月30日下午2:17:48
	 * 
	 * @param pNum
	 * @param membersWxID
	 * @return
	 */
	public JSONObject bindingkik(String pNum, String membersWxID,
			String membersImage, String membersNickName, int membersSex) {
		
		JSONObject result=new JSONObject();
		
		String sql = "select membersID,membersWxID,membersNickName,membersImage,membersPhone,membersLoginName,membersPassword,membersSex,membersLocation from Members where membersPhone=?";
		JSONObject members = null;
		members = findJson(sql, " id desc", pNum);
		// 判读数据库里面是否有该用户
		if (members == null) {// 无该用户，创建新用户，并返回用户信息
			Members m = new Members();// 创建新用户
			m = this.insertMembers(pNum, membersWxID, membersImage,
					membersNickName, membersSex, 0);
			// 失败
			if (m.getMembersID() == -1) {
				result.put("flag", -1);
				result.put("ms","注册失败");
				result.put("data", null);
				return result;
			} else {
				// 创建个人账户
				accountsService.insertAccounts(m.getMembersID());
				// 注册环信用户
				loginhuanxin(m);
				m.setMembersID(m.getMembersID());
				members = JSONObject.fromObject(m);
				result.put("flag", 1);
				result.put("ms","");
				result.put("data", members);
				return result;
			}
		} else {// 有该用户，判断微信和手机号是否已经绑定
			if ("".equals(members.getString("membersWxID"))) {
				String sql3 = "update Members set membersWxID =? where membersPhone=?";
				super.execSql(sql3, membersWxID, pNum);
			} else {
				if (!membersWxID.equals(members.getString("membersWxID"))) {
					result.put("flag", -1);
					result.put("ms","该手机号已绑定过其他微信账户，不能重复绑定");
					result.put("data", null);
					return result;
				}
			}
			// 进行手机号和微信绑定
			String sql2 = "select membersID,membersNickName,membersImage,membersPhone,membersLoginName,membersPassword,membersSex,membersLocation from Members where membersPhone=? and membersWxID=?";
			JSONObject members2 = null;
			members2 = findJson(sql2, " id desc", pNum, membersWxID);
			result.put("flag", 1);
			result.put("ms","");
			result.put("data", members2);
			return result;

		}

	}

	
	/**
	 * 方法名称: bindAndroidWeiXin<br>
	 * 描述：安卓端微信登录
	 * 作者: 邢留杰
	 * 修改日期：2015年8月26日下午1:53:25
	 * @param openId
	 * @param membersImage
	 * @param membersNickName
	 * @param phone
	 * @return
	 */
	public JSONObject bindAndroidWeiXin(String openId,String membersImage, String membersNickName,String phone) {
		JSONObject result=new JSONObject();
		String sql = "select membersID,membersWxID,membersNickName,membersImage,membersPhone,membersAndroidWxID,membersQQID,membersWbID from Members where membersPhone=?";
		JSONObject members = null;
		members = findJson(sql, " id desc", phone);
		// 判读数据库里面是否有该用户
		if (members == null) {// 无该用户，创建新用户，并返回用户信息
			Members m = new Members();// 创建新用户
			m = this.insertThirdMembers(openId, phone, membersImage, membersNickName,"1");
			// 失败
			if (m.getMembersID() == -1) {
				result.put("flag", -1);
				result.put("ms","绑定失败");
				result.put("data", null);
				return result;
			} else {
				// 创建个人账户
				accountsService.insertAccounts(m.getMembersID());
				// 注册环信用户
				loginhuanxin(m);
				m.setMembersID(m.getMembersID());
				members = JSONObject.fromObject(m);
				result.put("flag", 1);
				result.put("ms","");
				result.put("data", members);
				return result;
			}
		} else {// 有该用户，判断微信和手机号是否已经绑定
			if ("".equals(members.getString("membersAndroidWxID"))) {
				String sql3 = "update Members set membersAndroidWxID =? where membersPhone=?";
				super.execSql(sql3, openId, phone);
			} else {
				if (!openId.equals(members.getString("membersAndroidWxID"))) {
					result.put("flag", -1);
					result.put("ms","该手机号已绑定过其他微信账户，不能重复绑定");
					result.put("data", null);
					return result;
				}
			}
			// 进行手机号和微信绑定
			String sql2 = "select membersID,membersPassword,membersNickName,membersImage,membersPhone,membersLoginName,membersSex,membersLocation,identifyState from Members where membersPhone=? and membersAndroidWxID=?";
			JSONObject members2 = null;
			members2 = findJson(sql2,null, phone, openId);
			result.put("flag", 1);
			result.put("ms","");
			result.put("data", members2);
			return result;

		}

	}
	/**
	 * 方法名称: bindAndroidWeiBo<br>
	 * 描述：安卓端微博登录
	 * 作者: 邢留杰
	 * 修改日期：2015年8月26日下午2:17:47
	 * @param openId
	 * @param membersImage
	 * @param membersNickName
	 * @param phone
	 * @return
	 */
	public JSONObject bindAndroidWeiBo(String openId,String membersImage, String membersNickName,String phone) {
		JSONObject result=new JSONObject();
		String sql = "select membersID,membersWxID,membersNickName,membersImage,membersPhone,membersAndroidWxID,membersQQID,membersWbID from Members where membersPhone=?";
		JSONObject members = null;
		members = findJson(sql, " id desc", phone);
		// 判读数据库里面是否有该用户
		if (members == null) {// 无该用户，创建新用户，并返回用户信息
			Members m = new Members();// 创建新用户
			m = this.insertThirdMembers(openId, phone, membersImage, membersNickName,"2");
			// 失败
			if (m.getMembersID() == -1) {
				result.put("flag", -1);
				result.put("ms","绑定失败");
				result.put("data", null);
				return result;
			} else {
				// 创建个人账户
				accountsService.insertAccounts(m.getMembersID());
				// 注册环信用户
				loginhuanxin(m);
				m.setMembersID(m.getMembersID());
				members = JSONObject.fromObject(m);
				result.put("flag", 1);
				result.put("ms","");
				result.put("data", members);
				return result;
			}
		} else {// 有该用户，判断微信和手机号是否已经绑定
			if ("".equals(members.getString("membersWbID"))) {
				String sql3 = "update Members set membersWbID =? where membersPhone=?";
				super.execSql(sql3, openId, phone);
			} else {
				if (!openId.equals(members.getString("membersWbID"))) {
					result.put("flag", -1);
					result.put("ms","该手机号已绑定过其他微博账户，不能重复绑定");
					result.put("data", null);
					return result;
				}
			}
			// 进行手机号和微信绑定
			String sql2 = "select membersID,membersPassword,membersNickName,membersImage,membersPhone,membersLoginName,membersSex,membersLocation,identifyState from Members where membersPhone=? and membersWbID=?";
			JSONObject members2 = null;
			members2 = findJson(sql2,null, phone, openId);
			result.put("flag", 1);
			result.put("ms","");
			result.put("data", members2);
			return result;

		}

	}
	/**
	 * 方法名称: bindAndroidWeiBo<br>
	 * 描述：安卓端微博登录
	 * 作者: 邢留杰
	 * 修改日期：2015年8月26日下午2:17:47
	 * @param openId
	 * @param membersImage
	 * @param membersNickName
	 * @param phone
	 * @return
	 */
	public JSONObject bindAndroidQq(String openId,String membersImage, String membersNickName,String phone) {
		JSONObject result=new JSONObject();
		String sql = "select membersID,membersWxID,membersNickName,membersImage,membersPhone,membersAndroidWxID,membersQQID,membersWbID from Members where membersPhone=?";
		JSONObject members = null;
		members = findJson(sql, " id desc", phone);
		// 判读数据库里面是否有该用户
		if (members == null) {// 无该用户，创建新用户，并返回用户信息
			Members m = new Members();// 创建新用户
			m = this.insertThirdMembers(openId, phone, membersImage, membersNickName,"3");
			// 失败
			if (m.getMembersID() == -1) {
				result.put("flag", -1);
				result.put("ms","绑定失败");
				result.put("data", null);
				return result;
			} else {
				// 创建个人账户
				accountsService.insertAccounts(m.getMembersID());
				// 注册环信用户
				loginhuanxin(m);
				m.setMembersID(m.getMembersID());
				members = JSONObject.fromObject(m);
				result.put("flag", 1);
				result.put("ms","");
				result.put("data", members);
				return result;
			}
		} else {// 有该用户，判断微信和手机号是否已经绑定
			if ("".equals(members.getString("membersQQID"))) {
				String sql3 = "update Members set membersQQID =? where membersPhone=?";
				super.execSql(sql3, openId, phone);
			} else {
				if (!openId.equals(members.getString("membersQQID"))) {
					result.put("flag", -1);
					result.put("ms","该手机号已绑定过其他QQ账户，不能重复绑定");
					result.put("data", null);
					return result;
				}
			}
			// 进行手机号和微信绑定
			String sql2 = "select membersID,membersPassword,membersNickName,membersImage,membersPhone,membersLoginName,membersSex,membersLocation,identifyState from Members where membersPhone=? and membersQQID=?";
			JSONObject members2 = null;
			members2 = findJson(sql2,null, phone, openId);
			result.put("flag", 1);
			result.put("ms","");
			result.put("data", members2);
			return result;

		}

	}
	/**
	 * 方法名称: findFriendsByMemberID<br>
	 * 描述：根据用户memberID查询好友列表 作者: 邢留杰 修改日期：2015年6月8日下午1:25:25
	 * 
	 * @param page
	 * @param size
	 * @param memberID
	 * @return
	 */
	public JSONArray findFriendsByMemberID(int memberID) {
		JSONArray getfriends = null;
		String sql = "select ms.membersID,ms.membersNickName,ms.membersImage,ms.membersPhone,ms.membersNamePY from Friends fs left join Members ms on fs.friendUserID =ms.membersID where fs.membersID=? order by ms.membersNamePY asc";
		getfriends = super.findJsonArray(sql, null, memberID);
		return getfriends;
	}

	/**
	 * 方法名称: updateIdCard<br>
	 * 描述：根据用户id更改用户身份证认证状态 作者: 邢留杰 修改日期：2015年7月16日下午6:14:26
	 * 
	 * @param state
	 * @param membersID
	 */
	public boolean updateIdCard(int state, int membersID) {
		String sql = "update Members set identifyState=? where membersID=?";
		boolean bl = super.execSql(sql, state, membersID);
		return bl;
	}

	/**
	 * 方法名称: findfriends<br>
	 * 描述：搜索用户用户列表（根据用户ＩＤ和关键字） 作者: 王小欢 修改日期：2015年6月23日上午10:50:41
	 * 
	 * @param memberID
	 * @param key
	 * @return
	 */
	public JSONArray findfriends(int memberID, String key) {
		JSONArray getfriends = null;
		String sql = "select ms.membersID,ms.membersNickName,ms.membersImage,ms.membersPhone,ms.membersNamePY from Friends fs left join Members ms on fs.friendUserID =ms.membersID where fs.membersID="
				+ memberID;
		if (key.length() != 0) {
			sql += " and ms.membersNickName like'%" + key + "%'";
		}
		sql += " order by ms.membersNamePY asc";
		getfriends = super.findJsonArray(sql, null);
		return getfriends;
	}

	/**
	 * 方法名称: findFriendsByMemberID<br>
	 * 描述：根据用户memberID and 更新时间查询好友列表 作者: 邢留杰 修改日期：2015年6月8日下午1:25:25
	 * 
	 * @param page
	 * @param size
	 * @param memberID
	 * @return
	 */
	public JSONArray repeatFriendsByMemberID(int memberID, Timestamp updateTime) {
		JSONArray getfriends = null;
		String sql = "select ms.membersID,ms.membersNickName,ms.membersImage,ms.membersPhone,ms.membersNamePY from Friends fs left join Members ms on fs.friendUserID =ms.membersID where fs.membersID=? and ms.updateTime>? order by ms.membersNamePY asc";
		getfriends = super.findJsonArray(sql, null, memberID, updateTime);
		return getfriends;
	}

	/**
	 * 方法名称: seachFriend<br>
	 * 描述：根据关键字（手机号或者昵称查询会员列表） 作者: 邢留杰 修改日期：2015年6月8日下午2:01:42
	 * 
	 * @param page
	 * @param size
	 * @param key
	 * @return
	 */
	public JSONArray seachFriend(String key) {
		JSONArray jsonArray = null;
		String sql = "select ms.membersID,ms.membersNickName,ms.membersImage from  Members ms  where ms.membersNickName like ?";
		jsonArray = findJsonArray(sql, null,"%"+key+"%");
		return jsonArray;
	}

	/**
	 * 方法名称: findMemberById<br>
	 * 描述：根据id查找用户 作者: 邢留杰 修改日期：2015年6月16日下午6:25:34
	 * 
	 * @param membersID
	 * @return
	 * @throws SQLException
	 */
	public JSONObject findMemberById(int membersID) throws SQLException {
		String sql = "select ms.membersID,ms.membersNickName,ms.membersImage,ms.membersSex,ms.membersLocation,ms.identifyState from  Members ms  where ms.membersID=?";
		JSONObject js = super.findJson(sql, null, membersID);
		return js;
	}

	/**
	 * 方法名称: loginhuanxin<br>
	 * 描述：注册环信用户 作者: 王小欢 修改日期：2015年6月30日下午2:02:57
	 * 
	 * @param m
	 */
	public void loginhuanxin(Members m) {
		SendPost sendPost = new SendPost();
		String url = "https://a1.easemob.com/" + Constants.easemob_key + "/"
				+ Constants.easemob_value + "/users";
		JSONObject obj = new JSONObject();
		obj.element("username", m.getMembersID());
		obj.element("password", m.getMembersPassword());
		JSONObject jsonObject = sendPost.postHuanxin(url, obj);
	}
}
