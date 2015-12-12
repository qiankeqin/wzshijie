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
import com.lagoqu.worldplay.bms.service.ArtbaseBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.util.Upload;
/**描述：文字类型库<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月5日下午5:08:30 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/artbase")
public class ArtbaseBmsAjax extends BMSController{

	
	@Resource
	ArtbaseBmsService artbaseBmsService;
	
	
	/**方法名称: findPageData<br>
	 * 描述：文字查询
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
		Pagination<JSONArray> pagination=artbaseBmsService.artbaseList(page,size);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	
	
	/**方法名称: artbaseadd<br>
	 * 描述：文字添加
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午2:55:03
	 * @throws IOException
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value="artbaseadd",method = RequestMethod.POST)
	public void artbaseTypeadd() throws IOException, SQLException{
		Map<String,String[]> map =super.getRequestParamsMap();
		int artbaseID=artbaseBmsService.artbaseadd(map);
		JSONObject jsonObject = new JSONObject();  
	    jsonObject.put("artbaseID",artbaseID);  
		returnSuccessJson(jsonObject.toString());
	}
	
	
	
	
	/**方法名称: updateArtbase<br>
	 * 描述：文字修改
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:18:01
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateArtbase", method = RequestMethod.PUT)
	public void updateArtbaseType() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		String operationName=super.getLoginUser();
		boolean state=artbaseBmsService.updateArtbase(map,operationName);
		if(state==true){
			 returnSuccessJson("true");
		}else{
			returnFailJson("失败");
		}
	}
	
	
	
	
	/**方法名称: delBatch<br>
	 * 描述：文字类型删除
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:17:50
	 * @param ids
	 * @param carouselState
	 * @throws SQLException
	 * @throws IOException
	 */
	@RequestMapping(value = "delete", method = RequestMethod.PUT)
	public void delBatch(@RequestParam(value = "ids[]", required = true) int[] ids,@RequestParam(value = "artbaseState", required = true) int artbaseState) throws SQLException, IOException {
		String operationName=super.getLoginUser();
		for (int i = 0; i < ids.length; i++) {
			int id = ids[i];
			artbaseBmsService.deleteArtbase(id,artbaseState,operationName);
		}
		returnSuccessJson("true");
	}
	
	
	
	/**方法名称: upload<br>
	 * 描述：图上传
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
		List imageList = up.uploadrelative2(request,"artbase");
		
		// 保存用户上传照片
		returnSuccessJson(imageList.get(0).toString());
	}
}
