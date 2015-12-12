package com.lagoqu;

import java.io.IOException;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.spring.test.BaseSpringTest;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.service.AppArtbaseService;
import com.lagoqu.worldplay.service.AppArtbaseTypeService;
import com.lagoqu.worldplay.service.AppCarouselService;
import com.lagoqu.worldplay.service.AppCrowdfundAttentionService;
import com.lagoqu.worldplay.service.AppCrowdfundService;
import com.lagoqu.worldplay.service.AppLabelStoreService;
import com.lagoqu.worldplay.service.AppPhotoGalleryService;

/**描述：信息查询ApiTest<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月27日上午11:36:43 <br>
 * E-mail:  <br> 
 */
public class CrowdfundInquiryApiTest extends BaseSpringTest{

	@Resource
	AppCarouselService appCarouselService;
	
	@Resource
	AppCrowdfundService appCrowdfundService;
	
	
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
	
	/**方法名称: carouselList<br>
	 * 描述：App首页列表查询
	 * 作者: 王小欢
	 * 修改日期：2015年10月29日下午1:36:42
	 * @throws IOException
	 */
	@Test
	public void crowdfundList() throws IOException{
		int page=1;
		int size=5;
		String updateTime = null;
		Pagination<JSONArray> pagination=appCrowdfundService.crowdfundList(page,size,updateTime);
		System.out.println(pagination.toString());
	}
	
	
	
	/**方法名称: crowdfundDetail<br>
	 * 描述：心愿或足迹详情
	 * 作者: 王小欢
	 * 修改日期：2015年10月29日下午1:36:42
	 * @throws IOException
	 */
	@Test
	public void crowdfundDetail() throws IOException{
		int crowdfundID=177;
		int crowdfundType=0;
		int membersID=5;
		JSONObject backJsonObject=appCrowdfundService.crowdfundDetail(crowdfundID,crowdfundType,membersID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println(backJsonObject.get("message").toString());
		}
	}
	
	
	
	/**方法名称: crowdfundMarkList<br>
	 * 描述：心愿或足迹打赏记录(包括评论)
	 * 作者: 王小欢
	 * 修改日期：2015年10月29日下午6:24:37
	 * @throws IOException
	 */
	@Test
	public void crowdfundMarkList() throws IOException{
		int page=1;
		int size=5;
		int crowdfundID=1;
		Pagination<JSONArray> pagination=appCrowdfundService.crowdfundMarkList(page, size,crowdfundID);
		System.out.println(pagination.toString());
	}
	
	
	
	/**方法名称: carouselList<br>
	 * 描述：App首页轮播图查询
	 * 作者: 王小欢
	 * 修改日期：2015年10月29日下午1:36:42
	 * @throws IOException
	 */
	@Test
	public void carouselList() throws IOException{
		int page=1;
		int size=5;
		Pagination<JSONArray> pagination=appCarouselService.carouselList(page,size);
		System.out.println(pagination.toString());
	}
	
	
	
	
	/**方法名称: CrowdfundAttentionList<br>
	 * 描述：心愿或足迹用户关注详情
	 * 作者: 王小欢
	 * 修改日期：2015年11月4日上午10:29:01
	 * @throws IOException
	 */
	@Test
	public void CrowdfundAttentionList() throws IOException{
		int crowdfundID=97;
		JSONObject backJsonObject=appCrowdfundAttentionService.CrowdfundAttentionList(crowdfundID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	/**方法名称: CrowdfundMembersList<br>
	 * 描述：个人发布的心愿或足迹
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日下午3:11:38
	 * @throws IOException
	 */
	@Test
	public void CrowdfundMembersList() throws IOException{
		int page=1;
		int size=5;
		int crowdfundType=0;
		int membersID=97;		
		Pagination<JSONArray> pagination=appCrowdfundService.CrowdfundMembersList(page,size,crowdfundType,membersID);
		System.out.println(pagination.toString());
	}
	
	
	
	
	/**方法名称: CrowdfundMembersAttentionList<br>
	 * 描述：个人关注的心愿或足迹
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日下午3:11:38
	 * @throws IOException
	 */
	@Test
	public void CrowdfundMembersAttentionList() throws IOException{
		int page=1;
		int size=5;
		int crowdfundType=0;
		int membersID=97;		
		Pagination<JSONArray> pagination=appCrowdfundService.CrowdfundMembersAttentionList(page,size,crowdfundType,membersID);
		System.out.println(pagination.toString());
	}
	
	
	
	
	/**方法名称: CrowdfundMembersMarkList<br>
	 * 描述：个人打赏的心愿或足迹
	 * 作者: 王小欢
	 * 修改日期：2015年11月10日下午3:11:38
	 * @throws IOException
	 */
	@Test
	public void CrowdfundMembersMarkList() throws IOException{
		int page=1;
		int size=5;
		int crowdfundType=0;
		int membersID=4;		
		Pagination<JSONArray> pagination=appCrowdfundService.CrowdfundMembersMarkList(page,size,crowdfundType,membersID);
		System.out.println(pagination.toString());
	}
	
	
	
	/**方法名称: PhotoGalleryList<br>
	 * 描述：图片库列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月30日下午2:51:59
	 * @throws IOException
	 */
	@Test
	public void PhotoGalleryList() throws IOException{
		int page=1;
		int size=5;
		Pagination<JSONArray> pagination=appPhotoGalleryService.PhotoGalleryList(page,size);
		System.out.println(pagination.toString());
	}
	
	
	/**方法名称: ArtbaseList<br>
	 * 描述：文字库列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月31日上午11:48:46
	 * @throws IOException
	 */
	@Test
	public void ArtbaseList() throws IOException{
		int page=1;
		int size=5;
		int artbaseType=2;
		Pagination<JSONArray> pagination=appArtbaseService.ArtbaseList(page,size,artbaseType);
		System.out.println(pagination.toString());
	}
	
	
	
	/**方法名称: ArtbaseTypeList<br>
	 * 描述：文字库分类列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月31日上午11:48:49
	 * @throws IOException
	 */
	@Test
	public void ArtbaseTypeList() throws IOException{
		JSONObject backJsonObject=appArtbaseTypeService.ArtbaseTypeList();
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	/**方法名称: LabelList<br>
	 * 描述：标签库列表
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日上午10:51:52
	 * @throws IOException
	 */
	@Test
	public void LabelList() throws IOException{
		int page=1;
		int size=5;
		Pagination<JSONArray> pagination=appLabelStoreService.LabelList(page,size);
		System.out.println(pagination.toString());
	}
	
	
	
	
	/**方法名称: otherMemberSpace<br>
	 * 描述：他的空间信息展示
	 * 作者: 王小欢
	 * 修改日期：2015年11月11日下午3:30:46
	 * @throws IOException
	 */
	@Test
	public void otherMemberSpace() throws IOException{
		int membersID=5;
		JSONObject backJsonObject=appCrowdfundService.otherMemberSpace(membersID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println(backJsonObject.get("message").toString());
		}
	}
}
