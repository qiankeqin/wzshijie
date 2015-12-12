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
import com.lagoqu.worldplay.service.AccountDetailsService;
@Controller
@Scope("prototype")
@RequestMapping("/AccountDetails")
public class AccountDetailsApi extends APIController{

	@Resource
	AccountDetailsService accountDetailsService;
	
	
	/**方法名称: aDetailsList<br>
	 * 描述：查询个人账户明细
	 * 作者: 王小欢
	 * 修改日期：2015年7月7日上午9:48:37
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="aDetailsList",method = RequestMethod.POST)
	public void aDetailsList() throws IOException{
		JSONObject jObject=super.getRequestJsonParams();
		int page=jObject.getInt("page");
		int size=jObject.getInt("size");
		int membersID=jObject.getInt("membersID");
		Pagination<JSONArray> pagination=accountDetailsService.aDetailsList(page,size,membersID);
		returnSuccessJson(pagination.toString());
	}
}
