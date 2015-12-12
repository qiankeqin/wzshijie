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
import com.lagoqu.worldplay.entity.CrowdfundReply;
import com.lagoqu.worldplay.entity.OrderInfo;
import com.lagoqu.worldplay.entity.ReportRecord;
import com.lagoqu.worldplay.service.AppCrowdfundAttentionService;
import com.lagoqu.worldplay.service.AppCrowdfundReplyService;
import com.lagoqu.worldplay.service.AppCrowdfundService;
import com.lagoqu.worldplay.service.AppOrderInfoService;
import com.lagoqu.worldplay.service.AppReportRecordService;
import com.lagoqu.worldplay.util.EmojiFilter;
/**描述：信息动作Api
 * 修改，删除，打赏，评论，关注，举报<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月27日上午11:33:05 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/CrowdfundMovementApi")
public class CrowdfundMovementApi extends APIController{

	@Resource
	AppCrowdfundAttentionService appCrowdfundAttentionService;
	
	@Resource
	AppCrowdfundService appCrowdfundService;
	
	
	@Resource
	AppCrowdfundReplyService appCrowdfundReplyService;
	
	
	@Resource
	AppReportRecordService appReportRecordService;
	
	
	@Resource
	AppOrderInfoService appOrderInfoService;
	
	
	
	/**方法名称: CrowdfundReleaseUpdate<br>
	 * 描述：修改心愿或足迹
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日下午4:37:03
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundReleaseUpdate",method = RequestMethod.POST)
	public void CrowdfundReleaseUpdate() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int crowdfundID=receivejJsonObject.getInt("crowdfundID");
		int crowdfundType=receivejJsonObject.getInt("crowdfundType");
		String crowdfundDesc=receivejJsonObject.getString("crowdfundDesc");
		String crowdfundPath=receivejJsonObject.getString("crowdfundPath");
		String crowdfundPic=receivejJsonObject.getString("crowdfundPic");
		String crowdfundTitle=receivejJsonObject.getString("crowdfundTitle");
		JSONArray detail =receivejJsonObject.getJSONArray("detail");
		JSONObject backJsonObject=appCrowdfundService.CrowdfundReleaseUpdate(crowdfundID,crowdfundType,crowdfundDesc,crowdfundPath,crowdfundPic,crowdfundTitle,detail);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	/**方法名称: CrowdfundReleaseDelete<br>
	 * 描述：删除信息
	 * * 用于删除信息，如果已经有人打赏过，不能删除
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日下午5:36:43
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundReleaseDelete",method = RequestMethod.POST)
	public void CrowdfundReleaseDelete() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int crowdfundID=receivejJsonObject.getInt("crowdfundID");
		JSONObject backJsonObject=appCrowdfundService.CrowdfundReleaseDelete(crowdfundID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	/**方法名称: CrowdfundReleaseReward<br>
	 * 描述：心愿或足迹打赏创建订单
	 * 作者: 王小欢
	 * 修改日期：2015年11月4日下午1:45:53
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundReleaseReward",method = RequestMethod.POST)
	public void CrowdfundReleaseReward() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		String orderMemo = EmojiFilter.filterEmoji(receivejJsonObject.getString("orderMemo").trim());
		receivejJsonObject.put("orderMemo", orderMemo);
		OrderInfo orderInfo=(OrderInfo) receivejJsonObject.toBean(receivejJsonObject, OrderInfo.class);
		JSONObject backJsonObject=appOrderInfoService.CrowdfundReleaseReward(orderInfo);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	/**方法名称: CrowdfundReleaseComment<br>
	 * 描述：心愿或足迹评论
	 * 作者: 王小欢
	 * 修改日期：2015年11月3日上午10:17:21
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundReleaseComment",method = RequestMethod.POST)
	public void CrowdfundReleaseComment() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		CrowdfundReply crowdfundReply = (CrowdfundReply)JSONObject.toBean(receivejJsonObject, CrowdfundReply.class);
		JSONObject backJsonObject=appCrowdfundReplyService.CrowdfundReleaseComment(crowdfundReply);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	/**方法名称: CrowdfundReleaseCommentDel<br>
	 * 描述：打赏评论删除
	 * 作者: 王小欢
	 * 修改日期：2015年11月3日上午11:06:48
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundReleaseCommentDel",method = RequestMethod.POST)
	public void CrowdfundReleaseCommentDel() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int crowdfundReplyID=receivejJsonObject.getInt("crowdfundReplyID");
		JSONObject backJsonObject=appCrowdfundReplyService.CrowdfundReleaseCommentDel(crowdfundReplyID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	/**方法名称: CrowdfundReleaseAttention<br>
	 * 描述：心愿或足迹关注
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日下午3:08:04
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundReleaseAttention",method = RequestMethod.POST)
	public void CrowdfundReleaseAttention() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int crowdfundID=receivejJsonObject.getInt("crowdfundID");
		int membersID=receivejJsonObject.getInt("membersID");
		JSONObject backJsonObject=appCrowdfundAttentionService.CrowdfundReleaseAttention(crowdfundID,membersID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	
	/**方法名称: CrowdfundReleaseReport<br>
	 * 描述：心愿或足迹举报
	 * 作者: 王小欢
	 * 修改日期：2015年11月3日下午7:57:23
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundReleaseReport",method = RequestMethod.POST)
	public void CrowdfundReleaseReport() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		ReportRecord reportRecord = (ReportRecord)JSONObject.toBean(receivejJsonObject, ReportRecord.class);
		JSONObject backJsonObject=appReportRecordService.CrowdfundReleaseReport(reportRecord);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
}
