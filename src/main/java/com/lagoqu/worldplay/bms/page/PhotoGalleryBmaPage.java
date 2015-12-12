package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.bms.service.PhotoGalleryBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.PhotoGallery;
/**描述：图片库管理<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月6日上午11:07:08 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/bms/photoGallery")
public class PhotoGalleryBmaPage extends BMSController{

	
	@Resource
	PhotoGalleryBmsService photoGalleryBmsService;
	
	
	@RequestMapping
	public String index() {
		return "bms/photoGallery/photoGallery.html";
	}
	
	@RequestMapping("add")
	public String add() {		
		return "bms/photoGallery/photoGallery_add.html";
	}
	
	@RequestMapping("{id}/edit")
	public String edit(@PathVariable("id") int id) throws Exception {
		PhotoGallery photoGallery = this.photoGalleryBmsService.get(id);
		super.setRequestAttibute("photoGallery", photoGallery);
		return "bms/photoGallery/photoGallery_edit.html";
	}
}
