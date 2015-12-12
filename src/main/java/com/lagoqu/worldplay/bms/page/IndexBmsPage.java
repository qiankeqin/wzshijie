package com.lagoqu.worldplay.bms.page;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.common.controller.BMSController;

@Controller
@Scope("prototype")
@RequestMapping("/bms")
public class IndexBmsPage extends BMSController {
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String toIndex() throws Exception {
		if (isUserLogin()) {
			String userName=getLoginUser();
			super.setRequestAttibute("userName", userName);
			return "bms/index.html";
		} else {
			return "bms/login.html";
		}
	}

	@RequestMapping(value = "loginOut", method = RequestMethod.GET)
	public String toLogin() throws Exception {
		loginout();
		return "bms/login.html";
	}
	@RequestMapping(value = "getServiceCode", method = RequestMethod.GET)
	public String getServiceCode() throws Exception{
		return "bms/ArtificialServices/checkCodeServices.html";
	}
}
