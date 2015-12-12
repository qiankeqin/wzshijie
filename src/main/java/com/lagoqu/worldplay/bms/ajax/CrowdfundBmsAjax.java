package com.lagoqu.worldplay.bms.ajax;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.bms.service.CrowdfundBmsService;
import com.lagoqu.worldplay.bms.service.TrackRecordBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/crowdfund")
public class CrowdfundBmsAjax extends BMSController{

	@Resource
	CrowdfundBmsService crowdfundBmsService;
	
	@Resource
	TrackRecordBmsService trackRecordBmsService;
	
	/**方法名称: findPageData<br>
	 * 描述：心愿查询
	 * 作者: 王小欢
	 * 修改日期：2015年7月20日上午11:22:00
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="list",method = RequestMethod.POST)
	public void findPageData() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		String membersPhone=map.get("membersPhone")[0];
		int crowdfundIsDel=Integer.parseInt(map.get("crowdfundIsDel")[0]);
		int crowdfundType=Integer.parseInt(map.get("crowdfundType")[0]);
		Pagination<JSONArray> pagination=crowdfundBmsService.crowdfundList(page,size,membersPhone,crowdfundIsDel,crowdfundType);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	
	/**方法名称: crowdfundRdList<br>
	 * 描述：推荐的心愿或足迹
	 * 作者: 王小欢
	 * 修改日期：2015年12月8日上午10:28:25
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="crowdfundRdList",method = RequestMethod.POST)
	public void crowdfundRdList() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		String membersPhone=map.get("membersPhone")[0];
		int crowdfundIsDel=Integer.parseInt(map.get("crowdfundIsDel")[0]);
		int crowdfundType=Integer.parseInt(map.get("crowdfundType")[0]);
		Pagination<JSONArray> pagination=crowdfundBmsService.crowdfundRdList(page,size,membersPhone,crowdfundIsDel,crowdfundType);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	
	
	/**方法名称: delBatch<br>
	 * 描述：删除心愿
	 * 作者: 王小欢
	 * 修改日期：2015年7月20日下午1:29:31
	 * @param ids
	 * @param crowdfundIsDel
	 * @throws SQLException
	 * @throws IOException
	 */
	@RequestMapping(value = "delete", method = RequestMethod.PUT)
	public void delBatch(@RequestParam(value = "ids[]", required = true) int[] ids,@RequestParam(value = "crowdfundIsDel", required = true) int crowdfundIsDel) throws SQLException, IOException {
		String operationName=super.getLoginUser();
		for (int i = 0; i < ids.length; i++) {
			int id = ids[i];
			crowdfundBmsService.deleteCrowdfund(id,crowdfundIsDel,operationName);
		}
		returnSuccessJson("true");
	}
	
	
	
	
	
	/**方法名称: updateRd<br>
	 * 描述：修改心愿或足迹推荐状态
	 * 作者: 王小欢
	 * 修改日期：2015年12月8日上午10:34:57
	 * @param id
	 * @param crowdfundRdState
	 * @throws SQLException
	 * @throws IOException
	 */
	@RequestMapping(value = "updateRd", method = RequestMethod.PUT)
	public void updateRd(@RequestParam(value = "id", required = true) int id,@RequestParam(value = "crowdfundRdState", required = true) int crowdfundRdState) throws SQLException, IOException {
		String operationName=super.getLoginUser();
		crowdfundBmsService.updateRd(id,crowdfundRdState,operationName);
		returnSuccessJson("true");
	}
	
	
	
	/**方法名称: save<br>
	 * 描述：用户发布心愿,心愿保存
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日上午10:10:08
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value="save",method = RequestMethod.PUT)
	public void save() throws IOException, ParseException{
		Map<String,String[]> map =super.getRequestParamsMap();
		int crowdfundID=crowdfundBmsService.saveCrowdfund(map);
		if(crowdfundID==-1){
			returnFailJson("false");
		}else{
 
			returnSuccessJson("true");
		}
	}
	
	
	
	
	/**方法名称: saveZuji<br>
	 * 描述：用户发布足迹详情,足迹详情保存
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日上午10:10:08
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value="saveZuji",method = RequestMethod.PUT)
	public void saveZuji() throws IOException, ParseException{
		Map<String,String[]> map =super.getRequestParamsMap();
		int trackRecordID=trackRecordBmsService.saveTrackRecord(map);
		if(trackRecordID==-1){
			returnFailJson("false");
		}else{
 
			returnSuccessJson("true");
		}
	}
	
	
	
	/**方法名称: updateCrowdfund<br>
	 * 描述：助理编辑
	 * 作者: 王小欢
	 * 修改日期：2015年7月23日下午6:30:31
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateCrowdfund", method = RequestMethod.PUT)
	public void updateCrowdfund() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		String operationName=super.getLoginUser();
		crowdfundBmsService.updateCrowdfund(map,operationName);
        returnSuccessJson("true");
	}
	
	
	
	
	
	
	/**方法名称: crowdfundCountRmb<br>
	 * 描述：打赏记录大于3000RMB的订单信息
	 * 作者: 王小欢
	 * 修改日期：2015年11月19日上午10:54:57
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="crowdfundCountRmb",method = RequestMethod.POST)
	public void crowdfundCountRmb() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		Pagination<JSONArray> pagination=crowdfundBmsService.crowdfundCountRmb(page,size);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	/**方法名称: crowdfundCountRmbSum<br>
	 * 描述：打赏记录大于3000RMB的订单总额
	 * 作者: 王小欢
	 * 修改日期：2015年12月11日下午4:05:20
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="crowdfundCountRmbSum",method = RequestMethod.POST)
	public void crowdfundCountRmbSum() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		JSONArray sumPrice=crowdfundBmsService.crowdfundCountRmbSum();
		returnSuccessJson(sumPrice.get(0).toString());
	}
}
