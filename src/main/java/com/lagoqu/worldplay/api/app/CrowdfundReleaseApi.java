package com.lagoqu.worldplay.api.app;

import java.io.IOException;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.service.AppCrowdfundService;

/**描述：信息发布Api
 * 发布心愿，足迹<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月27日上午11:28:52 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/CrowdfundReleaseApi")
public class CrowdfundReleaseApi extends APIController{

	
	@Resource
	AppCrowdfundService appCrowdfundService;
	
	
	/**方法名称: CrowdfundReleaseWish<br>
	 * 描述：心愿发布
	 * 作者: 王小欢
	 * 修改日期：2015年10月30日下午2:11:10
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundReleaseWish",method = RequestMethod.POST)
	public void CrowdfundReleaseWish() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int membersID=receivejJsonObject.getInt("membersID");
		String crowdfundDesc=receivejJsonObject.getString("crowdfundDesc");
		String crowdfundPath=receivejJsonObject.getString("crowdfundPath");
		String crowdfundPic=receivejJsonObject.getString("crowdfundPic");
		int crowdfundDays=receivejJsonObject.getInt("crowdfundDays");
		JSONObject backJsonObject=appCrowdfundService.CrowdfundReleaseWish(membersID,crowdfundDesc,crowdfundPath,crowdfundPic,crowdfundDays);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	/**方法名称: CrowdfundWishLabel<br>
	 * 描述：心愿添加标签
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日上午10:27:23
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundWishLabel",method = RequestMethod.POST)
	public void CrowdfundWishLabel() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int crowdfundID=receivejJsonObject.getInt("crowdfundID");
		String crowdfundLabel=receivejJsonObject.getString("crowdfundLabel");
		JSONObject backJsonObject=appCrowdfundService.CrowdfundWishLabel(crowdfundID,crowdfundLabel);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	/**方法名称: CrowdfundReleaseSpoor<br>
	 * 描述：足迹发布
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日下午1:33:18
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundReleaseSpoor",method = RequestMethod.POST)
	public void CrowdfundReleaseSpoor() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int membersID=receivejJsonObject.getInt("membersID");
		String crowdfundTitle=receivejJsonObject.getString("crowdfundTitle");
		String crowdfundDesc=receivejJsonObject.getString("crowdfundDesc");
		String crowdfundPath=receivejJsonObject.getString("crowdfundPath");
		JSONArray detail =receivejJsonObject.getJSONArray("detail");
		JSONObject backJsonObject=appCrowdfundService.CrowdfundReleaseSpoor(membersID,crowdfundTitle,crowdfundDesc,crowdfundPath,detail);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
}
