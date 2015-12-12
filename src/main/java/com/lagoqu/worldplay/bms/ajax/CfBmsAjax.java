package com.lagoqu.worldplay.bms.ajax;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.bms.service.CfBmsService;
import com.lagoqu.worldplay.bms.service.TrackRecordBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
import com.lagoqu.worldplay.entity.Members;
import com.lagoqu.worldplay.service.CrowdfundAttentionService;
import com.lagoqu.worldplay.service.CrowdfundMarkService;
import com.lagoqu.worldplay.service.CrowdfundService;
import com.lagoqu.worldplay.service.MembersService;
import com.lagoqu.worldplay.util.EmojiFilter;
import com.lagoqu.worldplay.util.Upload;
/**描述：后台发布助理<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年8月4日上午11:00:19 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/cf")
public class CfBmsAjax extends BMSController{

	@Resource
	CfBmsService cfBmsService;

	@Resource
	CrowdfundService crowdfundService;
	
	@Resource
	CrowdfundMarkService crowdfundMarkService;
	
	@Resource
	CrowdfundAttentionService crowdfundAttentionService;
	
	@Resource
	TrackRecordBmsService trackRecordBmsService;
	
	/**方法名称: findPageData<br>
	 * 描述：（后台发布心愿）心愿记录查询
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
		int crowdfundState=Integer.parseInt(map.get("crowdfundState")[0]);
		Pagination<JSONArray> pagination=cfBmsService.crowdfundList(page,size,membersPhone,crowdfundIsDel,crowdfundState);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	
	
	
	/**方法名称: findPageData<br>
	 * 描述：（后台发布足迹）足迹记录查询
	 * 作者: 王小欢
	 * 修改日期：2015年7月20日上午11:22:00
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="zujilist",method = RequestMethod.POST)
	public void findPageDataZuji() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		String membersPhone=map.get("membersPhone")[0];
		int crowdfundIsDel=Integer.parseInt(map.get("crowdfundIsDel")[0]);
		int crowdfundState=Integer.parseInt(map.get("crowdfundState")[0]);
		Pagination<JSONArray> pagination=cfBmsService.crowdfundListZuji(page,size,membersPhone,crowdfundIsDel,crowdfundState);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	
	
	
	/**方法名称: TrackRecordlist<br>
	 * 描述：足迹详情查询
	 * 作者: 王小欢
	 * 修改日期：2015年11月17日下午2:19:53
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="TrackRecordlist",method = RequestMethod.POST)
	public void TrackRecordlist() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		int crowdfundID=Integer.parseInt(map.get("crowdfundID")[0]);
		Pagination<JSONArray> pagination=trackRecordBmsService.TrackRecordlist(page,size,crowdfundID);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	

	
	
	/**方法名称: crowdfundMarkadd<br>
	 * 描述：保存用户心愿记录
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日下午4:53:20
	 * @param crowdfundMark
	 * @throws IOException 
	 * @throws SQLException 
	 */
	@ResponseBody
	@RequestMapping(value="crowdfundMarkadd",method = RequestMethod.POST)
	public void crowdfundMarkadd() throws IOException, SQLException{
		JSONObject jObject=super.getRequestJsonParams();
		int crowdfundID=jObject.getInt("crowdfundID");
		int crowdfundAccount=jObject.getInt("crowdfundmarkAccount");
		int crowdfundmarkID=crowdfundMarkService.saveCrowdfundMark(jObject);
		crowdfundService.updateCrowdfundTimes(crowdfundID,crowdfundAccount);
		JSONObject jsonObject = new JSONObject();  
	    jsonObject.put("crowdfundmarkID",crowdfundmarkID);  
		returnSuccessJson(jsonObject.toString());
	}
	
	
	
	
	
	
	/**方法名称: crowdfundConcernadd<br>
	 * 描述：后台虚拟用户关注
	 * 作者: 王小欢
	 * 修改日期：2015年8月12日下午2:11:04
	 * @throws IOException
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value="crowdfundConcernadd",method = RequestMethod.POST)
	public void crowdfundConcernadd() throws IOException, SQLException{
		JSONObject jObject=super.getRequestJsonParams();
		int membersID=jObject.getInt("membersID");
		int crowdfundID=jObject.getInt("crowdfundID");
		JSONObject jsa = crowdfundAttentionService.ifAttention(membersID,crowdfundID);
		if(jsa!=null){
			int caID = jsa.getInt("caID");
			boolean bl = crowdfundAttentionService.delete(caID);
			JSONObject jsonObject = new JSONObject();  
		    jsonObject.put("state",false);  
			returnSuccessJson(jsonObject.toString());
		}else{
			int caID=crowdfundAttentionService.insertAttention(membersID,crowdfundID);
			JSONObject jsonObject = new JSONObject();  
		    jsonObject.put("caID",caID);    
		    jsonObject.put("state",true);  
			returnSuccessJson(jsonObject.toString());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	/**方法名称: upload<br>
	 * 描述：上传助理图片
	 * 作者: 王小欢
	 * 修改日期：2015年8月5日下午2:52:09
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public void upload() throws IOException {
		// 图片上传
		Upload up = new Upload();
		// 获得图片名称list
		List imageList = up.uploadrelative2(request,"zhuli");
		
		// 保存用户上传照片
		returnSuccessJson(imageList.get(0).toString());
		
	}
}
