package com.lagoqu.worldplay.api;

import java.io.IOException;

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
import com.lagoqu.worldplay.service.WithdrawDetailsService;
@Controller
@Scope("prototype")
@RequestMapping("/WithdrawDetails")
public class WithdrawDetailsApi extends APIController{

	
	@Resource
	WithdrawDetailsService withdrawDetailsService;
	
	
	/**方法名称: wdetailsSuc<br>
	 * 描述：用户提现后台操作
	 * 作者: 王小欢
	 * 修改日期：2015年7月7日下午1:26:54
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="wdetailsSuc",method = RequestMethod.POST)
	public void wdetailsSuc() throws IOException{
		JSONObject jObject=super.getRequestJsonParams();
		int wDetailsID=jObject.getInt("wDetailsID");
		boolean state=withdrawDetailsService.wdetailsSuc(wDetailsID);
		if(state=true){
			returnSuccessJson("true");
		}else{
			returnFailJson("提现失败");
		}
		
	}
}
