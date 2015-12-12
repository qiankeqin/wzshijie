package com.lagoqu.worldplay.constants;

import java.io.IOException;

import com.lagoqu.common.util.PropertiesExpand;



/**描述：公共常量类<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月3日上午10:51:05 <br>
 * E-mail:  <br> 
 */
public class Constants { 
	public final static PropertiesExpand pe = new PropertiesExpand();
	static {
		String relativePath = "util.properties";
		try {
			pe.loadByRelativePath(relativePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 三基时代短信用户名
	 */
	public final static String ClientName = "SDK-666-010-03608";
	
	/**
	 * 三基时代短信密码
	 */
	public final static String ClientPassword = "714957";

	/**
	 * 短信验证apikey Constants.java
	 */
	public final static String apikey = "e182676964d5135cdaacd4119aa2b342";
	
	/**
	 * 短信模版id 注册
	 */
	public final static long sms_tpl_id = 901673;
	
	
	/**
	 * 短信模版id 提现  通知后台人员短信
	 */
	public final static long sms_tpl_withdraw = 906619;
	
	
	/**
	 *  提现  通知后台人员手机号
	 */
	public final static String tpl_withdraw_Phone ="18677982988";

	
	/**
	 * memecahed的ip
	 */
	public final static String memcached_servers = pe.getProperty("memcached_servers");
	
	/**
	 * 上传图片的路径
	 */
	public final static String upload_img_path=pe.getProperty("upload_img_path");
	
	
	
	/**
	 * 上传图片的路径(后台发布助理)
	 */
	public final static String upload_img_path_bms=pe.getProperty("upload_img_path_bms");
	
	/**
	 * MD5 加密字符串
	 */
	public final static String ENCRYP_STRING="*+^%$";
	
	
	public final static String MCH_ID = "1254500201";//商户号
	public static final String APP_ID = "wxc4240168d70dab2f";//服务号的应用号
	public final static String API_KEY = "d5844fb1666475211ad868f1e94c27f4";//API密钥
	public final static String API_KEY_CFT = "abcdefghijABCDEFGHIJ123456789cst";//API密钥(财付通)
	public final static String SIGN_TYPE = "MD5";//签名加密方式
	
	/**
	 * 微信基础接口地址
	 */
	public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";// 获取access
    public static final String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";//获取ticket                                                                          
    public static final String GET_LOGIN_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";// 获取access
    public static final String GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo";
	public final static String WEIXIN_USERINFO="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc4240168d70dab2f&redirect_uri=http%3a%2f%2fwww.wzshijie.com%2ftoSkip&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
	//发布获取用户信息
	public final static String WEIXIN_FABU_USERINFO="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc4240168d70dab2f&redirect_uri=http%3a%2f%2fwww.wzshijie.com%2ftoSkip&response_type=code&scope=snsapi_userinfo&state=fabu#wechat_redirect";
	public final static String WEIXIN_ACCOUNT_USERINFO="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc4240168d70dab2f&redirect_uri=http%3a%2f%2fwww.wzshijie.com%2ftoSkip&response_type=code&scope=snsapi_userinfo&state=account#wechat_redirect";
	//微信支付统一接口(POST)
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public final static String NOTIFY_URL = "http://www.wzshijie.com/weixin/payResult?code=123123";
	/**
	 * 注册环信用户环信key
	 */
	public final static String easemob_key = pe.getProperty("easemob_key");
	
	/**
	 * 注册环信用户环信value
	 */
	public final static String easemob_value = pe.getProperty("easemob_value");
	
	public final static String fun_link = pe.getProperty("fun_link");
}
