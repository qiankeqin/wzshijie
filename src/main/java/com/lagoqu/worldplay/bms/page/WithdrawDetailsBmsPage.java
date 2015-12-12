package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.bms.service.AccountsBmsService;
import com.lagoqu.worldplay.bms.service.WithdrawDetailsBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.service.IdCardService;
@Controller
@Scope("prototype")
@RequestMapping("/bms/wDetails")
public class WithdrawDetailsBmsPage extends BMSController{

	@Resource
	WithdrawDetailsBmsService withdrawDetailsBmsService;
	
	@Resource
	AccountsBmsService accountsBmsService;
	
	@Resource
	IdCardService idCardService;
	/**方法名称: undisposed<br>
	 * 描述：
	 * 作者: 王小欢
	 * 修改日期：2015年7月19日上午11:50:38
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("undisposed")
	public String undisposed() throws Exception{
		
		return "bms/wDetails/wDUndisposedList.html";
	}
	
	
	@RequestMapping("wDSuccessList")
	public String wDSuccessList() throws Exception{
		
		return "bms/wDetails/wDSuccessList.html";
	}
	
	
	@RequestMapping("wDDetails/{membersID}/{wDetailsID}")
	public String wDDetails(@PathVariable("membersID") int membersID,@PathVariable("wDetailsID") int wDetailsID) throws Exception{
		Object Accounts=accountsBmsService.findByIdAccounts(membersID);
		Object WDetails=withdrawDetailsBmsService.findByIdWDetails(wDetailsID);
		Object IdCords=idCardService.findByIdCords(membersID);
		super.setRequestAttibute("Accounts", Accounts);
		super.setRequestAttibute("WDetails", WDetails);
		super.setRequestAttibute("IdCords", IdCords);
		return "bms/wDetails/wDDetails.html";
	}
	

}
