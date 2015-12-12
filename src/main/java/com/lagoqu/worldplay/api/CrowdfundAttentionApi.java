package com.lagoqu.worldplay.api;

import java.io.IOException;
import java.sql.SQLException;

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
import com.lagoqu.worldplay.entity.Members;
import com.lagoqu.worldplay.service.CrowdfundAttentionService;
import com.lagoqu.worldplay.service.MembersService;

/**描述：关注助力<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月5日上午10:29:36 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/helpfav")
public class CrowdfundAttentionApi extends APIController{

	
	@Resource
	CrowdfundAttentionService crowdfundAttentionService;
	@Resource
	MembersService membersService;
	
	
	/**方法名称: attentionlist<br>
	 * 描述：用户个人关注助力列表
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日上午10:32:57
	 * @param page
	 * @param size
	 * @param memberID
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="attentionlist",method = RequestMethod.POST)
	public void attentionlist() throws IOException{
		JSONObject jObject=super.getRequestJsonParams();
		int page=jObject.getInt("page");
		int size=jObject.getInt("size");
		int memberID=jObject.getInt("membersID");
		Pagination<JSONArray> pagination=crowdfundAttentionService.listCrowdfundAttention(page,size,memberID);
		returnSuccessJson(pagination.toString());
	}
	
	/**方法名称: attentionadd<br>
	 * 描述：关注
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日上午10:36:14
	 * @param membersID
	 * @param crowdfundID
	 * @throws IOException 
	 * @throws SQLException 
	 */
	@ResponseBody
	@RequestMapping(value="attentionadd",method = RequestMethod.POST)
	public void attentionadd() throws IOException, SQLException{
		JSONObject jObject=super.getRequestJsonParams();
		if(jObject.get("membersID") == null){
			returnSuccessJson("请先登录");
			return;
		}
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
			JSONObject ms = membersService.findMemberById(membersID);
			JSONObject jsonObject = new JSONObject();  
		    jsonObject.put("caID",caID);  
		    jsonObject.put("members",ms);  
		    jsonObject.put("state",true);  
			returnSuccessJson(jsonObject.toString());
		}
	}
	
	
	/**方法名称: delete<br>
	 * 描述：删除收藏的助力信息
	 * 作者: 王小欢
	 * 修改日期：2015年6月5日上午10:36:12
	 * @param caID
	 * @throws IOException 
	 */
	@ResponseBody	
	@RequestMapping(value="delete",method = RequestMethod.POST)
	public void delete() throws IOException{
		JSONObject jObject=super.getRequestJsonParams();
		int caID=jObject.getInt("caID");
		boolean bl = crowdfundAttentionService.delete(caID);
		JSONObject jsonObject = new JSONObject();  
	    jsonObject.put("bl",bl);  
		returnSuccessJson(jsonObject.toString());
	}
}
