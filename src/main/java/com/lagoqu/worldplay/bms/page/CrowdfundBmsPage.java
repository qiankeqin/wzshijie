package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.api.CrowdfundAttentionApi;
import com.lagoqu.worldplay.bms.service.CrowdfundBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.Crowdfund;
import com.lagoqu.worldplay.service.CrowdfundAttentionService;
@Controller
@Scope("prototype")
@RequestMapping("/bms/crowdfund")
public class CrowdfundBmsPage extends BMSController{

	@Resource
	CrowdfundBmsService crowdfundBmsService;
	
	@Resource
	CrowdfundAttentionService crowdfundAttentionService;
	
	
	@RequestMapping("list")
	public String undisposed() throws Exception{
		
		return "bms/crowdfund/crowdfundList.html";
	}
	
	
	
	@RequestMapping("crowdfundRdList")
	public String crowdfundRdList() throws Exception{
		
		return "bms/crowdfund/crowdfundRdList.html";
	}
	
	
	@RequestMapping("crowdfundDel")
	public String crowdfundDel() throws Exception{
		
		return "bms/crowdfund/crowdfundDelList.html";
	}
	
	
	
	@RequestMapping("{crowdfundID}/edit")
	public String edit(@PathVariable("crowdfundID") int crowdfundID) throws Exception {
		Crowdfund crowdfund=crowdfundBmsService.get(crowdfundID);
		super.setRequestAttibute("crowdfund", crowdfund);
		return "bms/crowdfund/crowdfund_update.html";
	}
	
	
	@RequestMapping("{crowdfundID}/details")
	public String details(@PathVariable("crowdfundID") int crowdfundID) throws Exception {
		Crowdfund crowdfund=crowdfundBmsService.get(crowdfundID);
		String sql="select * from CrowdfundAttention where crowdfundID=?";
		JSONArray jsonArray=crowdfundAttentionService.findJsonArray(sql, null, crowdfundID);
		super.setRequestAttibute("AttentionCount", jsonArray.size());
		super.setRequestAttibute("crowdfund", crowdfund);
		return "bms/crowdfund/crowdfund_details.html";
	}
	
	
	/**方法名称: bmsUndisposed<br>
	 * 描述：后台发布助理
	 * 作者: 王小欢
	 * 修改日期：2015年8月4日上午11:00:08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("Bmslist")
	public String bmsUndisposed() throws Exception{
		
		return "bms/bmsCf/crowdfundList.html";
	}
	
	
	@RequestMapping("addBms")
	public String editBms() throws Exception {
		return "bms/bmsCf/crowdfund_add.html";
	}
	
	
	
	@RequestMapping("{crowdfundID}/concernBms")
	public String concernBms(@PathVariable("crowdfundID") int crowdfundID) throws Exception {
		Crowdfund crowdfund=crowdfundBmsService.get(crowdfundID);
		super.setRequestAttibute("crowdfund", crowdfund);
		return "bms/bmsCf/crowdfund_concern.html";
	}
	
	
	
	@RequestMapping("{crowdfundID}/markBms")
	public String markBms(@PathVariable("crowdfundID") int crowdfundID) throws Exception {
		Crowdfund crowdfund=crowdfundBmsService.get(crowdfundID);
		super.setRequestAttibute("crowdfund", crowdfund);
		return "bms/bmsCf/crowdfund_mark.html";
	}
	
	
	@RequestMapping("{crowdfundID}/editBms")
	public String editBms(@PathVariable("crowdfundID") int crowdfundID) throws Exception {
		Crowdfund crowdfund=crowdfundBmsService.get(crowdfundID);
		super.setRequestAttibute("crowdfund", crowdfund);
		return "bms/bmsCf/crowdfund_update.html";
	}
	
	
	
	@RequestMapping("{crowdfundID}/detailsBms")
	public String detailsBms(@PathVariable("crowdfundID") int crowdfundID) throws Exception {
		Crowdfund crowdfund=crowdfundBmsService.get(crowdfundID);
		super.setRequestAttibute("crowdfund", crowdfund);
		return "bms/bmsCf/crowdfund_details.html";
	}
	
	
	
	/**方法名称: crowdfundCountRmb<br>
	 * 描述：打赏记录大于3000RMB的订单信息
	 * 作者: 王小欢
	 * 修改日期：2015年11月19日上午10:17:11
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("crowdfundCountRmb")
	public String crowdfundCountRmb() throws Exception {
		return "bms/bmsCf/crowdfundCountRmb.html";
	}
}
