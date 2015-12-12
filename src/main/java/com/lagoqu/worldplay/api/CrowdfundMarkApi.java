package com.lagoqu.worldplay.api;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.constants.Constants;
import com.lagoqu.worldplay.service.AccountDetailsService;
import com.lagoqu.worldplay.service.AccountsService;
import com.lagoqu.worldplay.service.CrowdfundMarkService;
import com.lagoqu.worldplay.service.CrowdfundService;
import com.lagoqu.worldplay.service.OrderInfoService;
import com.lagoqu.worldplay.util.EmojiFilter;
import com.lagoqu.worldplay.util.WeixinToken;
import com.lagoqu.worldplay.util.WeixinUtil;

/**描述：助力记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月5日上午10:47:38 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/helprecord")
public class CrowdfundMarkApi extends APIController{

	@Resource
	CrowdfundMarkService crowdfundMarkService;
	@Resource
	OrderInfoService orderInfoService;
	@Resource
	CrowdfundService crowdfundService;
	@Resource
	AccountDetailsService accountDetailsService;
	@Resource
	AccountsService accountsService;
	
	
	/**方法名称: myjoin<br>
	 * 描述：我参与的助力列表
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日上午11:01:23
	 * @param page
	 * @param size
	 * @param memberID
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="myjoin",method = RequestMethod.POST)
	public void myjoin() throws Exception{
		JSONObject jObject=super.getRequestJsonParams();
		int page=jObject.getInt("page");
		int size=jObject.getInt("size");
		int memberID=jObject.getInt("membersID");
		try {
			Pagination<JSONArray> pagination = crowdfundMarkService.findCrowdfundMarkByMembersID(page,size,memberID);
			returnSuccessJson(pagination.toString());
		} catch (Exception e) {
			returnFailJson("find fail");
		}
		
	}
	/**
	 * 方法名称: toHelp<br>
	 * 描述：我要助力
	 * 作者: 邢留杰
	 * 修改日期：2015年6月18日下午4:36:16
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="toHelp",method = RequestMethod.POST)
	public void toHelp() throws Exception{
		JSONObject jObject=super.getRequestJsonParams();
		String orderMemo = EmojiFilter.filterEmoji(jObject.getString("orderMemo").trim());
		jObject.put("orderMemo", orderMemo);
		int orderInfo = orderInfoService.saveOrder(jObject);
		if(orderInfo==-1){
			returnFailJson("打赏失败");
		}else{
			JSONObject jsonObject = new JSONObject();  
		    jsonObject.put("id",orderInfo);  
		    returnSuccessJson(jsonObject.toString());
		}
	}
	/**
	 * 方法名称: toHelp<br>
	 * 描述：微信我要助力
	 * 作者: 邢留杰
	 * 修改日期：2015年6月18日下午4:36:16
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="toWeiXinHelp",method = RequestMethod.POST)
	public void toWeiXinHelp() throws Exception{
		JSONObject jObject=super.getRequestJsonParams();
		int orderInfo = orderInfoService.saveOrderWx(jObject);
		if(orderInfo==-1){
			returnFailJson("打赏失败");
		}else{
			int orderPrice=jObject.getInt("orderPrice");
			String openid=jObject.getString("openId");
			Map<String, String> map = WeixinToken.getUnifiedorder(orderInfo+"",orderPrice+"",openid);
			String timestamp = WeixinUtil.create_timestamp();
			JSONObject jsonObject = new JSONObject();  
			String prepay_id="prepay_id="+(String) map.get("prepay_id");
			String nonce_str =(String) map.get("nonce_str");
			String temp = "appId="+Constants.APP_ID + "&nonceStr="+nonce_str + "&package="+prepay_id+"&signType=MD5&timeStamp=" + timestamp + "&key="+Constants.API_KEY_CFT;
			String sign=WeixinUtil.MD5Encode(temp,"UTF-8").toUpperCase();
			jsonObject.put("prepayid",prepay_id); 
		    jsonObject.put("nonce_str",nonce_str);
		    jsonObject.put("sign",sign);
		    jsonObject.put("temp",temp);
		    jsonObject.put("timestamp",timestamp);
		    jsonObject.put("id",orderInfo);  
		    returnSuccessJson(jsonObject.toString());
		}
	}
	/**方法名称: crowdfundMarkadd<br>
	 * 描述：保存用户助力记录
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日下午4:53:20
	 * @param crowdfundMark
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="crowdfundMarkadd",method = RequestMethod.POST)
	public void crowdfundMarkadd() throws IOException{
		JSONObject jObject=super.getRequestJsonParams();
		int crowdfundmarkID=crowdfundMarkService.saveCrowdfundMark(jObject);
		JSONObject jsonObject = new JSONObject();  
	    jsonObject.put("crowdfundmarkID",crowdfundmarkID);  
		returnSuccessJson(jsonObject.toString());
	}

}
