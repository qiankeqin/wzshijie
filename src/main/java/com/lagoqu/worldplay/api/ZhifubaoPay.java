package com.lagoqu.worldplay.api;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@Controller
@Scope("prototype")
@RequestMapping("/zhifubao")
public class ZhifubaoPay extends APIController{

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
	@RequestMapping(value="ZfbpayResult",method = RequestMethod.POST)
	public void crowdfundDetail() throws Exception{
		System.out.println("进入支付宝回调，获取参数");
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			System.out.println("支付宝返回参数zhifubao:名称"+name+"值"+valueStr);
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		System.out.println("进入支付宝回调，参数获取完成");
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//支付宝交易号

		String oderPayNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
		//支付宝支付方账号
		String orderPayUser = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

		
		if(trade_status.equals("TRADE_SUCCESS")){
			System.out.println("进入逻辑处理");
			boolean bl = orderInfoService.updateOrder(Integer.valueOf(out_trade_no));
			if(bl){
				OrderInfo orderInfo =orderInfoService.get(Integer.valueOf(out_trade_no));
				CrowdfundMark crowdfundMark = new CrowdfundMark();
				crowdfundMark.setOrderID(Integer.valueOf(out_trade_no));
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
				parms.put("userTime", time.format(new Date()).toString());;
				parms.put("id", String.valueOf(crowdfund.getCrowdfundID()));
				EasemobMessages.sendCMDtoUser(String.valueOf(accountDetails.getMembersID()), "001", parms);
			}
			response.getWriter().write("SUCCESS");
		}
		if(trade_status.equals("WAIT_BUYER_PAY")){
			response.getWriter().write("等待付款");
		}
				
	}
}
