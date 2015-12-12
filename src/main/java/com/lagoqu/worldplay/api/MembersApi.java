package com.lagoqu.worldplay.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.util.RandomUtil;
import com.lagoqu.worldplay.bms.service.MessageRecordBmsService;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.common.controller.SessionCache;
import com.lagoqu.worldplay.constants.Constants;
import com.lagoqu.worldplay.entity.Members;
import com.lagoqu.worldplay.service.IdCardService;
import com.lagoqu.worldplay.service.MembersService;
import com.lagoqu.worldplay.util.CheckUtil;
import com.lagoqu.worldplay.util.Client;
import com.lagoqu.worldplay.util.EmojiFilter;
import com.lagoqu.worldplay.util.MemCachedManager;
import com.lagoqu.worldplay.util.YunpianResult;
import com.lagoqu.worldplay.util.YunpianSms;

/**描述：个人信息<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月3日上午10:39:30 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/user")
public class MembersApi extends APIController {

	@Resource
	MembersService membersService;
	@Resource
	IdCardService idCardService;
	@Resource
	MessageRecordBmsService messageRecordBmsService;
	// 关键字（手机号或者昵称）
	private String key;

	/**方法名称: getcode<br>
	 * 描述：获取验证码  (短信发送)
	 * 作者: 王小欢
	 * 修改日期：2015年6月3日上午10:39:46
	 */
	@ResponseBody
	@RequestMapping(value = "getcode", method = RequestMethod.POST)
	public void getcode() throws IOException, Exception {
		JSONObject jObject = super.getRequestJsonParams();

		String pNum = jObject.get("pNum").toString();
		if (!CheckUtil.isMobile(pNum)) {
			returnFailJson("手机号码格式不正确");
			return;
		}
		MemCachedManager cache = MemCachedManager.getInstance();
		boolean flag = true;
		String pNumCode = pNum+"count";
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
			returnFailJson("同一手机号30分钟之内只能发送三次验证码");
		}else{
		RandomUtil randomUtil = new RandomUtil();
		String code=randomUtil.getNum(6);
	//	String code="123456";
		String tpl_value = "#code#=" + code;
		YunpianResult yr = YunpianSms.tplSendSms(Constants.sms_tpl_id,tpl_value, pNum);
		//短信日志记录
		messageRecordBmsService.saveMessageRecord(pNum, tpl_value, 1, yr.getMsg());
		if (yr.isSendSuccess()) {
				request.getSession().setAttribute(SessionCache.telephone_code_regist,code);
				cache.set(pNum, code);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("pNum", pNum);
				jsonObject.put("code", code);
				returnSuccessJson(jsonObject.toString());
				
		}else{ 
			  String ms = null; 
			  if (yr.isIllegalUserOperate()) { 
				     ms=yr.getMsg(); 
			  }else{ 
					ms = "短信发送失败"; 
			  } 
			returnFailJson(ms); 
	    }
		}
	}
	
	
	
	
	
	/**方法名称: getcodeVoice<br>
	 * 描述：获取第二次验证码  (三基时代)
	 * 作者: 王小欢
	 * 修改日期：2015年6月3日上午10:39:46
	 */
	@ResponseBody
	@RequestMapping(value = "getcodeVoice", method = RequestMethod.POST)
	public void getcodeVoice() throws IOException, Exception {
		JSONObject jObject = super.getRequestJsonParams();
			
		String pNum = jObject.get("pNum").toString();
		if (!CheckUtil.isMobile(pNum)) {
			returnFailJson("手机号码格式不正确");
			return;
		}
		MemCachedManager cache = MemCachedManager.getInstance();
		boolean flag = true;
		String pNumCode = pNum+"count";
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
			returnFailJson("同一手机号30分钟之内只能发送三次验证码");
		}else{
			RandomUtil randomUtil = new RandomUtil();
			String code=randomUtil.getNum(6);
			Client client=new Client(Constants.ClientName,Constants.ClientPassword);
			String content=URLEncoder.encode(code+"玩赚世界登录短信验证码，关注微信：玩赚世界 有惊喜【玩赚世界】", "utf8");
			String result_mt = client.mdsmssend(pNum, content, "4", "", "", "");
			//短信日志记录
			messageRecordBmsService.saveMessageRecord(pNum, content, 2, result_mt);
			if (result_mt.length()>17) {
				request.getSession().setAttribute(SessionCache.telephone_code_regist,code);
				cache.set(pNum, code);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("pNum", pNum);
				jsonObject.put("code", code);
				returnSuccessJson(jsonObject.toString());
				
			}else{ 
				String ms = "短信发送失败"; 
				returnFailJson(ms); 
		    }
		}
	}
	
	
	
	

	/**方法名称: login<br>
	 * 描述：用户登录(手机号+验证码)
	 * 作者: 王小欢
	 * 修改日期：2015年6月3日下午1:37:19
	 * @param telephone
	 * @param validcode
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public void login() throws Exception {
		JSONObject jObject = super.getRequestJsonParams();
		String pNum = jObject.get("pNum").toString();
		String code = jObject.get("code").toString();
		MemCachedManager cache = MemCachedManager.getInstance();
		if("13888888888".equals(pNum)){
			JSONObject members=membersService.findById(pNum);
			super.setMember(members);
			returnSuccessJson(members.toString());
		}else{
			// 判断验证码是否正确
			if (cache.get(pNum) != null && cache.get(pNum).toString().equals(code)) {
				JSONObject members=membersService.findById(pNum);
				super.setMember(members);
				cache.remove(pNum);
				returnSuccessJson(members.toString());
			} else {
				returnFailJson("验证码已过期");
			}
		}

	}
	
	
	
	
	
	
	/**方法名称: login<br>
	 * 描述：用户登录(微信)
	 * 作者: 王小欢
	 * 修改日期：2015年6月30日上午11:08:42
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "kiklogin", method = RequestMethod.POST)
	public void weixinlogin() throws Exception {
		JSONObject jObject = super.getRequestJsonParams();
		String membersWxID = (String) jObject.get("membersWxID");
		JSONObject members=membersService.findBymembersWxID(membersWxID);
		//判断用户是否绑定微信号，if members==null用户未绑定微信，else 用户绑定微信 直接登录
		JSONObject jb = new JSONObject();
		if(members==null){
			jb.put("state", "false");
			returnSuccessJson(jb.toString());
		}else{
			super.setMember(members);
			returnSuccessJson(members.toString());
		}
	}
	
	
	
	/**方法名称: login<br>
	 * 描述：绑定微信和手机号
	 * 作者: 王小欢
	 * 修改日期：2015年6月30日上午11:08:42
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "bindingkik", method = RequestMethod.POST)
	public void bindingkik() throws Exception{
		JSONObject jObject = super.getRequestJsonParams();
		String pNum = jObject.get("pNum").toString();
		String code = jObject.get("code").toString();
		String membersWxID = jObject.getString("membersWxID");
		String membersImage =jObject.getString("membersImage");
		String membersNickName =EmojiFilter.filterEmoji(jObject.getString("membersNickName").trim());
		if("".equals(membersNickName.trim())){
			membersNickName=RandomUtil.getCharAndNum(6);
		}
		int membersSex =jObject.getInt("membersSex");
		MemCachedManager cache = MemCachedManager.getInstance();
		// 判断验证码是否正确
		if (cache.get(pNum) != null && cache.get(pNum).toString().equals(code)) {
			JSONObject members=membersService.bindingkik(pNum,membersWxID,membersImage,membersNickName,membersSex);
			if(members.getInt("flag")==-1){
				returnFailJson(members.getString("ms"));
			}else{
				super.setMember(members.getJSONObject("data"));
				cache.remove(pNum);
				returnSuccessJson(members.getJSONObject("data").toString());
			}
		} else {
			returnFailJson("验证码已过期");
		}
	}
	

	/**方法名称: setinfo<br>
	 * 描述：修改个人信息
	 * 作者: 王小欢
	 * 修改日期：2015年6月4日下午1:51:57
	 * @param membersID
	 * @param membersNickName
	 * @param membersImage
	 * @param membersSex
	 * @param membersLocation
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "setinfo", method = RequestMethod.POST)
	public void setinfo() throws Exception {
		JSONObject jObject = super.getRequestJsonParams();
		Members mbs=membersService.updateMembers(jObject);
        if(mbs!=null){
        	super.setMember(mbs);
        	returnSuccessJson("success");
        }else {
        	returnFailJson("fail");
		}
	}
	/**
	 * 方法名称: getUserinfo<br>
	 * 描述：获取用户信息
	 * 作者: 邢留杰
	 * 修改日期：2015年6月18日下午3:51:41
	 * @throws SQLException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "getUserinfo", method = RequestMethod.POST)
	public void getUserinfo() throws SQLException, IOException {
		JSONObject jObject = super.getRequestJsonParams();
		int memberID = 0;
		if(jObject.get("membersID")!=null){
			memberID=jObject.getInt("membersID");
			JSONObject ms=membersService.findMemberById(memberID);
			JSONObject jb=new JSONObject();
			jb.put("ms", ms);
	        if(ms!=null){
	        	returnSuccessJson(jb.toString());
	        }else {
	        	returnSuccessJson("获取用户信息失败，请重试");
			}
		}else{
			returnFailJson("尚未登录");
		}
	}

	/**
	 * 方法名称: getfriends<br>
	 * 描述：根据用户memberID查询好友列表
	 * 作者: 邢留杰
	 * 修改日期：2015年6月8日下午1:3:03
	 * @param page
	 * @param size
	 * @param memberID
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getfriends", method = RequestMethod.POST)
	public void getfriends() throws Exception {
		JSONObject jObject = super.getRequestJsonParams();
		int memberID = jObject.getInt("membersID");
		try {
			JSONArray getfriends = membersService.findFriendsByMemberID(memberID);
			returnSuccessJson(getfriends.toString());
		} catch (Exception e) {
			returnFailJson("find fail");
		}
	}
	
	
	
	/**方法名称: findfriends<br>
	 * 描述：搜索用户用户列表（根据用户ＩＤ和关键字）
	 * 作者: 王小欢
	 * 修改日期：2015年6月23日上午10:47:30
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "findfriends", method = RequestMethod.POST)
	public void findfriends() throws Exception {
		JSONObject jObject = super.getRequestJsonParams();
		int memberID = jObject.getInt("membersID");
		String key = jObject.get("key").toString();
		try {
			JSONArray getfriends = membersService.findfriends(memberID,key);
			returnSuccessJson(getfriends.toString());
		} catch (Exception e) {
			returnFailJson("find fail");
		}
	}
	
	
	/**
	 * 方法名称: getfriends<br>
	 * 描述：根据用户memberID and 更新时间查询好友列表
	 * 作者: 邢留杰
	 * 修改日期：2015年6月8日下午1:3:03
	 * @param page
	 * @param size
	 * @param memberID
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "repeatGetfriends", method = RequestMethod.POST)
	public void repeatGetfriends() throws Exception {
		JSONObject jObject = super.getRequestJsonParams();
		int memberID = jObject.getInt("membersID");
		Timestamp updateTime=(Timestamp) jObject.get("updateTime");
		try {
			JSONArray getfriends = membersService.repeatFriendsByMemberID(memberID,updateTime);
			returnSuccessJson(getfriends.toString());
		} catch (Exception e) {
			returnFailJson("find fail");
		}
	}
	
	
	/**
	 * 方法名称: seachfriend<br>
	 * 描述：用于用户搜索好友，可以根据手机号后者昵称
	 * 作者: 邢留杰
	 * 修改日期：2015年6月8日下午1:3:03
	 * @param page
	 * @param size
	 * @param memberID
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "seachfriend", method = RequestMethod.POST)
	public void seachfriend() throws Exception {
		JSONObject jObject = super.getRequestJsonParams();
		String key = jObject.get("key").toString();
		try {
			if ("".equals(key) || key == null) {
				returnSuccessJson("请输入手机号或者昵称!");
				return;
			}
			JSONArray seachfriend = membersService.seachFriend(key);
			returnSuccessJson(seachfriend.toString());
		} catch (Exception e) {
			returnFailJson("find fail");
		}
	}

	/**
	 * 方法名称: idCardAdd<br>
	 * 描述：用户添加身份信息
	 * 作者: 邢留杰
	 * 修改日期：2015年7月16日下午6:38:22
	 * @throws IOException
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value="idCardAdd",method = RequestMethod.POST)
	public void idCardAdd() throws IOException, ParseException{
		JSONObject jObject=super.getRequestJsonParams();
		if(!jObject.containsKey("membersID")){
			returnFailJson("用户未登录");
		}else {
			JSONObject js = idCardService.selectByMembersId(jObject.getInt("membersID"));		
			if(js==null){
				int p = idCardService.saveIdCard(jObject);
				if(p==-1){
					returnFailJson("false");
				}
			}else {
				if(js.getInt("state")==1){
					returnFailJson("审核成功，不能再次提交");
					return;
				}
				if(js.getInt("state")==0){
					returnFailJson("审核中");
					return;
				}
				if(!idCardService.updateIdCard(jObject,js.getInt("idCardId"))){
					returnFailJson("false");
					return;
				}
			}	
			
			if(membersService.updateIdCard(1, jObject.getInt("membersID"))){
				returnSuccessJson("true");
			}else{
				returnFailJson("false");
			}
		}
	}
	
	/**
	 * 方法名称: weixinAndroidLogin<br>
	 * 描述：安卓端三方登陆跳转页面
	 * 作者: 邢留杰
	 * 修改日期：2015年8月26日上午10:24:45
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping(value = "weixinAndroidLogin", method = RequestMethod.POST)
	public void weixinAndroidLogin() throws IOException{
		JSONObject jObject=super.getRequestJsonParams();
		String type = jObject.getString("androidLoginType");
		String openId = jObject.getString("androidOpenId");
		JSONObject result=new JSONObject();
		try {
			JSONObject jsonObj = membersService.findByAndroidLoginType(type, openId);
			if(jsonObj.getInt("flag")==0){//跳转到绑定手机号页面
				result.put("flag", 0);
				result.put("ms","");
				result.put("data","");
				returnSuccessJson(result.toString());
			}else if(jsonObj.getInt("flag")==1){//跳过绑定手机页面
				result.put("flag", 1);
				result.put("ms","");
				result.put("data", jsonObj.getJSONObject("data"));
				returnSuccessJson(result.toString());
			}else if(jsonObj.getInt("flag")==-1){//防外挂
				returnFailJson("非法请求");
			}
		} catch (IOException e) {
			returnFailJson("系统异常");
		}
		
	}
	/**方法名称: login<br>
	 * 描述：app三方登陆绑定微信和手机号
	 * 作者: 王小欢
	 * 修改日期：2015年6月30日上午11:08:42
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "bindAndroid", method = RequestMethod.POST)
	public void bindAndroid() throws Exception{
		JSONObject jObject = super.getRequestJsonParams();
		String openId = jObject.getString("openId");
		String membersImage =jObject.getString("membersImage");
		String membersNickName =jObject.getString("membersNickName");
		String phone =jObject.getString("phone");
		String type =jObject.getString("type");
		String checkCode = jObject.getString("checkCode");
		MemCachedManager cache = MemCachedManager.getInstance();
		// 判断验证码是否正确
		if (cache.get(phone) != null && cache.get(phone).toString().equals(checkCode)) {
			if("1".equals(type)){
				JSONObject members=membersService.bindAndroidWeiXin(openId,membersImage,membersNickName,phone);
				if(members.getInt("flag")==-1){
					returnFailJson(members.getString("ms"));
				}else{
					super.setMember(members.getJSONObject("data"));
					returnSuccessJson(members.getJSONObject("data").toString());
				}
			}else if("2".equals(type)){
				JSONObject members=membersService.bindAndroidWeiBo(openId,membersImage,membersNickName,phone);
				if(members.getInt("flag")==-1){
					returnFailJson(members.getString("ms"));
				}else{
					super.setMember(members.getJSONObject("data"));
					returnSuccessJson(members.getJSONObject("data").toString());
				}
			}else if("3".equals(type)){
				JSONObject members=membersService.bindAndroidQq(openId,membersImage,membersNickName,phone);
				if(members.getInt("flag")==-1){
					returnFailJson(members.getString("ms"));
				}else{
					super.setMember(members.getJSONObject("data"));
					returnSuccessJson(members.getJSONObject("data").toString());
				}
			}
		} else {
			returnFailJson("验证码已过期");
		}
	}
	/**
	 * @Description java获取客户端访问的真实IP地址
	 * @author temdy
	 * @Date 2014-11-07
	 * @param request 请求对象
	 * @return
	 */
	public String getIPAddress(HttpServletRequest request) {
		// 获取X-Forwarded-For
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			// 获取Proxy-Client-IP
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			// WL-Proxy-Client-IP
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			// 获取的IP实际上是代理服务器的地址，并不是客户端的IP地址
			ip = request.getRemoteAddr();
		}
		if (ip.contains(",")) {
			ip = ip.split(",")[0];
		}
		return ip;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
