package com.lagoqu.worldplay.api.page;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.constants.Constants;

@Controller
@Scope("prototype")
@RequestMapping("/messagesPage")
public class MessagesPage extends APIController{
	/**
	 * 方法名称: toMessages<br>
	 * 描述：跳转到消息历史记录
	 * 作者: 邢留杰
	 * 修改日期：2015年6月12日上午10:28:28
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String toMessages() throws Exception{
		if(!super.isMemberLogin()){
			return "redirect:" + Constants.WEIXIN_USERINFO; 
		}
		 super.setRequestAttibute("crrrentMember", super.getMember());
		return "message/message.html";
	}
}
