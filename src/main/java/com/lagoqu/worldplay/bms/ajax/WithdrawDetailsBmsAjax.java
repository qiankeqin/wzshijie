package com.lagoqu.worldplay.bms.ajax;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.bms.service.WithdrawDetailsBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;

@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/wDetails")
public class WithdrawDetailsBmsAjax extends BMSController{

	@Resource
	WithdrawDetailsBmsService withdrawDetailsBmsService;
	
	/**方法名称: findPageData<br>
	 * 描述：未处理提现记录列表
	 * 作者: 王小欢
	 * 修改日期：2015年7月19日下午5:01:11
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="list",method = RequestMethod.POST)
	public void findPageData() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		String membersPhone=map.get("membersPhone")[0];
		Pagination<JSONArray> pagination=withdrawDetailsBmsService.wDetailsList(page,size,membersPhone);
		returnJSONEasyUISuccess(pagination);
	}
	
	/**方法名称: checkPass<br>
	 * 描述：提现记录审核通过
	 * 作者: 王小欢
	 * 修改日期：2015年7月19日下午5:01:49
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="checkPass",method = RequestMethod.POST)
	public void checkPass() throws IOException{
		Map<String,String[]> map =super.getRequestParamsMap();
		int wDetailsID=Integer.parseInt(map.get("wDetailsID")[0]);
		String operationName=super.getLoginUser();
		boolean state=withdrawDetailsBmsService.wdetailsSuc(wDetailsID,operationName);
		if(state=true){
			returnSuccessJson("true");
		}else{
			returnFailJson("false");
		}
		
	}
	
	
	
	/**方法名称: findPageDataSuccess<br>
	 * 描述：已审核通过提现记录列表
	 * 作者: 王小欢
	 * 修改日期：2015年7月19日下午5:04:24
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="listSuccess",method = RequestMethod.POST)
	public void findPageDataSuccess() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		String membersPhone=map.get("membersPhone")[0];
		Pagination<JSONArray> pagination=withdrawDetailsBmsService.wDetailsListSuccess(page,size,membersPhone);
		returnJSONEasyUISuccess(pagination);
	}

}
