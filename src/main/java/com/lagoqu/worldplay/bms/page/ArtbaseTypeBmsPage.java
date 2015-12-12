package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.bms.service.ArtbaseTypeBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.ArtbaseType;
/**描述：文字类型库<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月5日上午10:59:27 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/bms/artbaseType")
public class ArtbaseTypeBmsPage extends BMSController{

	@Resource
	ArtbaseTypeBmsService artbaseTypeBmsService;
	
	
	@RequestMapping
	public String index() {
		return "bms/artbaseType/artbaseType.html";
	}
	
	@RequestMapping("add")
	public String add() {		
		return "bms/artbaseType/artbaseType_add.html";
	}
	
	@RequestMapping("{id}/edit")
	public String edit(@PathVariable("id") int id) throws Exception {
		ArtbaseType artbaseType = this.artbaseTypeBmsService.get(id);
		super.setRequestAttibute("artbaseType", artbaseType);
		return "bms/artbaseType/artbaseType_edit.html";
	}
}
