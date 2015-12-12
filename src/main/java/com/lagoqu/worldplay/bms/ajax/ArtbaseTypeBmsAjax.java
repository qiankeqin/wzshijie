package com.lagoqu.worldplay.bms.ajax;

import java.io.IOException;
import java.sql.SQLException;
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
import com.lagoqu.worldplay.bms.service.ArtbaseTypeBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
/**描述：文字类型库<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月5日下午5:08:30 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/artbaseType")
public class ArtbaseTypeBmsAjax extends BMSController{

	
	@Resource
	ArtbaseTypeBmsService artbaseTypeBmsService;
	
	
	/**方法名称: findPageData<br>
	 * 描述：文字类型查询
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
		Pagination<JSONArray> pagination=artbaseTypeBmsService.artbaseTypeList(page,size);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	
	
	/**方法名称: labelStoreadd<br>
	 * 描述：文字类型添加
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午2:55:03
	 * @throws IOException
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value="artbaseTypeadd",method = RequestMethod.POST)
	public void artbaseTypeadd() throws IOException, SQLException{
		Map<String,String[]> map =super.getRequestParamsMap();
		int crowdfundmarkID=artbaseTypeBmsService.artbaseTypeadd(map);
		JSONObject jsonObject = new JSONObject();  
	    jsonObject.put("crowdfundmarkID",crowdfundmarkID);  
		returnSuccessJson(jsonObject.toString());
	}
	
	
	
	
	/**方法名称: updateLabelStore<br>
	 * 描述：文字类型修改
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日下午3:18:01
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateArtbaseType", method = RequestMethod.PUT)
	public void updateArtbaseType() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		String operationName=super.getLoginUser();
		boolean state=artbaseTypeBmsService.updateArtbaseType(map,operationName);
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
	public void delBatch(@RequestParam(value = "ids[]", required = true) int[] ids,@RequestParam(value = "artbaseTypeState", required = true) int artbaseTypeState) throws SQLException, IOException {
		String operationName=super.getLoginUser();
		for (int i = 0; i < ids.length; i++) {
			int id = ids[i];
			artbaseTypeBmsService.deleteArtbaseType(id,artbaseTypeState,operationName);
		}
		returnSuccessJson("true");
	}
	
	
	
	/**方法名称: findAllArtbaseType<br>
	 * 描述：文字类型查询
	 * 作者: 王小欢
	 * 修改日期：2015年11月5日上午11:15:35
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="findAllArtbaseType",method = RequestMethod.POST)
	public void findAllArtbaseType() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		JSONArray jsonArray=artbaseTypeBmsService.findAllJson();
		returnSuccessJson(jsonArray.toString());
	}
}
