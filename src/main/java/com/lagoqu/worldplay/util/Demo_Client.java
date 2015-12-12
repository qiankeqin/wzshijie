package com.lagoqu.worldplay.util;

import java.io.UnsupportedEncodingException;
import java.net.*;

import com.lagoqu.common.util.RandomUtil;
import com.lagoqu.worldplay.constants.Constants;


public class Demo_Client {
		


		public static void main(String[] args) throws UnsupportedEncodingException
		{

			Client client=new Client(Constants.ClientName,Constants.ClientPassword);
			//获取信息
			//String result = client.mdgetSninfo();
			//System.out.print(result);
			//短信发送	
			RandomUtil randomUtil = new RandomUtil();
			String code=randomUtil.getNum(6);
		   String content=URLEncoder.encode(code+"玩赚世界登录短信验证码，关注微信：玩赚世界 有惊喜【玩赚世界】", "utf8");
		   //参    数：mobile,content,ext,stime,rrid,msgfmt(手机号，内容，扩展码，定时时间，唯一标识，内容编码)
			String result_mt = client.mdsmssend("13466691514", content, "4", "", "", "");
			System.out.print(result_mt);
			//个性短信发送
//			String result_gxmt = client.mdgxsend("13800138", "测试", "", "", "", "");
//			System.out.print(result_gxmt);

		}
	}
