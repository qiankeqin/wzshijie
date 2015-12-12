package com.lagoqu.worldplay.api;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easemob.server.httpclient.apidemo.EasemobMessages;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.entity.AccountDetails;
import com.lagoqu.worldplay.entity.Crowdfund;
import com.lagoqu.worldplay.entity.CrowdfundMark;
import com.lagoqu.worldplay.entity.Members;
import com.lagoqu.worldplay.entity.OrderInfo;
import com.lagoqu.worldplay.service.AccountDetailsService;
import com.lagoqu.worldplay.service.AccountsService;
import com.lagoqu.worldplay.service.AppMembersService;
import com.lagoqu.worldplay.service.CrowdfundMarkService;
import com.lagoqu.worldplay.service.CrowdfundService;
import com.lagoqu.worldplay.service.OrderInfoService;
import com.lagoqu.worldplay.util.DateUtil;
import com.lagoqu.worldplay.util.SignUtil;
import com.lagoqu.worldplay.util.WeixinUtil;

@Controller
@Scope("prototype")
@RequestMapping("/weixin")
public class WeixinPay extends APIController{
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
	@Resource
	AppMembersService appMembersService;
	@ResponseBody
	@RequestMapping(value="payResult",method = RequestMethod.POST)
	public void crowdfundDetail() throws Exception{
		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
		    outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		String resultStr  = new String(outSteam.toByteArray(),"utf-8");
		Map<String, String> resultMap = WeixinUtil.getMapFromXML(resultStr);
		
		String return_code = resultMap.get("return_code");
		int out_trade_no =Integer.parseInt(resultMap.get("out_trade_no"));
		System.out.println("out_trade_no:"+out_trade_no);
		if(return_code.equals("SUCCESS")){
			boolean bl = orderInfoService.updateOrder(out_trade_no);
			if(bl){
				OrderInfo orderInfo =orderInfoService.get(out_trade_no);
				CrowdfundMark crowdfundMark = new CrowdfundMark();
				crowdfundMark.setOrderID(out_trade_no);
				crowdfundMark.setCrowdfundID(orderInfo.getCrowdfundID());
				crowdfundMark.setCrowdfundmarkAccount(orderInfo.getOrderPrice());
				crowdfundMark.setCrowdfundmarkMessage(orderInfo.getOrderMemo());
				crowdfundMark.setMembersID(orderInfo.getMembersID());
				Crowdfund crowdfund = crowdfundService.get(orderInfo.getCrowdfundID());
				crowdfundMarkService.insert(crowdfundMark);
				//账单明细增加一条
				AccountDetails accountDetails = new AccountDetails();
				accountDetails.setMembersID(crowdfund.getMembersID());
				accountDetails.setDetailsAccount(orderInfo.getOrderPrice());
				accountDetails.setDetailsType("1");
				accountDetails.setDetailsSource(crowdfund.getCrowdfundType());
				accountDetails.setDetailsMembersId(orderInfo.getMembersID());
				if(crowdfund.getCrowdfundType()==0){
					accountDetails.setDetailsTime(crowdfund.getEndTime());
				}else {
					Date d = new Date();
					DateUtil du = new DateUtil();
					String s = du.addDay(du.convertDateToString(d),15);
					s= s+" 00:00:00";
					Timestamp ts = new Timestamp(System.currentTimeMillis());
					ts = Timestamp.valueOf(s);
					accountDetails.setDetailsTime(ts);
				}
				accountDetailsService.insert(accountDetails);
				//账户总额变化（余额+收到金额）
				if(crowdfund.getCrowdfundState()==0){
					accountsService.updateAccountsAfterOrderDone(orderInfo.getOrderPrice(), crowdfund.getMembersID());
				}else{
					accountsService.updateAccountsAfterOrderDone(orderInfo.getOrderPrice(), -100);
				}
				crowdfundService.updateCrowdfundTimes(orderInfo.getCrowdfundID(), crowdfundMark.getCrowdfundmarkAccount());
				//消息透传
				Members members=appMembersService.get(orderInfo.getMembersID());
				Map<String,String> parms=new HashMap<String,String>();
				parms.put("type", String.valueOf(crowdfund.getCrowdfundType()));
				parms.put("userName", members.getMembersNickName());
				parms.put("userHeader", members.getMembersImage());
				SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				parms.put("account",String.valueOf(orderInfo.getOrderPrice()/100));
				parms.put("userTime", time.format(new Date()).toString());
				parms.put("id", String.valueOf(crowdfund.getCrowdfundID()));
				EasemobMessages.sendCMDtoUser(String.valueOf(accountDetails.getMembersID()), "001", parms);
			}
		    //此处就是你的逻辑代码
        }
		//通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
		response.getWriter().write(WeixinUtil.setXML("SUCCESS", ""));
	}
	@ResponseBody
	@RequestMapping(value="paySdk",method = RequestMethod.GET)
	public void paySdk() throws Exception{
		 // 微信加密签名  
		String signature = request.getParameter("signature");  
		
		// 时间戳  
		String timestamp = request.getParameter("timestamp");  
		// 随机数  
		String nonce = request.getParameter("nonce");  
		// 随机字符串  
		String echostr = request.getParameter("echostr");  
		if (SignUtil.checkSignature(signature, timestamp, nonce)) { 
			response.getWriter().write(echostr);  
		}  
		
	}
	@ResponseBody
	@RequestMapping(value="notePay",method = RequestMethod.GET)
	public void notePay(@RequestParam(value = "out_trade_no", required = true) int out_trade_no) throws Exception{
		boolean bl = orderInfoService.updateOrder(out_trade_no);
		if(bl){
			OrderInfo orderInfo =orderInfoService.get(out_trade_no);
			CrowdfundMark crowdfundMark = new CrowdfundMark();
			crowdfundMark.setOrderID(out_trade_no);
			crowdfundMark.setCrowdfundID(orderInfo.getCrowdfundID());
			crowdfundMark.setCrowdfundmarkAccount(orderInfo.getOrderPrice());
			crowdfundMark.setCrowdfundmarkMessage(orderInfo.getOrderMemo());
			crowdfundMark.setMembersID(orderInfo.getMembersID());
			JSONArray jsonArray = crowdfundMarkService.findCrowdfundMarkListByOrderId(out_trade_no);
			Crowdfund crowdfund = crowdfundService.get(orderInfo.getCrowdfundID());
			if(jsonArray.isEmpty()){
				crowdfundMarkService.insert(crowdfundMark);
				//账单明细增加一条
				AccountDetails accountDetails = new AccountDetails();
				accountDetails.setMembersID(crowdfund.getMembersID());
				accountDetails.setDetailsAccount(orderInfo.getOrderPrice());
				accountDetails.setDetailsType("1");
				accountDetails.setDetailsMembersId(orderInfo.getMembersID());
				if(crowdfund.getCrowdfundType()==0){
					accountDetails.setDetailsTime(crowdfund.getEndTime());
				}else {
					Date d = new Date();
					DateUtil du = new DateUtil();
					String s = du.addDay(du.convertDateToString(d),15);
					s= s+" 00:00:00";
					Timestamp ts = new Timestamp(System.currentTimeMillis());
					ts = Timestamp.valueOf(s);
					accountDetails.setDetailsTime(ts);
				}
				accountDetailsService.insert(accountDetails);
				//账户总额变化（余额+收到金额）
				accountsService.updateAccountsAfterOrderDone(orderInfo.getOrderPrice(), crowdfund.getMembersID());
				crowdfundService.updateCrowdfundTimes(orderInfo.getCrowdfundID(), crowdfundMark.getCrowdfundmarkAccount());
				//消息透传
				Members members=appMembersService.get(orderInfo.getMembersID());
				Map<String,String> parms=new HashMap<String,String>();
				parms.put("type", String.valueOf(crowdfund.getCrowdfundType()));
				parms.put("userName", members.getMembersNickName());
				parms.put("userHeader", members.getMembersImage());
				SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				parms.put("account",String.valueOf(orderInfo.getOrderPrice()/100));
				parms.put("userTime", time.format(new Date()).toString());
				parms.put("id", String.valueOf(crowdfund.getCrowdfundID()));
				EasemobMessages.sendCMDtoUser(String.valueOf(accountDetails.getMembersID()), "001", parms);
			}
		}
	}
	
}
