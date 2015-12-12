package com.lagoqu.worldplay.api.app;

import java.io.IOException;

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
import com.lagoqu.worldplay.service.AppArtbaseService;
import com.lagoqu.worldplay.service.AppArtbaseTypeService;
import com.lagoqu.worldplay.service.AppCarouselService;
import com.lagoqu.worldplay.service.AppCrowdfundAttentionService;
import com.lagoqu.worldplay.service.AppCrowdfundService;
import com.lagoqu.worldplay.service.AppFunCarouselService;
import com.lagoqu.worldplay.service.AppLabelStoreService;
import com.lagoqu.worldplay.service.AppNewsService;
import com.lagoqu.worldplay.service.AppPhotoGalleryService;

/**描述：信息查询Api<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月27日上午11:36:43 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/CrowdfundInquiryApi")
public class CrowdfundInquiryApi extends APIController{

	@Resource
	AppCrowdfundService appCrowdfundService;
	
	@Resource
	AppCarouselService appCarouselService;
	
	@Resource
	AppCrowdfundAttentionService appCrowdfundAttentionService;
	
	
	@Resource
	AppPhotoGalleryService appPhotoGalleryService;
	
	@Resource
	AppArtbaseService appArtbaseService;
	
	@Resource
	AppArtbaseTypeService appArtbaseTypeService;
	
	
	@Resource
	AppLabelStoreService appLabelStoreService;
	
	@Resource
	AppFunCarouselService appFunCarouselService;
	
	@Resource
	AppNewsService appNewsService;
	
	/**方法名称: carouselList<br>
	 * 描述：App首页列表查询
	 * 作者: 王小欢
	 * 修改日期：2015年10月29日下午1:36:42
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="crowdfundList",method = RequestMethod.POST)
	public void crowdfundList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int page=receivejJsonObject.getInt("page");
		int size=receivejJsonObject.getInt("size");
		String updateTime = null;
		if(receivejJsonObject.containsKey("updateTime")){
			updateTime = receivejJsonObject.getString("updateTime");
		}
		Pagination<JSONArray> pagination=appCrowdfundService.crowdfundList(page,size,updateTime);
		returnSuccessJson(pagination.toString());
	}
	
	
	
	
	/**方法名称: crowdfundRdList<br>
	 * 描述：App推荐列表查询
	 * 作者: 王小欢
	 * 修改日期：2015年12月7日下午5:27:19
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="crowdfundRdList",method = RequestMethod.POST)
	public void crowdfundRdList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int page=receivejJsonObject.getInt("page");
		int size=receivejJsonObject.getInt("size");
		int crowdfundType=receivejJsonObject.getInt("crowdfundType");
		String updateTime = null;
		if(receivejJsonObject.containsKey("updateTime")){
			updateTime = receivejJsonObject.getString("updateTime");
		}
		Pagination<JSONArray> pagination=appCrowdfundService.crowdfundRdList(page,size,updateTime,crowdfundType);
		returnSuccessJson(pagination.toString());
	}
	
	
	
	
	
	
	
	
	/**方法名称: crowdfundDetail<br>
	 * 描述：心愿或足迹详情
	 * 作者: 王小欢
	 * 修改日期：2015年10月29日下午6:24:37
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="crowdfundDetail",method = RequestMethod.POST)
	public void crowdfundDetail() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int crowdfundID=receivejJsonObject.getInt("crowdfundID");
		int membersID=receivejJsonObject.getInt("membersID");
		int crowdfundType=receivejJsonObject.getInt("crowdfundType");
		JSONObject backJsonObject=appCrowdfundService.crowdfundDetail(crowdfundID,crowdfundType,membersID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	
	
	/**方法名称: crowdfundMarkList<br>
	 * 描述：心愿或足迹打赏记录(包括评论)
	 * 作者: 王小欢
	 * 修改日期：2015年10月29日下午6:24:37
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="crowdfundMarkList",method = RequestMethod.POST)
	public void crowdfundMarkList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int page=receivejJsonObject.getInt("page");
		int size=receivejJsonObject.getInt("size");
		int crowdfundID=receivejJsonObject.getInt("crowdfundID");
		if(size==5){
			size = 1000;
		}
		Pagination<JSONArray> pagination=appCrowdfundService.crowdfundMarkList(page, size,crowdfundID);
		returnSuccessJson(pagination.toString());
	}
	
	
	
	
	
	
	
	
	/**方法名称: carouselList<br>
	 * 描述：App首页轮播图查询
	 * 作者: 王小欢
	 * 修改日期：2015年10月29日下午1:36:42
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="carouselList",method = RequestMethod.POST)
	public void carouselList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int page=receivejJsonObject.getInt("page");
		int size=receivejJsonObject.getInt("size");
		Pagination<JSONArray> pagination=appCarouselService.carouselList(page,size);
		returnSuccessJson(pagination.toString());
	}
	
	
	
	
	
	
	/**方法名称: CrowdfundAttentionList<br>
	 * 描述：心愿或足迹用户关注详情
	 * 作者: 王小欢
	 * 修改日期：2015年11月4日上午10:29:01
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundAttentionList",method = RequestMethod.POST)
	public void CrowdfundAttentionList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int crowdfundID=receivejJsonObject.getInt("crowdfundID");
		JSONObject backJsonObject=appCrowdfundAttentionService.CrowdfundAttentionList(crowdfundID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	/**方法名称: CrowdfundMembersList<br>
	 * 描述：个人发布的心愿或足迹
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日下午3:11:38
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundMembersList",method = RequestMethod.POST)
	public void CrowdfundMembersList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int page=receivejJsonObject.getInt("page");
		int size=receivejJsonObject.getInt("size");
		int crowdfundType=receivejJsonObject.getInt("crowdfundType");
		int membersID=receivejJsonObject.getInt("membersID");		
		Pagination<JSONArray> pagination=appCrowdfundService.CrowdfundMembersList(page,size,crowdfundType,membersID);
		returnSuccessJson(pagination.toString());
	}
	
	
	
	
	
	/**方法名称: CrowdfundMembersAttentionList<br>
	 * 描述：个人关注的心愿或足迹
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日下午3:11:38
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundMembersAttentionList",method = RequestMethod.POST)
	public void CrowdfundMembersAttentionList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int page=receivejJsonObject.getInt("page");
		int size=receivejJsonObject.getInt("size");
		int crowdfundType=receivejJsonObject.getInt("crowdfundType");
		int membersID=receivejJsonObject.getInt("membersID");		
		Pagination<JSONArray> pagination=appCrowdfundService.CrowdfundMembersAttentionList(page,size,crowdfundType,membersID);
		returnSuccessJson(pagination.toString());
	}
	
	
	
	
	
	/**方法名称: CrowdfundMembersMarkList<br>
	 * 描述：个人打赏的心愿或足迹
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日下午3:11:38
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="CrowdfundMembersMarkList",method = RequestMethod.POST)
	public void CrowdfundMembersMarkList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int page=receivejJsonObject.getInt("page");
		int size=receivejJsonObject.getInt("size");
		int crowdfundType=receivejJsonObject.getInt("crowdfundType");
		int membersID=receivejJsonObject.getInt("membersID");		
		Pagination<JSONArray> pagination=appCrowdfundService.CrowdfundMembersMarkList(page,size,crowdfundType,membersID);
		returnSuccessJson(pagination.toString());
	}
	
	
	
	
	
	/**方法名称: PhotoGalleryList<br>
	 * 描述：图片库列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月30日下午2:51:59
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="PhotoGalleryList",method = RequestMethod.POST)
	public void PhotoGalleryList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int page=receivejJsonObject.getInt("page");
		int size=receivejJsonObject.getInt("size");
		Pagination<JSONArray> pagination=appPhotoGalleryService.PhotoGalleryList(page,size);
		returnSuccessJson(pagination.toString());
	}
	
	
	
	/**方法名称: ArtbaseList<br>
	 * 描述：文字库列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月31日下午2:51:59
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="ArtbaseList",method = RequestMethod.POST)
	public void ArtbaseList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int page=receivejJsonObject.getInt("page");
		int size=receivejJsonObject.getInt("size");
		int artbaseType=receivejJsonObject.getInt("artbaseType");
		Pagination<JSONArray> pagination=appArtbaseService.ArtbaseList(page,size,artbaseType);
		returnSuccessJson(pagination.toString());
	}
	
	
	
	
	/**方法名称: ArtbaseTypeList<br>
	 * 描述：文字库分类列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月31日下午2:51:59
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="ArtbaseTypeList",method = RequestMethod.POST)
	public void ArtbaseTypeList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int artbaseType=receivejJsonObject.getInt("artbaseType");
		JSONObject backJsonObject=appArtbaseTypeService.ArtbaseTypeList();
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	
	/**方法名称: LabelList<br>
	 * 描述：标签库列表
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日上午10:51:52
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="LabelList",method = RequestMethod.POST)
	public void LabelList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int page=receivejJsonObject.getInt("page");
		int size=receivejJsonObject.getInt("size");
		Pagination<JSONArray> pagination=appLabelStoreService.LabelList(page,size);
		returnSuccessJson(pagination.toString());
	}
	
	
	
	/**方法名称: otherMemberSpace<br>
	 * 描述：他的空间信息展示
	 * 作者: 王小欢
	 * 修改日期：2015年11月11日下午3:30:46
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="otherMemberSpace",method = RequestMethod.POST)
	public void otherMemberSpace() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int membersID=receivejJsonObject.getInt("membersID");
		JSONObject backJsonObject=appCrowdfundService.otherMemberSpace(membersID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	/**方法名称: funCarouselList<br>
	 * 描述：哪好玩轮播图查询
	 * 作者: 王小欢
	 * 修改日期：2015年10月29日下午1:36:42
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="funCarouselList",method = RequestMethod.POST)
	public void funCarouselList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int page=receivejJsonObject.getInt("page");
		int size=receivejJsonObject.getInt("size");
		Pagination<JSONArray> pagination=appFunCarouselService.funCarouselList(page,size);
		returnSuccessJson(pagination.toString());
	}
	
	
	
	
	
	/**方法名称: newsList<br>
	 * 描述：哪好玩列表
	 * 作者: 王小欢
	 * 修改日期：2015年12月8日下午6:24:53
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="newsList",method = RequestMethod.POST)
	public void newsList() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int page=receivejJsonObject.getInt("page");
		int size=receivejJsonObject.getInt("size");
		Pagination<JSONArray> pagination=appNewsService.newsList(page,size);
		returnSuccessJson(pagination.toString());
	}
	
	
	
	/**方法名称: newsDetail<br>
	 * 描述：查看哪好玩详情
	 * 作者: 王小欢
	 * 修改日期：2015年12月9日上午9:55:19
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="newsDetail",method = RequestMethod.POST)
	public void newsDetail() throws IOException{
		JSONObject receivejJsonObject = super.getRequestJsonParams();
		int newsId=receivejJsonObject.getInt("newsId");
		JSONObject backJsonObject=appNewsService.newsDetail(newsId);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			returnSuccessJson(backJsonObject.get("data").toString());
		}else{
			returnFailJson(backJsonObject.get("message").toString());
		}
	}
}
