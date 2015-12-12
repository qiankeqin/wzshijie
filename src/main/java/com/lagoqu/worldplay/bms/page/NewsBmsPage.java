package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lagoqu.worldplay.bms.service.NewsBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.News;

@Controller
@Scope("prototype")
@RequestMapping("/bms/news")
public class NewsBmsPage extends BMSController{
	@Resource
	private NewsBmsService newsBmsService;
	/**
	 * 方法名称: list<br>
	 * 描述：哪好玩列表页
	 * 作者: 邢留杰
	 * 修改日期：2015年12月7日下午3:36:16
	 * @return
	 */
	@RequestMapping("list")
	public String list(){
		return "bms/news/news.html";
	}
	/**
	 * 方法名称: edit<br>
	 * 描述：编辑页面
	 * 作者: 邢留杰
	 * 修改日期：2015年12月7日下午3:38:06
	 * @param newsId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public String edit(@RequestParam(value = "newsId", required = true) int newsId) throws Exception {
		News news = this.newsBmsService.get(newsId);
		super.setRequestAttibute("news", news);
		return "bms/news/news_edit.html";
	}
}
