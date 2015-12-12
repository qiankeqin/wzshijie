package com.lagoqu.worldplay.api.page;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.constants.Constants;
import com.lagoqu.worldplay.entity.Crowdfund;
import com.lagoqu.worldplay.service.CrowdfundService;
import com.lagoqu.worldplay.util.Sign;

@Controller
@Scope("prototype")
@RequestMapping("/helpPage")
public class CrowdfundPage extends APIController{
	@Resource
	CrowdfundService crowdfundService;
	/**
	 * 方法名称: toCrowdfund<br>
	 * 描述：添加助力
	 * 作者: 邢留杰
	 * 修改日期：2015年6月12日上午11:05:51
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String toCrowdfund() throws Exception{
		if(!super.isMemberLogin()){
			return "redirect:" + Constants.WEIXIN_FABU_USERINFO; 
		}
		super.setRequestAttibute("crrrentMember", super.getMember());
		return "zhuli/zhuli_fabu.html";
	}
	/**
	 * 方法名称: toCrowdfundDetail<br>
	 * 描述：跳转到助力详情页
	 * 作者: 邢留杰
	 * 修改日期：2015年6月15日下午3:49:51
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/crowdfundDetail/{crowdfundID}", method = RequestMethod.GET)
	public String toCrowdfundDetail(@PathVariable("crowdfundID") int crowdfundID,@RequestParam(value = "from",required = false) String from) throws Exception{
		Crowdfund crowdfund=crowdfundService.get(crowdfundID);
		if(crowdfund.getCrowdfundIsDel()!=0){
			return "index.html";
		}
		super.setRequestAttibute("cfId", crowdfundID);
		String requestUrl = request.getRequestURL()+"?"+request.getQueryString();
		Map<String,String> ret = Sign.sign(requestUrl);
		super.setRequestAttibute("accessToken", ret.get("accessToken"));
		super.setRequestAttibute("url",ret.get("url"));
	    super.setRequestAttibute("jsapi_ticket",ret.get("jsapi_ticket"));
	    super.setRequestAttibute("nonceStr",ret.get("nonceStr"));
	    super.setRequestAttibute("timestamp",ret.get("timestamp"));
	    super.setRequestAttibute("signature",ret.get("signature"));
	    return "zhuli/zhuli_details_1.html";
	}
	
	/**
	 * 方法名称: toCrowdfundDetail<br>
	 * 描述：跳转到助力详情页
	 * 作者: 邢留杰
	 * 修改日期：2015年6月15日下午3:49:51
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/crowdfundThirdDetail/{crowdfundID}", method = RequestMethod.GET)
	public String toCrowdfundThirdDetail(@PathVariable("crowdfundID") int crowdfundID) throws Exception{
		super.setRequestAttibute("cfId", crowdfundID);
		Map<String,String> ret = Sign.sign("http://www.wzshijie.com/helpPage/crowdfundThirdDetail/"+crowdfundID);
		super.setRequestAttibute("accessToken", ret.get("accessToken"));
		super.setRequestAttibute("url",ret.get("url"));
	    super.setRequestAttibute("jsapi_ticket",ret.get("jsapi_ticket"));
	    super.setRequestAttibute("nonceStr",ret.get("nonceStr"));
	    super.setRequestAttibute("timestamp",ret.get("timestamp"));
	    super.setRequestAttibute("signature",ret.get("signature"));
		return "zhuli/zhuli_details_3.html";
	}
	
	
	/**
	 * 方法名称: toCrowdfundDetail<br>
	 * 描述：微信登录之后跳转到助力详情页
	 * 作者: 邢留杰
	 * 修改日期：2015年6月15日下午3:49:51
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/crowdfundDetailWx/{crowdfundID}", method = RequestMethod.GET)
	public String toCrowdfundDetailWx(@PathVariable("crowdfundID") int crowdfundID,@RequestParam(value = "membersWxID", required = true) String membersWxID) throws Exception{
		super.setRequestAttibute("cfId", crowdfundID);
		super.setRequestAttibute("openId", membersWxID);
		Map<String,String> ret = Sign.sign("http://www.wzshijie.com/helpPage/crowdfundDetailWx/"+crowdfundID+"?membersWxID="+membersWxID);
		super.setRequestAttibute("accessToken", ret.get("accessToken"));
		super.setRequestAttibute("url",ret.get("url"));
	    super.setRequestAttibute("jsapi_ticket",ret.get("jsapi_ticket"));
	    super.setRequestAttibute("nonceStr",ret.get("nonceStr"));
	    super.setRequestAttibute("timestamp",ret.get("timestamp"));
	    super.setRequestAttibute("signature",ret.get("signature"));
		return "zhuli/zhuli_details_2.html";
	}
	/**
	 * 方法名称: toCrowdfundDetail<br>
	 * 描述：带有分享效果的详情页面
	 * 作者: 邢留杰
	 * 修改日期：2015年6月15日下午3:49:51
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/crowdfundFourDetail/{crowdfundID}", method = RequestMethod.GET)
	public String crowdfundFourDetail(@PathVariable("crowdfundID") int crowdfundID) throws Exception{
		super.setRequestAttibute("cfId", crowdfundID);
		Map<String,String> ret = Sign.sign("http://www.wzshijie.com/helpPage/crowdfundFourDetail/"+crowdfundID);
		super.setRequestAttibute("accessToken", ret.get("accessToken"));
		super.setRequestAttibute("url",ret.get("url"));
	    super.setRequestAttibute("jsapi_ticket",ret.get("jsapi_ticket"));
	    super.setRequestAttibute("nonceStr",ret.get("nonceStr"));
	    super.setRequestAttibute("timestamp",ret.get("timestamp"));
	    super.setRequestAttibute("signature",ret.get("signature"));
		return "zhuli/zhuli_details_4.html";
	}
	/**
	 * 方法名称: wishDetail<br>
	 * 描述：跳转到心愿详情
	 * 作者: 邢留杰
	 * 修改日期：2015年11月14日上午10:33:07
	 * @param crowdfundID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wishDetail/{crowdfundID}", method = RequestMethod.GET)
	public String wishDetail(@PathVariable("crowdfundID") int crowdfundID) throws Exception{
		super.setRequestAttibute("id",crowdfundID);
		String requestUrl = "";
		if(request.getQueryString() == null){
			requestUrl = request.getRequestURL().toString();
		}else{
			requestUrl = request.getRequestURL()+"?"+request.getQueryString();
		}
		Map<String,String> ret = Sign.sign(requestUrl);
		super.setRequestAttibute("accessToken", ret.get("accessToken"));
		super.setRequestAttibute("url",ret.get("url"));
	    super.setRequestAttibute("jsapi_ticket",ret.get("jsapi_ticket"));
	    super.setRequestAttibute("nonceStr",ret.get("nonceStr"));
	    super.setRequestAttibute("timestamp",ret.get("timestamp"));
	    super.setRequestAttibute("signature",ret.get("signature"));
		return "v2/wishDetails.html";
	}
	/**
	 * 方法名称: wishDetail<br>
	 * 描述：跳转到心愿详情
	 * 作者: 邢留杰
	 * 修改日期：2015年11月14日上午10:33:07
	 * @param crowdfundID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tripDetail/{crowdfundID}", method = RequestMethod.GET)
	public String tripDetail(@PathVariable("crowdfundID") int crowdfundID) throws Exception{
		super.setRequestAttibute("id",crowdfundID);
		String requestUrl = "";
		if(request.getQueryString() == null){
			requestUrl = request.getRequestURL().toString();
		}else{
			requestUrl = request.getRequestURL()+"?"+request.getQueryString();
		}
		Map<String,String> ret = Sign.sign(requestUrl);
		super.setRequestAttibute("accessToken", ret.get("accessToken"));
		super.setRequestAttibute("url",ret.get("url"));
	    super.setRequestAttibute("jsapi_ticket",ret.get("jsapi_ticket"));
	    super.setRequestAttibute("nonceStr",ret.get("nonceStr"));
	    super.setRequestAttibute("timestamp",ret.get("timestamp"));
	    super.setRequestAttibute("signature",ret.get("signature"));
		return "v2/tripDetails.html";
	}
	/**
	 * 方法名称: toGive<br>
	 * 描述：去打赏
	 * 作者: 邢留杰
	 * 修改日期：2015年11月27日上午9:36:38
	 * @param crowdfundID
	 * @param type
	 * @param membersWxID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toGive/{crowdfundID}", method = RequestMethod.GET)
	public String toGive(@PathVariable("crowdfundID") int crowdfundID,@RequestParam(value = "membersWxID", required = true) String membersWxID) throws Exception{
		super.setRequestAttibute("openId", membersWxID);
		Crowdfund cf = crowdfundService.get(crowdfundID);
		super.setRequestAttibute("cfId", crowdfundID);
		super.setRequestAttibute("cfType", cf.getCrowdfundType());
		super.setRequestAttibute("cfName", cf.getCrowdfundTitle());
		String requestUrl = "";
		if(request.getQueryString() == null){
			requestUrl = request.getRequestURL().toString();
		}else{
			requestUrl = request.getRequestURL()+"?"+request.getQueryString();
		}
		Map<String,String> ret = Sign.sign(requestUrl);
		super.setRequestAttibute("accessToken", ret.get("accessToken"));
		super.setRequestAttibute("url",ret.get("url"));
	    super.setRequestAttibute("jsapi_ticket",ret.get("jsapi_ticket"));
	    super.setRequestAttibute("nonceStr",ret.get("nonceStr"));
	    super.setRequestAttibute("timestamp",ret.get("timestamp"));
	    super.setRequestAttibute("signature",ret.get("signature"));
	    super.setRequestAttibute("crrrentMember", super.getMember());
		return "v2/toPay.html";
	}
}
