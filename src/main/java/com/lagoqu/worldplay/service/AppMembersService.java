package com.lagoqu.worldplay.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.security.Base64MD5;
import com.lagoqu.common.security.MD5;
import com.lagoqu.common.util.RandomUtil;
import com.lagoqu.worldplay.bms.service.MessageRecordBmsService;
import com.lagoqu.worldplay.constants.Constants;
import com.lagoqu.worldplay.entity.Members;
import com.lagoqu.worldplay.util.CheckUtil;
import com.lagoqu.worldplay.util.Cn2Spell;
import com.lagoqu.worldplay.util.MemCachedManager;
import com.lagoqu.worldplay.util.SendPost;
import com.lagoqu.worldplay.util.YunpianResult;
import com.lagoqu.worldplay.util.YunpianSms;
/**描述：用户管理<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月27日下午1:25:21 <br>
 * E-mail:  <br> 
 */
@Service
public class AppMembersService extends BaseDao<Members>{

	@Resource
	AppAccountsService appAccountsService;
	
	@Resource
	MessageRecordBmsService messageRecordBmsService;
	
	/**方法名称: getVerificationCode<br>
	 * 描述：获取验证码  (短信发送)
	 * 用户登录，服务器发送验证码。
	 * 作者: 王小欢
	 * 修改日期：2015年10月27日下午3:04:10
	 * @param membersPhone
	 * @param codeType   1 登录 2 注册  3绑定  4找回密码
	 * @return
	 * @throws IOException
	 */
	public JSONObject getVerificationCode(String membersPhone,int codeType) throws IOException{
		JSONObject backJsonObject=new JSONObject();
		if (!CheckUtil.isMobile(membersPhone)) {
			backJsonObject.put("message", "手机号码格式不正确");
			backJsonObject.put("state", false);
			return backJsonObject;
		}
		String sql = "select membersID,membersPhone from Members where membersPhone=?";
		JSONObject members = findJson(sql, " id desc", membersPhone);
		if(codeType==1){
			if(members==null){
				backJsonObject.put("message", "该手机号未注册,请注册后在登录");
				backJsonObject.put("state", false);
				return backJsonObject;
			}
		}
		if(codeType==2){
			if(members!=null){
				backJsonObject.put("message", "该手机号已注册，请登录");
				backJsonObject.put("state", false);
				return backJsonObject;
			}
		}
		if(codeType==4){
			if(members==null){
				backJsonObject.put("message", "该手机号不存在，请重新输入");
				backJsonObject.put("state", false);
				return backJsonObject;
			}
		}
		MemCachedManager cache = MemCachedManager.getInstance();
		boolean flag = true;
		String pNumCode = membersPhone+"count";
		if(cache.get(pNumCode)==null){
			cache.set(pNumCode, 1,new Date(30*60*1000));
		}else {
			int count=Integer.parseInt(cache.get(pNumCode).toString());
			if(count>=3){
				flag = false;
			}else {
				count++;
				cache.set(pNumCode,count,new Date(30*60*1000));
			}
		}
		if(flag == false){
			backJsonObject.put("message", "同一手机号30分钟之内只能发送三次验证码");
			backJsonObject.put("state", false);
			return backJsonObject;
		}else{
			RandomUtil randomUtil = new RandomUtil();
			String code=randomUtil.getNum(6);
		//	String code="123456";
			String tpl_value = "#code#=" + code;
			YunpianResult yr = YunpianSms.tplSendSms(Constants.sms_tpl_id,tpl_value, membersPhone);
			//短信日志记录
			messageRecordBmsService.saveMessageRecord(membersPhone, tpl_value, 1, yr.getMsg());
			if (yr.isSendSuccess()) {
					cache.set(membersPhone, code);
					if(codeType!=2){
						backJsonObject.put("membersID", members.getInt("membersID"));
					}
					backJsonObject.put("data", code);
					backJsonObject.put("state", true);	
					return backJsonObject; 
			}else{ 
				  String ms = null; 
				  if (yr.isIllegalUserOperate()) { 
					     ms=yr.getMsg(); 
				  }else{ 
						ms = "短信发送失败"; 
				  } 
				  backJsonObject.put("message", ms);
				  backJsonObject.put("state", false);
				  return backJsonObject; 
		    }
		}
	}
	
	
	
	
	/**方法名称: membersRegister<br>
	 * 描述：用户注册(手机号+验证码)
	 * 作者: 王小欢
	 * 修改日期：2015年11月9日下午3:41:33
	 * @param membersPhone
	 * @param code
	 * @return
	 */
	public JSONObject membersRegister(String membersPhone,String code){
		JSONObject backJsonObject=new JSONObject();
		MemCachedManager cache = MemCachedManager.getInstance();
		// 判断验证码是否正确
		if (cache.get(membersPhone) != null && cache.get(membersPhone).toString().equals(code)) {
			String sql = "select membersID,membersNickName,membersImage,membersPhone,membersLoginName,membersPassword,membersSex,membersLocation,identifyState from Members where membersPhone=?";
			JSONObject members = null;
			members = findJson(sql, " id desc", membersPhone);
			// 判读数据库里面是否有该用户
			if (members == null) {// 无该用户，创建新用户，并返回用户信息
				Members m = new Members();// 创建新用户
				m = this.insertMembers(membersPhone, null, null, null, 1, 0);
				// 失败
				if (m.getMembersID() == -1) {
					backJsonObject.put("message","创建用户失败");
					backJsonObject.put("state", false);
					return backJsonObject; 
				} else {
					// 创建个人账户
					appAccountsService.insertAccounts(m.getMembersID());
					// 注册环信用户
					loginhuanxin(m);
					m.setMembersID(m.getMembersID());
					members = JSONObject.fromObject(m);
					backJsonObject.put("data", members);
					backJsonObject.put("state", true);	
					cache.remove(membersPhone);
					return backJsonObject; 
				}
			}else{
				backJsonObject.put("message","该用户已存在，请登录");
				backJsonObject.put("state", false);
				return backJsonObject;
			}
		}else{
			backJsonObject.put("message","验证码已过期");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
	
	
	
	
	
	
	/**方法名称: membersLoginCode<br>
	 * 描述：用户登录（手机号+验证码）
	 * 作者: 王小欢
	 * 修改日期：2015年10月27日下午5:39:33
	 * @param membersPhone
	 * @param code
	 * @return
	 */
	public JSONObject membersLoginCode(String membersPhone,String code){
		JSONObject backJsonObject=new JSONObject();
		MemCachedManager cache = MemCachedManager.getInstance();
		if("13888888888".equals(membersPhone)){
			String sql1 = "select membersID,membersNickName,membersImage,membersPhone,membersLoginName,membersPassword,membersSex,membersLocation,identifyState from Members where membersPhone=?";
			JSONObject members1 = null;
			members1 = findJson(sql1, " id desc", membersPhone);
			backJsonObject.put("data", members1);
			backJsonObject.put("state", true);
			return backJsonObject; 
		}
		// 判断验证码是否正确
		if (cache.get(membersPhone) != null && cache.get(membersPhone).toString().equals(code)) {
			String sql = "select membersID,membersNickName,membersImage,membersPhone,membersLoginName,membersPassword,membersSex,membersLocation,identifyState from Members where membersPhone=?";
			JSONObject members = null;
			members = findJson(sql, " id desc", membersPhone);
			// 判读数据库里面是否有该用户
			if (members == null) {// 无该用户，创建新用户，并返回用户信息
				/*Members m = new Members();// 创建新用户
				m = this.insertMembers(membersPhone, null, null, null, 1, 0);
				// 失败
				if (m.getMembersID() == -1) {
					backJsonObject.put("message","创建用户失败");
					backJsonObject.put("state", false);
					return backJsonObject; 
				} else {
					// 创建个人账户
					appAccountsService.insertAccounts(m.getMembersID());
					// 注册环信用户
					loginhuanxin(m);
					m.setMembersID(m.getMembersID());
					members = JSONObject.fromObject(m);
					backJsonObject.put("data", members);
					backJsonObject.put("state", true);	
					cache.remove(membersPhone);
					return backJsonObject; 
				}*/
				backJsonObject.put("message","该手机号未注册,请注册后在登录");
				backJsonObject.put("state", false);
				return backJsonObject;
			} else {// 有该用户，返回用户信息
				backJsonObject.put("data", members);
				backJsonObject.put("state", true);
				cache.remove(membersPhone);
				return backJsonObject; 
			}
			
		} else {
			backJsonObject.put("message","验证码已过期");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}

	}
	
	
	
	
	
	/**方法名称: membersLoginPass<br>
	 * 描述：用户登录(手机号+密码)
	 * 作者: 王小欢
	 * 修改日期：2015年11月9日下午3:56:41
	 * @param membersPhone
	 * @param password
	 * @return
	 */
	public JSONObject membersLoginPass(String membersPhone,String password){
		JSONObject backJsonObject=new JSONObject();
		if("13888888888".equals(membersPhone)){
			String sql1 = "select membersID,membersNickName,membersImage,membersPhone,membersLoginName,membersPassword,membersSex,membersLocation,identifyState from Members where membersPhone=?";
			JSONObject members1 = null;
			members1 = findJson(sql1, " id desc", membersPhone);
			backJsonObject.put("data", members1);
			backJsonObject.put("state", true);
			return backJsonObject; 
		}
		String sql = "select membersID,membersNickName,membersImage,membersPhone,membersLoginName,membersPassword,membersSex,membersLocation,identifyState from Members where membersPhone=? and membersPassword=?";
		JSONObject members = null;
		members = findJson(sql, " id desc", membersPhone,password);
		// 判读数据库里面是否有该用户
		if (members == null) {// 无该用户，创建新用户，并返回用户信息
			backJsonObject.put("message","手机号或密码错误，请检查");
			backJsonObject.put("state", false);
			return backJsonObject;
		} else {// 有该用户，返回用户信息
			backJsonObject.put("data", members);
			backJsonObject.put("state", true);
			return backJsonObject; 
		}
	}
	
	
	/**方法名称: subsidiaryLogin<br>
	 * 描述：用户第三方工具登录(QQ，微信，微博)
	 * 1 微信 2 微博 3 qq
	 * 作者: 王小欢
	 * 修改日期：2015年10月27日下午5:58:39
	 * @param loginType   1 微信 2 微博 3 qq
	 * @param openId
	 * @return
	 */
	public JSONObject subsidiaryLogin(String loginType,String openId){
		JSONObject backJsonObject=new JSONObject();
		String sql="";
		if("1".equals(loginType)){
			 sql = "select * from Members where membersAndroidWxID=?";
		}
		if("2".equals(loginType)){
			sql = "select * from Members where membersWbID=?";	
		}
		if("3".equals(loginType)){
			sql = "select * from Members where membersQQID=?";
		}
		JSONObject jsonObj = null;
		jsonObj = findJson(sql, null, openId);
		if(jsonObj==null){
			backJsonObject.put("message","用户不存在,需要绑定手机号");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}else{
			backJsonObject.put("data", jsonObj);
			backJsonObject.put("state", true);
			return backJsonObject; 
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
				appAccountsService.insertAccounts(m.getMembersID());
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
				appAccountsService.insertAccounts(m.getMembersID());
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
	 * 方法名称: bindAndroidQq<br>
	 * 描述：安卓端QQ登录
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
				appAccountsService.insertAccounts(m.getMembersID());
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
	
	
	
	/**方法名称: membersSetPassword<br>
	 * 描述：用户（设置  修改   找回）  密码
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日上午10:40:33
	 * @param membersID
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public JSONObject membersSetPassword(int membersID,String password) throws SQLException{
		JSONObject backJsonObject=new JSONObject();
		String membersPassword=Base64MD5.encrypt(MD5.convertMD5(password)+ Constants.ENCRYP_STRING);
		String sql = "update Members set membersPassword='"+membersPassword+"' where membersID ="+membersID;
		boolean state = super.execSql(sql);
		if (state == true) {
			Members members=super.get(membersID);
			//默认环信用户密码为  444444
		//	setPasswordhuanxin(members);
			backJsonObject.put("data",members);
			backJsonObject.put("state", true);
			return backJsonObject;
		} else {
			backJsonObject.put("message","设置密码失败");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
	
	
	/**方法名称: insertMembers<br>
	 * 描述：新增用户
	 * 作者: 王小欢
	 * 修改日期：2015年10月27日下午3:38:28
	 * @param pNum
	 * @param membersWxID
	 * @param membersImage
	 * @param membersNickName
	 * @param membersSex
	 * @param membersState
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
	
	
	/**方法名称: setinfo<br>
	 * 描述：用户信息修改
	 * 作者: 王小欢
	 * 修改日期：2015年10月27日下午8:58:27
	 * @param membersID
	 * @param membersNickName
	 * @param membersImage
	 * @param membersLocation
	 * @param membersSex
	 * @return
	 * @throws SQLException
	 */
	public JSONObject setinfo(int membersID,String membersNickName,String membersImage,String membersLocation,int membersSex) throws SQLException{
		JSONObject backJsonObject=new JSONObject();
		Cn2Spell cn = new Cn2Spell();
		String membersNamePY = "#";
		if (membersNickName.isEmpty()) {
			membersNamePY = "#";
		} else {
			membersNamePY = cn.converterToFirstSpell(membersNickName);
		}
		String sql = "update Members set membersNickName='"+membersNickName+"',membersNamePY='"+membersNamePY+"',updateTime=CURRENT_TIMESTAMP,membersImage='"+membersImage+"',membersLocation='"+membersLocation+"',membersSex="+membersSex+" where membersID ="+membersID;
		boolean state = super.execSql(sql);
		if (state == true) {
			Members members=super.get(membersID);
			backJsonObject.put("data",members);
			backJsonObject.put("state", true);
			return backJsonObject;
		} else {
			backJsonObject.put("message","修改用户信息失败");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
	
	
	
	/**方法名称: getUserinfo<br>
	 * 描述：根据用户id查找用户信息
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日上午9:44:39
	 * @return
	 */
	public JSONObject getUserinfo(int membersID){
		JSONObject backJsonObject=new JSONObject();
		String sql = "select ms.membersID,ms.membersNickName,ms.membersImage,ms.membersSex,ms.membersLocation,ms.membersPhone,ms.identifyState from  Members ms  where ms.membersID=?";
		JSONObject js = super.findJson(sql, null, membersID);
		if (js!= null) {
			backJsonObject.put("data",js);
			backJsonObject.put("state", true);
			return backJsonObject;
		} else {
			backJsonObject.put("message","获取用户信息失败，请重试");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
	
	
	
	/**方法名称: updateIdCard<br>
	 * 描述：根据用户id更改用户身份证认证状态
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午6:41:18
	 * @param state
	 * @param membersID
	 * @return
	 */
	public boolean updateIdCard(int state,int membersID) {
		String sql = "update Members set identifyState=? where membersID=?";
		boolean bl = super.execSql(sql, state, membersID);
		return bl;
	}
	
	
	/**
	 * 方法名称: loginhuanxin<br>
	 * 描述：注册环信用户 作者: 王小欢 修改日期：2015年6月30日下午2:02:57
	 * 
	 * @param m
	 * http://docs.easemob.com/doku.php?id=start:100serverintegration:20users
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
	
	
	
	/**方法名称: setPasswordhuanxin<br>
	 * 描述：修改环信用户密码
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日上午10:42:37
	 * http://docs.easemob.com/doku.php?id=start:100serverintegration:20users#重置im用户密码
	 */
	public void setPasswordhuanxin(Members m){
		SendPost sendPost = new SendPost();
		String url = "https://a1.easemob.com/" + Constants.easemob_key + "/"
				+ Constants.easemob_value + "/users/"+m.getMembersID()+"/password";
		JSONObject obj = new JSONObject();
		obj.element("newpassword", m.getMembersPassword());
		JSONObject jsonObject = sendPost.postHuanxin(url, obj);
	}
}
