package com.lagoqu.worldplay.api;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.entity.PhoneLaunch;
import com.lagoqu.worldplay.service.PhoneLaunchService;
/**描述：手机启动记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月5日上午10:47:38 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/applaunch")
public class PhoneLaunchApi extends APIController{

	@Resource
	PhoneLaunchService phoneLaunchService;
	
	/**方法名称: oneQD<br>
	 * 描述：手机启动
	 * 应用第一次启动时候，发送到服务端，服务端为该设备注册登记
	 * 作者: 王小欢
	 * 修改日期：2015年6月8日下午3:56:48
	 * @param phoneLaunch
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="",method = RequestMethod.POST)
	public void  oneQD() throws IOException{
		JSONObject jObject=super.getRequestJsonParams();
		int plID=phoneLaunchService.insertPhoneLaunch(jObject);
		JSONObject jsonObject = new JSONObject();  
	    jsonObject.put("plID",plID);  
		returnSuccessJson(jsonObject.toString());
	}
	@ResponseBody
	@RequestMapping(value="getPhoneCode",method = RequestMethod.POST)
	public void  getPhoneCode() throws IOException{
		JSONObject jObject=super.getRequestJsonParams();
		String num = phoneLaunchService.getCheckCode(jObject.getString("tel"));
		JSONObject jsonObject = new JSONObject();  
	    jsonObject.put("num",num);  
		returnSuccessJson(jsonObject.toString());
	}
}
