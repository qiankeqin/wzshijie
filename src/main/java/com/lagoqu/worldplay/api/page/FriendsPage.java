package com.lagoqu.worldplay.api.page;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.constants.Constants;
import com.lagoqu.worldplay.entity.Members;
import com.lagoqu.worldplay.service.MembersService;

@Controller
@Scope("prototype")
@RequestMapping("/friendsPage")
public class FriendsPage extends APIController{
	@Resource
	MembersService membersService;
	
	/**
	 * 方法名称: toFriends<br>
	 * 描述：跳转到好友模块
	 * 作者: 邢留杰
	 * 修改日期：2015年6月12日上午10:20:18
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String toFriends() throws Exception{
		if(!super.isMemberLogin()){
			return "redirect:" + Constants.WEIXIN_USERINFO; 
		}
		Members members=super.getMember();
		
		System.out.println(members.getMembersID());
		super.setRequestAttibute("crrrentMember", super.getMember());
		return "friends/myfriends.html";
	}
	/**
	 * 方法名称: toTalk<br>
	 * 描述：跳转到聊天界面
	 * 作者: 邢留杰
	 * 修改日期：2015年6月16日上午11:24:08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toTalk/{membersID}", method = RequestMethod.GET)
	public String toTalk(@PathVariable("membersID") int membersID) throws Exception{
		if(!super.isMemberLogin()){
			return "redirect:" + Constants.WEIXIN_USERINFO; 
		}
		super.setRequestAttibute("crrrentMember", super.getMember());
		Members members=membersService.get(membersID);
		super.setRequestAttibute("mb", members);
		return "my/talk.html";
	}
}
