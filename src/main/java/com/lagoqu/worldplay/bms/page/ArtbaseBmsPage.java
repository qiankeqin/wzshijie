package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.bms.service.ArtbaseBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.Artbase;
/**描述：文字类型库<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月5日上午10:59:27 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/bms/artbase")
public class ArtbaseBmsPage extends BMSController{

	@Resource
	ArtbaseBmsService artbaseBmsService;
	
	
	@RequestMapping
	public String index() {
		return "bms/artbase/artbase.html";
	}
	
	@RequestMapping("add")
	public String add() {		
		return "bms/artbase/artbase_add.html";
	}
	
	@RequestMapping("{id}/edit")
	public String edit(@PathVariable("id") int id) throws Exception {
		Artbase artbase = this.artbaseBmsService.get(id);
		super.setRequestAttibute("artbase", artbase);
		return "bms/artbase/artbase_edit.html";
	}
}
