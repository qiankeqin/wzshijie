package com.lagoqu.worldplay.bms.ajax;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.bms.service.MembersBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/members")
public class MembersBmsAjax extends BMSController{

	@Resource
	MembersBmsService membersBmsService;
	@ResponseBody
	@RequestMapping(value="list",method = RequestMethod.POST)
	public void findPageData() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		String membersNickName=map.get("membersNickName")[0];
		String membersPhone=map.get("membersPhone")[0];
		Pagination<JSONArray> pagination=membersBmsService.membersList(page,size,membersPhone,membersNickName);
		returnJSONEasyUISuccess(pagination);
	}
}
