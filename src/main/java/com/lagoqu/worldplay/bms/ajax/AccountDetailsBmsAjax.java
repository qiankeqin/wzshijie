package com.lagoqu.worldplay.bms.ajax;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.bms.service.AccountDetailsBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;

@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/aDetails")
public class AccountDetailsBmsAjax extends BMSController{

	@Resource
	AccountDetailsBmsService accountDetailsBmsService;
	/**方法名称: findPageData<br>
	 * 描述：查询个人账户明细
	 * 作者: 王小欢
	 * 修改日期：2015年7月23日下午6:31:12
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="list",method = RequestMethod.POST)
	public void findPageData() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		int membersID=Integer.parseInt(map.get("membersID")[0]);
		Pagination<JSONArray> pagination=accountDetailsBmsService.aDetailsList(page,size,membersID);
		returnJSONEasyUISuccess(pagination);
	}
}
