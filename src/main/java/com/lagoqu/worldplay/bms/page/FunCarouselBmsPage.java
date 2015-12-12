package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lagoqu.worldplay.bms.service.FunCarouselBmsService;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.entity.FunCarousel;

@Controller
@Scope("prototype")
@RequestMapping("/bms/funCarousel")
public class FunCarouselBmsPage extends APIController{
	@Resource
	private FunCarouselBmsService funCarouselBmsService;
	/**
	 * 方法名称: index<br>
	 * 描述：列表
	 * 作者: 邢留杰
	 * 修改日期：2015年12月8日下午2:11:50
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public String index() throws Exception{
		return "bms/funCarousel/funCarousel.html";
	}
	/**
	 * 方法名称: add<br>
	 * 描述：新增
	 * 作者: 邢留杰
	 * 修改日期：2015年12月8日下午2:11:31
	 * @return
	 */
	@RequestMapping("add")
	public String add() {
		return "bms/funCarousel/funCarousel_add.html";
	}
	/**
	 * 方法名称: edit<br>
	 * 描述：编辑
	 * 作者: 邢留杰
	 * 修改日期：2015年12月8日下午2:11:41
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public String edit(@RequestParam(value = "id", required = true) int id) throws Exception {
		FunCarousel funCarousel = funCarouselBmsService.get(id);
		super.setRequestAttibute("funCarousel", funCarousel);
		return "bms/funCarousel/funCarousel_edit.html";
	}
	
}
