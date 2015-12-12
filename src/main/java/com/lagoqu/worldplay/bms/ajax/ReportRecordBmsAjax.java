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
import com.lagoqu.worldplay.bms.service.ReportRecordBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.util.Upload;
/**描述：举报记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年11月5日下午4:30:33 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/reportRecord")
public class ReportRecordBmsAjax extends BMSController{

	
	@Resource
	ReportRecordBmsService reportRecordBmsService;
	
	
	/**方法名称: findPageData<br>
	 * 描述：举报记录查询
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
		Pagination<JSONArray> pagination=reportRecordBmsService.reportRecordList(page,size);
		returnJSONEasyUISuccess(pagination);
	}

}
