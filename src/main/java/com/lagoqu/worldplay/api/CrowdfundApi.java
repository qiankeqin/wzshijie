package com.lagoqu.worldplay.api;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.service.CrowdfundAttentionService;
import com.lagoqu.worldplay.service.CrowdfundService;
import com.lagoqu.worldplay.util.EmojiFilter;

/**描述：旅游助力<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月4日下午6:05:16 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/help")
public class CrowdfundApi extends APIController{

	@Resource
	CrowdfundService crowdfundService;
	@Resource
	CrowdfundAttentionService crowdfundAttentionService;
	/**方法名称: list<br>
	 * 描述：用于获取助力列表
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日上午10:03:36
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="list",method = RequestMethod.POST)
	public void list() throws IOException{
		JSONObject jObject=super.getRequestJsonParams();
		int page=jObject.getInt("page");
		int size=jObject.getInt("size");
		String updateTime = null;
		if(jObject.containsKey("updateTime")){
			updateTime = jObject.getString("updateTime");
		}
		int memberID = 0;
		if(jObject.get("membersID")!=null){
			memberID=jObject.getInt("membersID");
		}
		Pagination<JSONArray> pagination=crowdfundService.listCrowdfund(page,size,memberID,updateTime);
		returnSuccessJson(pagination.toString());
	}
	/**
	 * 方法名称: crowdfundDetail<br>
	 * 描述：助力详情
	 * 作者: 邢留杰
	 * 修改日期：2015年6月15日下午1:47:11
	 * @throws IOException
	 * @throws SQLException 
	 */
	@ResponseBody
	@RequestMapping(value="crowdfundDetail",method = RequestMethod.POST)
	public void crowdfundDetail() throws IOException, SQLException{
		JSONObject jObject=super.getRequestJsonParams();
		int page=jObject.getInt("page");
		int size=jObject.getInt("size");
		int crowdfundID=jObject.getInt("crowdfundID");
		//每查看一次记录增加1
		crowdfundService.updateCrowdfundById(crowdfundID);
		//助力信息
		JSONArray js = crowdfundService.getCrowdfundById(crowdfundID);
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String et = js.getJSONObject(0).getString("endTime");
		Timestamp endTime = Timestamp.valueOf(et);
		boolean flag = true;
		if(ts.getTime()>endTime.getTime()){
			flag = false;
		}
		//关注助力的人
		JSONArray jsabci = crowdfundService.getAttentionByCrowdfundID(crowdfundID);
		//赞助人的列表
		Pagination<JSONArray> pjs=crowdfundService.findMembersByCrowdfundID(page, size, crowdfundID);
		
		boolean bl = false;
		if(jObject.get("membersID") != null){
			int membersID=jObject.getInt("membersID");
			JSONObject b = crowdfundAttentionService.ifAttention(membersID,crowdfundID);
			if(b!=null){
				bl=true;
			}else{
				bl=false;
			}
		}else{
			bl = false;
		}
		JSONObject a=new JSONObject();
		a.put("crowdfundInfo", js);
		a.put("attentionInfo", jsabci);
		a.put("crowdfundMarkInfo", pjs);
		a.put("ifAttention", bl);
		a.put("flag", flag);
		returnSuccessJson(a.toString());
	}
	/**
	 * 方法名称: crowdfundListByPage<br>
	 * 描述：详情助力列表
	 * 作者: 邢留杰
	 * 修改日期：2015年8月18日下午4:01:49
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="crowdfundListByPage",method = RequestMethod.POST)
	public void crowdfundListByPage() throws Exception{
		JSONObject jObject=super.getRequestJsonParams();
		int page=jObject.getInt("page");
		int size=jObject.getInt("size");
		int crowdfundID=jObject.getInt("crowdfundID");
		try {
			Pagination<JSONArray> pjs=crowdfundService.findMembersByCrowdfundID(page, size, crowdfundID);
			returnSuccessJson(pjs.toString());
		} catch (Exception e) {
			returnFailJson("fail");
		}
	}
	/**方法名称: attentionuser<br>
	 * 描述：获取赞助助力的用户列表
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日上午10:08:53
	 * @param crowdfundID
	 */
	@ResponseBody
	@RequestMapping(value="attentionuser",method = RequestMethod.POST)
	public void attentionuser() throws Exception{
		JSONObject jObject=super.getRequestJsonParams();
		int page=jObject.getInt("page");
		int size=jObject.getInt("size");
		int crowdfundID=jObject.getInt("crowdfundID");
		try {
			Pagination<JSONArray> pagination = crowdfundService.findMembersByCrowdfundID(page,size,crowdfundID);
			returnSuccessJson(pagination.toString());
		} catch (Exception e) {
			returnFailJson("find fail");
		}
	}
	
	
	/**方法名称: save<br>
	 * 描述：用户发布助力,助理保存
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日上午10:10:08
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value="save",method = RequestMethod.POST)
	public void save() throws IOException, ParseException{
		JSONObject jObject=super.getRequestJsonParams();
		String crowdfundTitle = EmojiFilter.filterEmoji(jObject.getString("crowdfundTitle").trim());
		jObject.put("crowdfundTitle", crowdfundTitle);
		String crowdfundDesc = EmojiFilter.filterEmoji(jObject.getString("crowdfundDesc").trim());
		jObject.put("crowdfundDesc", crowdfundDesc);
		String crowdfundRepay = EmojiFilter.filterEmoji(jObject.getString("crowdfundRepay").trim());
		jObject.put("crowdfundRepay", crowdfundRepay);
		int crowdfundID=crowdfundService.saveCrowdfund(jObject);
		if(crowdfundID==-1){
			returnFailJson("发布失败");
		}else{
			JSONObject jsonObject = new JSONObject();  
		    jsonObject.put("crowdfundID",crowdfundID);  
			returnSuccessJson(jsonObject.toString());
		}
	}
	
	/**方法名称: delete<br>
	 * 描述：删除助力
	 * 用于删除助力信息，如果已经有人助力过，不能删除
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日上午10:12:32
	 * @param crowdfundID
	 * @throws IOException 
	 * @throws SQLException 
	 */
	@ResponseBody
	@RequestMapping(value="delete",method = RequestMethod.POST)
	public void delete() throws IOException, SQLException{
		JSONObject jObject=super.getRequestJsonParams();
		int crowdfundID=jObject.getInt("crowdfundID");
		String deleteState=crowdfundService.deleteCrowdfund(crowdfundID);
		JSONObject jsonObject = new JSONObject();  
	    jsonObject.put("msg",deleteState);  
		returnSuccessJson(jsonObject.toString());
	}
	
}
