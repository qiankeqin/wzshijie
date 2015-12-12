package com.lagoqu.worldplay.api;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.service.IdCardService;

@Controller
@Scope("prototype")
@RequestMapping("/idCard")
public class IdCardApi extends APIController{
	@Resource
	IdCardService idCardService;
	/**
	 * 方法名称: findList<br>
	 * 描述：待审核列表
	 * 作者: 邢留杰
	 * 修改日期：2015年7月19日下午1:02:16
	 * @throws IOException
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value="list",method = RequestMethod.POST)
	public void findList() throws IOException, ParseException{
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		String membersPhone = null;
		if(map.get("membersPhone")!=null){
			membersPhone = map.get("membersPhone")[0];
		}
		Pagination<JSONArray> pagination=idCardService.findPageData(page,size,membersPhone);
		returnJSONEasyUISuccess(pagination);
	}
	/**
	 * 方法名称: review<br>
	 * 描述：审核
	 * 作者: 邢留杰
	 * 修改日期：2015年7月19日下午4:46:26
	 * @throws IOException
	 * @throws ParseException
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value="review",method = RequestMethod.POST)
	public void review() throws IOException, ParseException, SQLException{
		int idCardId = Integer.parseInt(request.getParameter("idCardId"));
		String idCardRecordState = request.getParameter("idCardRecordState");
		String reviewFlag = request.getParameter("reviewFlag");
		if("1".equals(reviewFlag)){
			idCardService.reviewSuccess(idCardId,idCardRecordState);
		}
		if("0".equals(reviewFlag)){
			idCardService.reviewFail(idCardId,idCardRecordState);
		}
		returnSuccessJson("success");
	}

}
