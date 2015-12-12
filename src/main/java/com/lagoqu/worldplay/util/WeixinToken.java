package com.lagoqu.worldplay.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lagoqu.worldplay.constants.Constants;


public class WeixinToken
 {
     // 获取token
     public static String getToken()
     {
         String turl = String.format(
                 "%s?grant_type=client_credential&appid=%s&secret=%s", Constants.GET_TOKEN_URL,
                 Constants.APP_ID, Constants.API_KEY);
         HttpClient client = new DefaultHttpClient();
         HttpGet get = new HttpGet(turl);
         JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
         String result = null;
         try
         {
             HttpResponse res = client.execute(get);
             String responseContent = null; // 响应内容
             HttpEntity entity = res.getEntity();
             responseContent = EntityUtils.toString(entity, "UTF-8");
             JsonObject json = jsonparer.parse(responseContent)
                     .getAsJsonObject();
             // 将json字符串转换为json对象
             if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
             {
                 if (json.get("errcode") != null)
                 {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
                 }
                 else
                 {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
                     result = json.get("access_token").getAsString();
                 }
             }
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
         finally
         {
             // 关闭连接 ,释放资源
             client.getConnectionManager().shutdown();
             return result;
         }
     }
     //获取jsapiTicket
     public static String getJsapiTicket(String token)
     {
         String turl = String.format(
                 "%s?access_token=%s&type=jsapi", Constants.GET_TICKET_URL,
                 token);
         HttpClient client = new DefaultHttpClient();
         HttpGet get = new HttpGet(turl);
         JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
         String result = null;
         try
         {
             HttpResponse res = client.execute(get);
             String responseContent = null; // 响应内容
             HttpEntity entity = res.getEntity();
             responseContent = EntityUtils.toString(entity, "UTF-8");
             JsonObject json = jsonparer.parse(responseContent)
                     .getAsJsonObject();
             // 将json字符串转换为json对象
             if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
             {
                 if (json.get("errcode").getAsInt() != 0)
                 {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
                 }
                 else
                 {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
                     result = json.get("ticket").getAsString();
                 }
             }
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
         finally
         {
             // 关闭连接 ,释放资源
             client.getConnectionManager().shutdown();
             return result;
         }
     }
     
     
   //获取getOpenid
     public static Map<String, String> getOpenid(String code)
     {
    	 Map<String, String> ret = new HashMap<String, String>();	 
         String turl = String.format(
                 "%s?appid=%s&secret=%s&code=%s&grant_type=authorization_code", Constants.GET_LOGIN_TOKEN_URL,
                 Constants.APP_ID, Constants.API_KEY,code);
         HttpClient client = new DefaultHttpClient();
         HttpGet get = new HttpGet(turl);
         JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
         try
         {
             HttpResponse res = client.execute(get);
             String responseContent = null; // 响应内容
             HttpEntity entity = res.getEntity();
             responseContent = EntityUtils.toString(entity, "UTF-8");
             JsonObject json = jsonparer.parse(responseContent)
                     .getAsJsonObject();
             // 将json字符串转换为json对象
             if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
             {
                 if (json.get("errcode")!=null)
                 {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
                 }
                 else
                 {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
                	 ret.put("openid", json.get("openid").getAsString());
                	 ret.put("accesstoken",json.get("access_token").getAsString());
                 }
             }
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
         finally
         {
             // 关闭连接 ,释放资源
             client.getConnectionManager().shutdown();
             return ret;
         }
     }
     
     public static Map<String, String> getUserInfo(String openid,String accesstoken)
     {
    	 Map<String, String> ret = new HashMap<String, String>();	 
         String turl = String.format(
                 "%s?access_token=%s&openid=%s&lang=zh_CN", Constants.GET_USERINFO,
                 accesstoken, openid);
         HttpClient client = new DefaultHttpClient();
         HttpGet get = new HttpGet(turl);
         JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
         try
         {
             HttpResponse res = client.execute(get);
             String responseContent = null; // 响应内容
             HttpEntity entity = res.getEntity();
             responseContent = EntityUtils.toString(entity, "UTF-8");
             JsonObject json = jsonparer.parse(responseContent)
                     .getAsJsonObject();
             // 将json字符串转换为json对象
             if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
             {
                 if (json.get("errcode")!=null)
                 {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
                 }
                 else
                 {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
                	 ret.put("openid", json.get("openid").getAsString());
                	 ret.put("nickname",json.get("nickname").getAsString());
                	 ret.put("sex",json.get("sex").getAsString());
                	 ret.put("headimgurl",json.get("headimgurl").getAsString());
                 }
             }
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
         finally
         {
             // 关闭连接 ,释放资源
             client.getConnectionManager().shutdown();
             return ret;
         }
     }
     //获取统一支付
     public static Map<String, String> getUnifiedorder(String out_trade_no,String total_fee,String openid) throws ParserConfigurationException, IOException, SAXException{
    	 SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
    	 RandomStringGenerator randomStringGenerator=new RandomStringGenerator();
    	 String nonce_str=randomStringGenerator.getRandomStringByLength(32);
    	 parameters.put("appid", Constants.APP_ID);    //公众账号ID 
    	 parameters.put("body", "玩赚世界电子红包");     //商品描述 
    	 parameters.put("mch_id", Constants.MCH_ID);   //商户号
    	 parameters.put("nonce_str", nonce_str);   //随机字符串
    	 parameters.put("notify_url", Constants.NOTIFY_URL);     //通知地址 
    	 parameters.put("openid", openid);            //用户标识   trade_type=JSAPI，此参数必传
    	 parameters.put("out_trade_no", out_trade_no);  //商户订单号 
    	 parameters.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress());   //终端IP
    	 parameters.put("total_fee", total_fee);         //总金额 
    	 parameters.put("trade_type", "JSAPI");       //交易类型   JSAPI，NATIVE，APP，WAP
    	 String sign = WeixinUtil.createSign("UTF-8", parameters);
    	 parameters.put("sign",sign);     //签名 
    	 String requestXML = WeixinUtil.getRequestXml(parameters);
    	 String result =CommonUtil.httpsRequest(Constants.UNIFIED_ORDER_URL, "POST", requestXML);
    	 Map<String, String> map=WeixinUtil.getMapFromXML(result);
    	 String prepay_id="prepay_id="+(String) map.get("prepay_id");
    	 return map;
     }
     
     
     
     public Map<String,Object> toMap(){
         Map<String,Object> map = new HashMap<String, Object>();
         Field[] fields = this.getClass().getDeclaredFields();
         for (Field field : fields) {
             Object obj;
             try {
                 obj = field.get(this);
                 if(obj!=null){
                     map.put(field.getName(), obj);
                 }
             } catch (IllegalArgumentException e) {
                 e.printStackTrace();
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             }
         }
         return map;
     }
     
     public static void main(String[] args) throws Exception
     {
         String accessToken = getToken();// 获取token
         String ticket = getJsapiTicket(accessToken);
         if (accessToken != null)
             System.out.println(accessToken);
         if (ticket != null)
             System.out.println(ticket);
     }

 }