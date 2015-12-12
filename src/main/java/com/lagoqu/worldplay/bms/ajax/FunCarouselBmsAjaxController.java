package com.lagoqu.worldplay.bms.ajax;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

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
import com.lagoqu.worldplay.bms.service.FunCarouselBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.FunCarousel;
import com.lagoqu.worldplay.util.Upload;

@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/funCarousel")
public class FunCarouselBmsAjaxController extends BMSController{
	@Resource
	private FunCarouselBmsService funCarouselBmsService;
	
	/**
	 * 方法名称: initBinder<br>
	 * 描述：时间转化
	 * 作者: 邢留杰
	 * 修改日期：2015年10月31日下午8:12:18
	 * @param binder
	 * @param request
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		if (binder.getTarget() instanceof FunCarousel) {//此处为实体类
			binder.registerCustomEditor(java.sql.Timestamp.class, new TimestampEditor());			
			binder.registerCustomEditor(java.util.Date.class, new UtilDateEditor());
		}
	}
	/**
	 * 方法名称: findPageData<br>
	 * 描述：列表
	 * 作者: 邢留杰
	 * 修改日期：2015年12月8日下午2:23:19
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	public void findPageData() throws Exception{
		String page = super.getRequestParameter("page");
		String rows = super.getRequestParameter("rows");
		Pagination<FunCarousel> p = this.funCarouselBmsService.FunCarouselList(page ,rows);
		returnJSONEasyUISuccess(p);
	}
	/**
	 * 方法名称: add<br>
	 * 描述：新增
	 * 作者: 邢留杰
	 * 修改日期：2015年12月8日下午2:17:28
	 * @param rows
	 * @throws IOException
	 * @throws DBException
	 */
	@RequestMapping(value="add",method = RequestMethod.POST)
	public void add(@ModelAttribute("rows") FunCarousel rows) throws IOException, DBException {
		boolean bl = funCarouselBmsService.insert(rows);
		if(bl){
			returnSuccessJson("新增成功");
		}else{
			returnFailJson("新增失败");
		}
	}
	/**
	 * 方法名称: update<br>
	 * 描述：修改
	 * 作者: 邢留杰
	 * 修改日期：2015年12月8日下午2:20:07
	 * @param funCarousel
	 * @throws Exception
	 */
	@RequestMapping(value="update",method = RequestMethod.POST)
	public void update(@ModelAttribute("funCarousel") FunCarousel funCarousel) throws Exception {
		boolean bl = funCarouselBmsService.update(funCarousel);
		if(bl){
			returnSuccessJson("修改成功");
		}else{
			returnFailJson("修改失败");
		}
	}
	/**
	 * 方法名称: delBatch<br>
	 * 描述：删除
	 * 作者: 邢留杰
	 * 修改日期：2015年12月8日下午2:20:33
	 * @param ids
	 * @throws IOException
	 * @throws DBException
	 */
	@RequestMapping(value="delete", method = RequestMethod.POST)
	public void delete(@RequestParam(value = "ids[]", required = true) int[] ids) throws IOException, DBException {
		for (int i = 0; i < ids.length; i++) {
			int id = ids[i];
			this.funCarouselBmsService.delete(id);
		}
		returnSuccessJson("删除成功");
	}
	/**
	 * 方法名称: uploadUserPicture<br>
	 * 描述：图片上传
	 * 作者: 邢留杰
	 * 修改日期：2015年12月8日下午2:22:55
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public void upload() throws IOException {
		Upload up = new Upload();
		List<String> imageList = up.uploadrelative2(request,"funCarousel");		
		returnSuccessJson(imageList.get(0).toString());	
		
	}
}
