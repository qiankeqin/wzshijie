package com.lagoqu.worldplay.api;

import java.io.IOException;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.service.FriendsService;
/**描述：好友关系<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月8日上午11:11:22 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/friends")
public class FriendsApi extends APIController{

	
	@Resource
	FriendsService friendsService;
	
	
	
	@ResponseBody
	@RequestMapping(value="addfriend",method = RequestMethod.POST)
	public void addfriend() throws IOException{
		JSONObject jObject=super.getRequestJsonParams();
		boolean addfriendstate=friendsService.insertAddfriend(jObject);
		if(addfriendstate==true){
			returnSuccessJson("true");
		}else{
			returnFailJson("false");
		}
	}
}
