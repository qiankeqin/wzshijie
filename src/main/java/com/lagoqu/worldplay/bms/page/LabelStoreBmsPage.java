package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.bms.service.LabelStoreBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.LabelStore;
/**描述：标签库<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月5日上午10:59:27 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/bms/labelStore")
public class LabelStoreBmsPage extends BMSController{

	@Resource
	LabelStoreBmsService labelStoreBmsService;
	
	
	@RequestMapping
	public String index() {
		return "bms/labelStore/labelStore.html";
	}
	
	@RequestMapping("add")
	public String add() {		
		return "bms/labelStore/labelStore_add.html";
	}
	
	@RequestMapping("{id}/edit")
	public String edit(@PathVariable("id") int id) throws Exception {
		LabelStore labelStore = this.labelStoreBmsService.get(id);
		super.setRequestAttibute("labelStore", labelStore);
		return "bms/labelStore/labelStore_edit.html";
	}
}
