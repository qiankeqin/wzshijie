package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.bms.service.CarouselBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.Carousel;
/**描述：首页轮播图<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月5日上午10:59:27 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/bms/carousel")
public class CarouselBmsPage extends BMSController{

	@Resource
	CarouselBmsService carouselBmsService;
	
	
	@RequestMapping
	public String index() {
		return "bms/carousel/carousel.html";
	}
	
	@RequestMapping("add")
	public String add() {		
		return "bms/carousel/carousel_add.html";
	}
	
	@RequestMapping("{id}/edit")
	public String edit(@PathVariable("id") int id) throws Exception {
		Carousel carousel = this.carouselBmsService.get(id);
		super.setRequestAttibute("carousel", carousel);
		return "bms/carousel/carousel_edit.html";
	}
}
