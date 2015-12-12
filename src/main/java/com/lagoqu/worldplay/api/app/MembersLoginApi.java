package com.lagoqu.worldplay.api.app;

import java.io.IOException;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.lagoqu.common.framework.controller.TimestampEditor;
import com.lagoqu.common.framework.controller.UtilDateEditor;
import com.lagoqu.common.security.Base64MD5;
import com.lagoqu.common.security.MD5;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.common.controller.SessionCache;
import com.lagoqu.worldplay.constants.Constants;
import com.lagoqu.worldplay.entity.Members;
import com.lagoqu.worldplay.service.AppMembersService;
import com.lagoqu.worldplay.util.MemCachedManager;

/**描述：用户登录Api<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月27日上午11:17:45 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/MemberLogin")
public class MembersLoginApi extends APIController{

	
	@Resource
	AppMembersService appMembersService;
	

	
	/**方法名称: getVerificationCode<br>
	 * 描述：获取验证码  (短信发送)
	 * 用户登录，服务器发送验证码。
	 * 作者: 王小欢  
	 * 修改日期：2015年10月27日下午1:11:10
     * @param codeType   1 登录 2 注册  3绑定  4找回密码
	 * @throws IOException
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getVerificationCode", method = RequestMethod.POST)
	public void getVerificationCode() throws IOException, Exception { 
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		String membersPhone = receivejJsonObject.get("membersPhone").toString();
		int codeType=receivejJsonObject.getInt("codeType");
		JSONObject backJsonObject=appMembersService.getVerificationCode(membersPhone,codeType);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("membersPhone", membersPhone);
			jsonObject.put("membersID", backJsonObject.get("membersID"));
			jsonObject.put("code", backJsonObject.get("data"));
			request.getSession().setAttribute(SessionCache.telephone_code_regist,backJsonObject.get("data"));
			returnSuccessJson(jsonObject.toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	/**方法名称: membersRegister<br>
	 * 描述：用户注册(手机号+验证码)
	 * 作者: 王小欢
	 * 修改日期：2015年11月9日下午3:32:56
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "membersRegister", method = RequestMethod.POST)
	public void membersRegister() throws Exception{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		String membersPhone = receivejJsonObject.get("membersPhone").toString();
		String code = receivejJsonObject.get("code").toString();
		JSONObject backJsonObject=appMembersService.membersRegister(membersPhone,code);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	/**方法名称: membersLoginCode<br>
	 * 描述：用户登录(手机号+验证码)
	 * 作者: 王小欢
	 * 修改日期：2015年10月27日下午3:07:41
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "membersLoginCode", method = RequestMethod.POST)
	public void membersLoginCode() throws Exception{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		String membersPhone = receivejJsonObject.get("membersPhone").toString();
		String code = receivejJsonObject.get("code").toString();
		JSONObject backJsonObject=appMembersService.membersLoginCode(membersPhone,code);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	/**方法名称: membersLoginPass<br>
	 * 描述：用户登录(手机号+密码)
	 * 作者: 王小欢
	 * 修改日期：2015年10月27日下午3:07:41
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "membersLoginPass", method = RequestMethod.POST)
	public void membersLoginPass() throws Exception{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		String membersPhone = receivejJsonObject.get("membersPhone").toString();
		String password = receivejJsonObject.get("password").toString();
		JSONObject backJsonObject=appMembersService.membersLoginPass(membersPhone,Base64MD5.encrypt(MD5.convertMD5(password)+ Constants.ENCRYP_STRING));
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	/**方法名称: subsidiaryLogin<br>
	 * 描述：用户第三方工具登录(QQ，微信，微博)
	 * 1 微信 2 微博 3 qq
	 * 作者: 王小欢
	 * 修改日期：2015年10月27日下午3:07:41
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "subsidiaryLogin", method = RequestMethod.POST)
	public void subsidiaryLogin() throws Exception{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		String loginType = receivejJsonObject.getString("LoginType");
		String openId = receivejJsonObject.getString("OpenId");
		JSONObject backJsonObject=appMembersService.subsidiaryLogin(loginType,openId);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "subsidiaryRegister", method = RequestMethod.POST)
	public void subsidiaryRegister() throws Exception{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		String type = receivejJsonObject.getString("LoginType");
		String openId = receivejJsonObject.getString("OpenId");
		String membersImage =receivejJsonObject.getString("membersImage");
		String membersNickName =receivejJsonObject.getString("membersNickName");
		String phone =receivejJsonObject.getString("phone");
		String checkCode = receivejJsonObject.getString("checkCode");
		MemCachedManager cache = MemCachedManager.getInstance();
		// 判断验证码是否正确
		if (cache.get(phone) != null && cache.get(phone).toString().equals(checkCode)) {
			if("1".equals(type)){
				JSONObject members=appMembersService.bindAndroidWeiXin(openId,membersImage,membersNickName,phone);
				if(members.getInt("flag")==-1){
					returnFailJson(members.getString("ms"));
				}else{
					super.setMember(members.getJSONObject("data"));
					returnSuccessJson(members.getJSONObject("data").toString());
				}
			}else if("2".equals(type)){
				JSONObject members=appMembersService.bindAndroidWeiBo(openId,membersImage,membersNickName,phone);
				if(members.getInt("flag")==-1){
					returnFailJson(members.getString("ms"));
				}else{
					super.setMember(members.getJSONObject("data"));
					returnSuccessJson(members.getJSONObject("data").toString());
				}
			}else if("3".equals(type)){
				JSONObject members=appMembersService.bindAndroidQq(openId,membersImage,membersNickName,phone);
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
	
	
	/**方法名称: membersSetPassword<br>
	 * 描述：用户（设置  修改   找回）  密码
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日上午10:49:30
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "membersSetPassword", method = RequestMethod.POST)
	public void membersSetPassword() throws Exception{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int membersID = receivejJsonObject.getInt("membersID");
		String password = receivejJsonObject.get("password").toString();
		JSONObject backJsonObject=appMembersService.membersSetPassword(membersID,password);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
}
