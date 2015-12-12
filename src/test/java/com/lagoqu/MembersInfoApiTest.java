package com.lagoqu;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.spring.test.BaseSpringTest;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.entity.Members;
import com.lagoqu.worldplay.service.AppFriendsService;
import com.lagoqu.worldplay.service.AppIdCardService;
import com.lagoqu.worldplay.service.AppMembersService;
import com.mysql.fabric.xmlrpc.base.Data;

/**描述：用户信息Api测试<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月27日上午11:20:26 <br>
 * E-mail:  <br> 
 */
public class MembersInfoApiTest extends BaseSpringTest{

	@Resource
	AppMembersService appMembersService;
	
	@Resource
	AppFriendsService appFriendsService;
	
	@Resource
	AppIdCardService appIdCardService;
	
	
	/**方法名称: setinfo<br>
	 * 描述：修改用户信息
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日上午9:51:44
	 * @throws IOException
	 * @throws SQLException
	 */
	@Test
	public void setinfo() throws IOException, SQLException{
		int membersID=4;
		String membersNickName="tttttt";
		String membersImage="ttttttttt";
		String membersLocation="tttttt";
		int membersSex=1;
		JSONObject backJsonObject=appMembersService.setinfo(membersID,membersNickName,membersImage,membersLocation,membersSex);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		//	System.out.println(members.getMembersNickName());
		}else{
			System.out.println((backJsonObject.get("message").toString()));
		}
	}
		
		/**方法名称: getUserinfo<br>
		 * 描述：根据用户ＩＤ获取用户信息
		 * 作者: 王小欢
		 * 修改日期：2015年10月28日上午9:52:21
		 * @throws IOException
		 * @throws SQLException
		 */
	@Test
	public void getUserinfo() throws IOException, SQLException{
		int membersID=555555;
		JSONObject backJsonObject=appMembersService.getUserinfo(membersID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println((backJsonObject.get("message").toString()));
		}
	}
	
	
	
	/**方法名称: addfriend<br>
	 * 描述：添加好友
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日上午10:46:53
	 * @throws IOException
	 * @throws SQLException
	 */
	@Test
	public void addfriend() throws IOException, SQLException{
		int membersID=555555;
		int friendUserID=3;
		JSONObject backJsonObject=appFriendsService.addfriend(membersID,friendUserID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println((backJsonObject.get("message").toString()));
		}
	}
	
	
	/**方法名称: getfriendList<br>
	 * 描述：用户获取好友列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日上午10:47:30
	 * @throws IOException
	 * @throws SQLException
	 */
	@Test
	public void getfriendList() throws IOException, SQLException{
		int membersID=9;
		JSONObject backJsonObject=appFriendsService.getfriendList(membersID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println((backJsonObject.get("message").toString()));
		}
	}
	
	
	/**方法名称: seachUserfriendList<br>
	 * 描述：用户搜索本人好友列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午1:43:26
	 * @throws IOException
	 * @throws SQLException
	 */
	@Test
	public void seachUserfriendList() throws IOException, SQLException{
		int membersID=9;
		String  membersNickName="796";
		JSONObject backJsonObject=appFriendsService.seachUserfriendList(membersID,membersNickName);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println((backJsonObject.get("message").toString()));
		}
	}
	
	
	
	/**方法名称: refurbishUserfriendList<br>
	 * 描述：刷新用户好友列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午1:43:26
	 * @throws IOException
	 * @throws SQLException
	 */
	@Test
	public void refurbishUserfriendList() throws IOException, SQLException{
		int membersID=9;
		Timestamp updateTime=new Timestamp(System.currentTimeMillis());
		JSONObject backJsonObject=appFriendsService.refurbishUserfriendList(membersID,updateTime);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println((backJsonObject.get("message").toString()));
		}
	}
	
	
	
	/**方法名称: refurbishUserfriendList<br>
	 * 描述：用户搜索好友列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午1:43:26
	 * @throws IOException
	 * @throws SQLException
	 */
	@Test
	public void seachfriend() throws IOException, SQLException{
		String  membersNickName="123";
		JSONObject backJsonObject=appFriendsService.seachfriend(membersNickName);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println((backJsonObject.get("message").toString()));
		}
	}
	
	
	/**方法名称: userIdCardSubmit<br>
	 * 描述：用户身份证信息提交
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午1:43:26
	 * @throws IOException
	 * @throws SQLException
	 */
	@Test
	public void userIdCardSubmit() throws IOException, SQLException{
		int membersID = 2;
		String idCardName ="王二小";
		String correctSideImage = "xxxx";
		String oppositeSideImage = "yyyyyyy";
		String idCardNum = "123456789";
		JSONObject backJsonObject=appIdCardService.userIdCardSubmit(membersID,idCardName,correctSideImage,oppositeSideImage,idCardNum);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println((backJsonObject.get("message").toString()));
		}
	}
	
	
}
