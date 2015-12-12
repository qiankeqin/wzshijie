package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.bms.service.ReportRecordBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.ReportRecord;
/**描述：举报记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月5日下午4:26:54 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/bms/reportRecord")
public class ReportRecordBmsPage extends BMSController{

	
	@Resource
	ReportRecordBmsService reportRecordBmsService;
	
	
	@RequestMapping
	public String index() {
		return "bms/reportRecord/reportRecord.html";
	}
	
	
	
	@RequestMapping("{id}/edit")
	public String edit(@PathVariable("id") int id) throws Exception {
		ReportRecord reportRecord = this.reportRecordBmsService.get(id);
		super.setRequestAttibute("reportRecord", reportRecord);
		return "bms/reportRecord/reportRecord_edit.html";
	}
}
