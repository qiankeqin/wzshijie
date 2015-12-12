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
import com.lagoqu.worldplay.bms.service.PhotoGalleryBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.util.Upload;
/**描述：图片库<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月5日下午5:08:30 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/photoGallery")
public class PhotoGalleryBmsAjax extends BMSController{

	
	@Resource
	PhotoGalleryBmsService photoGalleryBmsService;
	
	
	/**方法名称: findPageData<br>
	 * 描述：图片库查询
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
		Pagination<JSONArray> pagination=photoGalleryBmsService.photoGalleryList(page,size);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	
	
	/**方法名称: labelStoreadd<br>
	 * 描述：图片库添加
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午2:55:03
	 * @throws IOException
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value="photoGalleryadd",method = RequestMethod.POST)
	public void photoGalleryadd() throws IOException, SQLException{
		Map<String,String[]> map =super.getRequestParamsMap();
		int crowdfundmarkID=photoGalleryBmsService.photoGalleryadd(map);
		JSONObject jsonObject = new JSONObject();  
	    jsonObject.put("crowdfundmarkID",crowdfundmarkID);  
		returnSuccessJson(jsonObject.toString());
	}
	
	
	
	
	/**方法名称: updatePhotoGallery<br>
	 * 描述：图片库修改
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:18:01
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updatePhotoGallery", method = RequestMethod.PUT)
	public void updatePhotoGallery() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		String operationName=super.getLoginUser();
		boolean state=photoGalleryBmsService.updatePhotoGallery(map,operationName);
		if(state==true){
			 returnSuccessJson("true");
		}else{
			returnFailJson("失败");
		}
	}
	
	
	
	
	/**方法名称: delBatch<br>
	 * 描述：图片库删除
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:17:50
	 * @param ids
	 * @param carouselState
	 * @throws SQLException
	 * @throws IOException
	 */
	@RequestMapping(value = "delete", method = RequestMethod.PUT)
	public void delBatch(@RequestParam(value = "ids[]", required = true) int[] ids,@RequestParam(value = "photoGalleryState", required = true) int photoGalleryState) throws SQLException, IOException {
		String operationName=super.getLoginUser();
		for (int i = 0; i < ids.length; i++) {
			int id = ids[i];
			photoGalleryBmsService.deletePhotoGallery(id,photoGalleryState,operationName);
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
		List imageList = up.uploadrelative2(request,"PhotoGallery");
		
		// 保存用户上传照片
		returnSuccessJson(imageList.get(0).toString());
	}
}
