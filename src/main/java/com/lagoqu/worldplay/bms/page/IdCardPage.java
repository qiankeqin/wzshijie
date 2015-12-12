package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.entity.IdCard;
import com.lagoqu.worldplay.entity.IdCardRecord;
import com.lagoqu.worldplay.service.IdCardRecordService;
import com.lagoqu.worldplay.service.IdCardService;

@Controller
@Scope("prototype")
@RequestMapping("/bms/idCard")
public class IdCardPage extends APIController{
	@Resource
	IdCardService idCardService;
	@Resource
	IdCardRecordService idCardRecordService;
	/**
	 * 方法名称: undisposed<br>
	 * 描述：身份证 待审核页面
	 * 作者: 邢留杰
	 * 修改日期：2015年7月19日下午1:21:41
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("unaudit")
	public String undisposed() throws Exception{
		return "bms/identity/unaudit.html";
	}
	/**
	 * 方法名称: undisposed<br>
	 * 描述：身份证 待审核页面
	 * 作者: 邢留杰
	 * 修改日期：2015年7月19日下午1:21:41
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("audit")
	public String disposed() throws Exception{
		return "bms/identity/audit.html";
	}
	/**
	 * 方法名称: showUnaudit<br>
	 * 描述：查看审核情况
	 * 作者: 邢留杰
	 * 修改日期：2015年7月19日下午2:37:19
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showUnaudit")
	public String showUnaudit(@RequestParam(value = "idCardId", required = true) int idCardId) throws Exception{
		IdCard ic = idCardService.get(idCardId);
		super.setRequestAttibute("ic", ic);
		return "bms/identity/showunaudit.html";
	}
	
	@RequestMapping("showList")
	public String showList(@RequestParam(value = "idCardRecordId", required = true) int idCardRecordId) throws Exception{
		IdCardRecord ic = idCardRecordService.get(idCardRecordId);
		super.setRequestAttibute("ic", ic);
		return "bms/identity/showaudit.html";
	}
}
