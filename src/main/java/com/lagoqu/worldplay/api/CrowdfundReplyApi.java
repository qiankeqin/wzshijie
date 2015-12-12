package com.lagoqu.worldplay.api;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.entity.CrowdfundReply;
import com.lagoqu.worldplay.service.CrowdfundReplyService;

@Controller
@Scope("prototype")
@RequestMapping("/helpReplay")
public class CrowdfundReplyApi extends APIController{
	@Resource
	CrowdfundReplyService crowdfundReplyService;
	/**
	 * 方法名称: delete<br>
	 * 描述：删除评论
	 * 作者: 邢留杰
	 * 修改日期：2015年8月28日下午3:51:42
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="delete",method = RequestMethod.POST)
	public void delete() throws Exception{
		try{
			JSONObject jObject=super.getRequestJsonParams();
			int crowdfundReplyID=jObject.getInt("crowdfundReplyID");
			boolean bl = crowdfundReplyService.delete(crowdfundReplyID);
			JSONObject jsonObject = new JSONObject();  
		    jsonObject.put("flag",bl);  
			returnSuccessJson(jsonObject.toString());
		}catch(Exception e){
			returnFailJson("系统异常，删除失败");
		}
	}
	/**
	 * 方法名称: add<br>
	 * 描述：新增一条评论
	 * 作者: 邢留杰
	 * 修改日期：2015年8月28日下午4:30:32
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="add",method = RequestMethod.POST)
	public void add() throws Exception{
		try{
			JSONObject jObject=super.getRequestJsonParams();
			CrowdfundReply crowdfundReply = (CrowdfundReply)JSONObject.toBean(jObject, CrowdfundReply.class);
			int bl = crowdfundReplyService.addCrowfundReply(crowdfundReply);
			JSONObject jsonObject = new JSONObject();  
		    jsonObject.put("flag",bl);  
			returnSuccessJson(jsonObject.toString());
		}catch(Exception e){
			returnFailJson("系统异常，新增失败");
		}
	}
	
}
