package com.lagoqu.worldplay.bms.ajax;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.bms.service.CarouselBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.util.Upload;
@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/carousel")
public class CarouselBmsAjax extends BMSController{

	
	@Resource
	CarouselBmsService carouselBmsService;
	
	
	/**方法名称: findPageData<br>
	 * 描述：轮播图查询
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日上午11:15:35
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="list",method = RequestMethod.POST)
	public void findPageData() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		int carouselState=Integer.parseInt(map.get("carouselState")[0]);
		Pagination<JSONArray> pagination=carouselBmsService.carouselList(page,size,carouselState);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	
	
	/**方法名称: carouseladd<br>
	 * 描述：轮播图添加
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午2:55:03
	 * @throws IOException
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value="carouseladd",method = RequestMethod.POST)
	public void carouseladd() throws IOException, SQLException{
		Map<String,String[]> map =super.getRequestParamsMap();
		int crowdfundmarkID=carouselBmsService.carouseladd(map);
		JSONObject jsonObject = new JSONObject();  
	    jsonObject.put("crowdfundmarkID",crowdfundmarkID);  
		returnSuccessJson(jsonObject.toString());
	}
	
	
	
	
	/**方法名称: updateCarousel<br>
	 * 描述：轮播图修改
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:18:01
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateCarousel", method = RequestMethod.PUT)
	public void updateCarousel() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		String operationName=super.getLoginUser();
		boolean state=carouselBmsService.updateCarousel(map,operationName);
		if(state==true){
			 returnSuccessJson("true");
		}else{
			returnFailJson("失败");
		}
	}
	
	
	
	
	/**方法名称: delBatch<br>
	 * 描述：轮播图删除
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:17:50
	 * @param ids
	 * @param carouselState
	 * @throws SQLException
	 * @throws IOException
	 */
	@RequestMapping(value = "delete", method = RequestMethod.PUT)
	public void delBatch(@RequestParam(value = "ids[]", required = true) int[] ids,@RequestParam(value = "carouselState", required = true) int carouselState) throws SQLException, IOException {
		String operationName=super.getLoginUser();
		for (int i = 0; i < ids.length; i++) {
			int id = ids[i];
			carouselBmsService.deleteCarousel(id,carouselState,operationName);
		}
		returnSuccessJson("true");
	}
	
	
	
	
	/**方法名称: upload<br>
	 * 描述：轮播图上传
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:17:36
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public void upload() throws IOException {
		// 图片上传
		Upload up = new Upload();
		// 获得图片名称list
		List imageList = up.uploadrelative2(request,"carousel");
		
		// 保存用户上传照片
		returnSuccessJson(imageList.get(0).toString());
	}
}
