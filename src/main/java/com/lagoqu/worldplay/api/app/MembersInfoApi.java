package com.lagoqu.worldplay.api.app;

import java.io.IOException;
import java.sql.Timestamp;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.service.AppFriendsService;
import com.lagoqu.worldplay.service.AppIdCardService;
import com.lagoqu.worldplay.service.AppMembersService;

/**描述：用户信息Api<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月27日上午11:20:26 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/MembersInfo")
public class MembersInfoApi extends APIController{

	@Resource
	AppMembersService appMembersService;
	
	@Resource
	AppFriendsService appFriendsService;
	
	@Resource
	AppIdCardService appIdCardService;
	
	/**方法名称: setinfo<br>
	 * 描述：用户信息修改
	 * 作者: 王小欢
	 * 修改日期：2015年10月27日下午8:58:06
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "setinfo", method = RequestMethod.POST)
	public void setinfo() throws Exception{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int membersID=receivejJsonObject.getInt("membersID");
		String membersNickName=receivejJsonObject.getString("membersNickName");
		String membersImage=receivejJsonObject.getString("membersImage");
		String membersLocation=receivejJsonObject.getString("membersLocation");
		int membersSex=receivejJsonObject.getInt("membersSex");
		JSONObject backJsonObject=appMembersService.setinfo(membersID,membersNickName,membersImage,membersLocation,membersSex);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			super.setMember(JSONObject.fromObject(backJsonObject.get("data").toString()));
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	/**方法名称: getUserinfo<br>
	 * 描述：根据用户ID获取用户信息
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日上午9:47:54
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getUserinfo", method = RequestMethod.POST)
	public void getUserinfo() throws Exception{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int membersID=receivejJsonObject.getInt("membersID");
		JSONObject backJsonObject=appMembersService.getUserinfo(membersID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	/**方法名称: addfriend<br>
	 * 描述：用户进行好友添加
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日上午10:04:00
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="addfriend",method = RequestMethod.POST)
	public void addfriend() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int membersID=receivejJsonObject.getInt("membersID");
		int friendUserID=receivejJsonObject.getInt("friendUserID");
		JSONObject backJsonObject=appFriendsService.addfriend(membersID,friendUserID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	/**方法名称: getfriendList<br>
	 * 描述：用户获取好友列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日上午10:45:37
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="getfriendList",method = RequestMethod.POST)
	public void getfriendList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int membersID=receivejJsonObject.getInt("membersID");
		JSONObject backJsonObject=appFriendsService.getfriendList(membersID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	/**方法名称: seachUserfriendList<br>
	 * 描述：用户搜索本人好友列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日上午10:45:37
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="seachUserfriendList",method = RequestMethod.POST)
	public void seachUserfriendList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int membersID=receivejJsonObject.getInt("membersID");
		String  membersNickName=receivejJsonObject.getString("membersNickName");
		JSONObject backJsonObject=appFriendsService.seachUserfriendList(membersID,membersNickName);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	/**方法名称: refurbishUserfriendList<br>
	 * 描述：刷新用户好友列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午1:49:05
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="refurbishUserfriendList",method = RequestMethod.POST)
	public void refurbishUserfriendList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int membersID=receivejJsonObject.getInt("membersID");
		Timestamp updateTime=(Timestamp) receivejJsonObject.get("updateTime");
		JSONObject backJsonObject=appFriendsService.refurbishUserfriendList(membersID,updateTime);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	/**方法名称: seachfriend<br>
	 * 描述：用户搜索好友列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午1:49:05
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="seachfriend",method = RequestMethod.POST)
	public void seachfriend() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		String  membersNickName=receivejJsonObject.getString("membersNickName");
		JSONObject backJsonObject=appFriendsService.seachfriend(membersNickName);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	/**方法名称: userIdCardSubmit<br>
	 * 描述：用户身份证信息提交
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午6:51:11
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="userIdCardSubmit",method = RequestMethod.POST)
	public void userIdCardSubmit() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int membersID = receivejJsonObject.getInt("membersID");
		String idCardName = receivejJsonObject.getString("idCardName");
		String correctSideImage = receivejJsonObject.getString("correctSideImage");
		String oppositeSideImage = receivejJsonObject.getString("oppositeSideImage");
		String idCardNum = receivejJsonObject.getString("idCardNum");
		JSONObject backJsonObject=appIdCardService.userIdCardSubmit(membersID,idCardName,correctSideImage,oppositeSideImage,idCardNum);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
}
