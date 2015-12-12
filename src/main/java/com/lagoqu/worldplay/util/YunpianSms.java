package com.lagoqu.worldplay.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.lagoqu.worldplay.constants.Constants;




/**描述：短信http接口的java代码调用示例<br>
 * 作者：王小欢 <br>
 * 修改日期：2014年8月18日上午10:18:23 <br>
 * E-mail:  <br> 
 */
public class YunpianSms {
	/**
	 * 服务http地址
	 */
	private static String BASE_URI = "http://yunpian.com";
	/**
	 * 服务版本号
	 */
	private static String VERSION = "v1";
	/**
	 * 编码格式
	 */
	private static String ENCODING = "UTF-8";
	/**
	 * 查账户信息的http地址
	 */
	private static String URI_GET_USER_INFO = BASE_URI + "/" + VERSION + "/user/get.json";
	/**
	 * 通用发送接口的http地址
	 */
	private static String URI_SEND_SMS = BASE_URI + "/" + VERSION + "/sms/send.json";
	/**
	 * 模板发送接口的http地址
	 */
	private static String URI_TPL_SEND_SMS = BASE_URI + "/" + VERSION + "/sms/tpl_send.json";
	/**
	 * 发送语音验证码接口的http地址
	 */
    private static String URI_SEND_VOICE = BASE_URI + "/" + VERSION + "/voice/send.json";
	/**
	 * 取账户信息
	 * @return json格式字符串
	 * @throws IOException 
	 */
	public static String getUserInfo(String apikey) throws IOException{
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(URI_GET_USER_INFO+"?apikey="+apikey);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}
	/**
	 * 发短信
	 * @param apikey apikey
	 * @param text　短信内容　
	 * @param mobile　接受的手机号
	 * @return json格式字符串
	 * @throws IOException 
	 */
	public static String sendSms(String apikey, String text, String mobile) throws IOException{
		HttpClient client = new HttpClient();
		NameValuePair[] nameValuePairs = new NameValuePair[3];
		nameValuePairs[0] = new NameValuePair("apikey", apikey);
		nameValuePairs[1] = new NameValuePair("text", text);
		nameValuePairs[2] = new NameValuePair("mobile", mobile);
		PostMethod method = new PostMethod(URI_SEND_SMS);
		method.setRequestBody(nameValuePairs);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}
	
	/**
	 * 通过模板发送短信
	 * @param apikey apikey
	 * @param tpl_id　模板id
	 * @param tpl_value　模板变量值　
	 * @param mobile　接受的手机号
	 * @return json格式字符串
	 * @throws IOException 
	 */
	public static String tplSendSms(String apikey, long tpl_id, String tpl_value, String mobile) throws IOException{
		HttpClient client = new HttpClient();
		NameValuePair[] nameValuePairs = new NameValuePair[4];
		nameValuePairs[0] = new NameValuePair("apikey", apikey);
		nameValuePairs[1] = new NameValuePair("tpl_id", String.valueOf(tpl_id));
		nameValuePairs[2] = new NameValuePair("tpl_value", tpl_value);
		nameValuePairs[3] = new NameValuePair("mobile", mobile);
		PostMethod method = new PostMethod(URI_TPL_SEND_SMS);
		method.setRequestBody(nameValuePairs);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}
	
	public static YunpianResult tplSendSms(long tpl_id, String tpl_value, String mobile) throws IOException{
		HttpClient client = new HttpClient();
		NameValuePair[] nameValuePairs = new NameValuePair[4];
		nameValuePairs[0] = new NameValuePair("apikey", Constants.apikey);
		nameValuePairs[1] = new NameValuePair("tpl_id", String.valueOf(tpl_id));
		nameValuePairs[2] = new NameValuePair("tpl_value", tpl_value);
		nameValuePairs[3] = new NameValuePair("mobile", mobile);
		PostMethod method = new PostMethod(URI_TPL_SEND_SMS);
		method.setRequestBody(nameValuePairs);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		String result = method.getResponseBodyAsString();
		JSONObject resultJo = JSONObject.fromObject(result);
		YunpianResult yr = new YunpianResult();
		yr.setCode(resultJo.getLong("code"));
		yr.setMsg(resultJo.getString("msg"));
//        YunpianResult yr = (YunpianResult) JSONObject.toBean(resultJo, YunpianResult.class);
        return yr;
	}
	
	
	 /**
	    * 通过接口发送语音验证码
	    * @param apikey apikey
	    * @param mobile 接收的手机号
	    * @param code   验证码
	    * @return
	 * @throws IOException 
	 * @throws HttpException 
	    */
	    public static String tplSendVoice(String mobile, String code) throws HttpException, IOException {
			YunpianPost pYunpianPost=new YunpianPost();
			  Map<String, String> params = new HashMap<String, String>();
		        params.put("apikey", Constants.apikey);
		        params.put("mobile", mobile);
		        params.put("code", code);
		        String aString=pYunpianPost.post(URI_SEND_VOICE, params);
			       System.out.println(aString);
		        return aString;
	    }
	    
	
	public static void main(String[] args) throws IOException {
		//修改为您的apikey
		String apikey = "e182676964d5135cdaacd4119aa2b342";
		//修改为您要发送的手机号
		String mobile = "13466691514";
		
		//**************** 查账户信息调用示例 *****************//*
		System.out.println(YunpianSms.getUserInfo(apikey));
		
		//**************** 使用通用接口发短信 *****************//*
		/*//设置您要发送的内容
		String text = "您的验证码是1234【云片网】";
		//发短信调用示例
		System.out.println(YunpianSms.sendSms(apikey, text, mobile));*/
        String membersID="007";
        String accont="007";
        String username="007";
	       String tpl_value = "#membersID#="+membersID+"&#accont#="+accont+"&#username#="+username;
			 YunpianResult yr = YunpianSms.tplSendSms(Constants.sms_tpl_withdraw,tpl_value, Constants.tpl_withdraw_Phone);
		//**************** 使用模板接口发短信 *****************//*
		//设置模板ID，如使用1号模板:您的验证码是#code#【#company#】
	/*	long tpl_id=5;
		//设置对应的模板变量值
		String tpl_value="#app#=拉钩去（www.laguqo.com）&#code#=1234&#company#=北京快乐旅途网络科技有限公司";
		//模板发送的调用示例
		System.out.println(JavaSmsApi.tplSendSms(apikey, tpl_id, tpl_value, mobile));*/
	}



}
