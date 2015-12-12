package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.bms.service.CrowdfundBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.Crowdfund;
@Controller
@Scope("prototype")
@RequestMapping("/bms/crowdfundZuji")
public class CrowdfundZujiBmsPage extends BMSController{

	@Resource
	CrowdfundBmsService crowdfundBmsService;
	
	
	@RequestMapping("list")
	public String undisposed() throws Exception{
		
		return "bms/crowdfund/crowdfundList.html";
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
		super.setRequestAttibute("crowdfund", crowdfund);
		return "bms/crowdfund/crowdfund_details.html";
	}
	
	
	/**方法名称: bmsUndisposed<br>
	 * 描述：后台发布足迹
	 * 作者: 王小欢
	 * 修改日期：2015年8月4日上午11:00:08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("Bmslist")
	public String bmsUndisposed() throws Exception{
		
		return "bms/bmsCfZj/crowdfundList.html";
	}
	
	
	@RequestMapping("addBms")
	public String addBms() throws Exception {
		return "bms/bmsCfZj/crowdfund_add.html";
	}
	
	
	@RequestMapping("{crowdfundID}/addZujiBms")
	public String addZujiBms(@PathVariable("crowdfundID") int crowdfundID) throws Exception {
		super.setRequestAttibute("id", crowdfundID);
		return "bms/bmsCfZj/crowdfundZuji_add.html";
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
		return "bms/bmsCfZj/crowdfund_mark.html";
	}
	
	
	@RequestMapping("{crowdfundID}/editBms")
	public String editBms(@PathVariable("crowdfundID") int crowdfundID) throws Exception {
		Crowdfund crowdfund=crowdfundBmsService.get(crowdfundID);
		super.setRequestAttibute("crowdfund", crowdfund);
		return "bms/bmsCfZj/crowdfund_update.html";
	}
	
	
	
	@RequestMapping("{crowdfundID}/editZujiBms")
	public String editZujiBms(@PathVariable("crowdfundID") int crowdfundID) throws Exception {
		super.setRequestAttibute("id", crowdfundID);
		return "bms/bmsCfZj/crowdfundZuji_update.html";
	}
	
	
	
	@RequestMapping("{crowdfundID}/detailsBms")
	public String detailsBms(@PathVariable("crowdfundID") int crowdfundID) throws Exception {
		Crowdfund crowdfund=crowdfundBmsService.get(crowdfundID);
		super.setRequestAttibute("crowdfund", crowdfund);
		return "bms/bmsCf/crowdfund_details.html";
	}
}
