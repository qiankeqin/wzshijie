package com.lagoqu.worldplay.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Sign {
	 public static Map<String, String> sign(String url) {
	        Map<String, String> ret = new HashMap<String, String>();
	        String nonce_str = WeixinUtil.create_nonce_str();
	        String timestamp = WeixinUtil.create_timestamp();
	        String string1;
	        String signature = "";
	        String accessToken = "";
	        String jsapi_ticket = "";
	        MemCachedManager mcm = new MemCachedManager();
	        if(mcm.get("accessTokenTemp")==null||mcm.get("jsapi_ticketTemp")==null){
	        	accessToken = WeixinToken.getToken();// 获取token
		        jsapi_ticket = WeixinToken.getJsapiTicket(accessToken);
		        mcm.set("accessTokenTemp", accessToken,new Date(30*60*1000));
		        mcm.set("jsapi_ticketTemp", jsapi_ticket,new Date(40*60*1000));
	        }else{
	        	accessToken = (String) mcm.get("accessTokenTemp");
	        	jsapi_ticket = (String)mcm.get("jsapi_ticketTemp");
	        }
//	        MemCachedManager mcm = new MemCachedManager();
//	        String mcmToken = (String) mcm.get("accessToken");
//	        String mcmTicket = (String)mcm.get("jsapi_ticket");
//	        if("".equals(mcmToken)||"".equals(mcmTicket)){
//	        	accessToken = WeixinToken.getToken();// 获取token
//		        jsapi_ticket = WeixinToken.getJsapiTicket(accessToken);
//		        Date expiryDate=new Date(7200);
//		        mcm.set("accessToken", accessToken, expiryDate);
//		        mcm.set("jsapi_ticket", jsapi_ticket, expiryDate);
//	        }else{
//	        	accessToken = (String) mcm.get("accessToken");
//	        	jsapi_ticket = (String)mcm.get("jsapi_ticket");
//	        }
//	        accessToken = WeixinToken.getToken();// 获取token
//	        jsapi_ticket = WeixinToken.getJsapiTicket(accessToken);
	        //注意这里参数名必须全部小写，且必须有序
	        string1 = "jsapi_ticket=" + jsapi_ticket +
	                  "&noncestr=" + nonce_str +
	                  "&timestamp=" + timestamp +
	                  "&url=" + url;
	        try
	        {
	            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	            crypt.reset();
	            crypt.update(string1.getBytes("UTF-8"));
	            signature = WeixinUtil.byteToHex(crypt.digest());
	        }
	        catch (NoSuchAlgorithmException e)
	        {
	            e.printStackTrace();
	        }
	        catch (UnsupportedEncodingException e)
	        {
	            e.printStackTrace();
	        }
	        System.out.println("accessToken:"+accessToken);
	        System.out.println("jsapi_ticket:"+jsapi_ticket);
	        ret.put("accessToken", accessToken);
	        ret.put("url", url);
	        ret.put("jsapi_ticket", jsapi_ticket);
	        ret.put("nonceStr", nonce_str);
	        ret.put("timestamp", timestamp);
	        ret.put("signature", signature);
	        return ret;
	    }
}
