package com.lagoqu.worldplay.api.page;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.service.AppNewsService;

@Controller
@Scope("prototype")
@RequestMapping("/news")
public class NewsPage extends APIController{
	@Resource
	private AppNewsService appNewsService;
	/**
	 * 方法名称: newsDetail<br>
	 * 描述：哪好玩详情
	 * 作者: 邢留杰
	 * 修改日期：2015年12月9日下午1:52:33
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "newsDetail/{id}", method = RequestMethod.GET)
	public String newsDetail(@PathVariable("id") int id) throws Exception{
		JSONObject backJsonObject=appNewsService.newsDetail(id);
		super.setRequestAttibute("news", backJsonObject.get("data"));
		return "v2/newDetail.html";
	}
}
