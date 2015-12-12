package com.lagoqu.worldplay.bms.ajax;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.lagoqu.common.exception.DBException;
import com.lagoqu.common.framework.controller.TimestampEditor;
import com.lagoqu.common.framework.controller.UtilDateEditor;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.bms.service.NewsBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.News;
import com.lagoqu.worldplay.util.Upload;

@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/news")
public class NewsBmsAjax extends BMSController{
	@Resource
	private NewsBmsService newsBmsService;
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		if (binder.getTarget() instanceof News) {//此处为实体类
			binder.registerCustomEditor(java.sql.Timestamp.class, new TimestampEditor());			
			binder.registerCustomEditor(java.util.Date.class, new UtilDateEditor());
		}
	}
	/**方法名称: findPageData<br>
	 * 描述：新闻内容查询
	 * 作者: 王小欢
	 * 修改日期：2015年3月26日下午2:02:47
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="list" ,method = RequestMethod.POST)
	public void findPageData() throws Exception{
		String page = super.getRequestParameter("page");
		String rows = super.getRequestParameter("rows");
		String newsTitle = super.getRequestParameter("newsTitle");
		Pagination<JSONArray> p=this.newsBmsService.list(page,rows,newsTitle);
		returnJSONEasyUISuccess(p);
	}
	/**方法名称: add<br>
	 * 描述：新闻内容添加
	 * 作者: 王小欢
	 * 修改日期：2015年3月26日下午2:02:26
	 * @param rows
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws DBException 
	 */
	@RequestMapping(value="add" ,method = RequestMethod.POST)
	public void add(@ModelAttribute("rows") News rows) throws IOException, DBException {
		boolean bl = this.newsBmsService.insert(rows);
		if(bl){
			returnSuccessJson("新增成功");
		}else{
			returnFailJson("新增失败");
		}
		
	}
	/**方法名称: update<br>
	 * 描述：新闻内容修改
	 * 作者: 王小欢
	 * 修改日期：2015年3月26日下午2:02:09
	 * @param news
	 * @throws Exception 
	 */
	@RequestMapping(value="interfaceEdit" ,method = RequestMethod.POST)
	public void interfaceEdit(@ModelAttribute("news") News news) throws Exception {
		this.newsBmsService.interfaceUpdateNews(news);
		returnSuccessJson("修改成功");
	}
	
	/**方法名称: update<br>
	 * 描述：新闻内容修改
	 * 作者: 王小欢
	 * 修改日期：2015年3月26日下午2:02:09
	 * @param news
	 * @throws Exception 
	 */
	@RequestMapping(value="edit" ,method = RequestMethod.POST)
	public void update(@ModelAttribute("news") News news) throws Exception {
		this.newsBmsService.updateNews(news);
		returnSuccessJson("修改成功");
	}
		
	/**方法名称: delBatch<br>
	 * 描述：新闻内容删除
	 * 作者: 王小欢
	 * 修改日期：2015年3月26日下午2:01:55
	 * @param ids
	 * @return
	 * @throws IOException
	 * @throws DBException 
	 */
	@RequestMapping(value="delete", method = RequestMethod.PUT)
	public void delBatch(@RequestParam(value = "ids[]", required = true) int[] ids) throws IOException, DBException {
		for (int i = 0; i < ids.length; i++) {
			int id = ids[i];
			newsBmsService.delete(id);
		}
		returnSuccessJson("删除成功");
	}
	/**
	 * 方法名称: delBatch<br>
	 * 描述：删除
	 * 作者: 邢留杰
	 * 修改日期：2015年12月10日下午3:27:44
	 * @param id
	 * @throws Exception 
	 */
	@RequestMapping(value="interfaceDelete", method = RequestMethod.POST)
	public void delBatch(@RequestParam(value = "id", required = true) long id) throws Exception {
		newsBmsService.interfaceDelete(id);
		returnSuccessJson("删除成功");
	}
	
		
	/**
	 * 方法名称: upload<br>
	 * 描述：图片上传
	 * 作者: 邢留杰
	 * 修改日期：2015年12月9日上午11:58:03
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public void upload() throws IOException {
		// 图片上传
		Upload up = new Upload();
		// 获得图片名称list
		List imageList = up.uploadrelative2(request,"news");
		// 保存用户上传照片
		returnSuccessJson(imageList.get(0).toString());
	}
}
