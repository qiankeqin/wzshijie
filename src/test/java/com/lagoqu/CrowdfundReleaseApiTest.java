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
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.service.AppCrowdfundService;

/**描述：信息发布Api
 * 发布心愿，足迹<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月30日上午11:28:52 <br>
 * E-mail:  <br> 
 */
public class CrowdfundReleaseApiTest extends BaseSpringTest{

	
	@Resource
	AppCrowdfundService appCrowdfundService;
	
	
	/**方法名称: CrowdfundReleaseWish<br>
	 * 描述：心愿发布
	 * 作者: 王小欢
	 * 修改日期：2015年10月30日下午2:11:10
	 * @throws IOException
	 */
	@Test
	public void CrowdfundReleaseWish() throws IOException{
		int membersID=1;
		String crowdfundDesc="xxxxxxx";
		String crowdfundPath="bbbbbbbbbbbbb";
		String crowdfundPic="ccccccccccccccccc";
		int crowdfundDays=5;
		JSONObject backJsonObject=appCrowdfundService.CrowdfundReleaseWish(membersID,crowdfundDesc,crowdfundPath,crowdfundPic,crowdfundDays);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	/**方法名称: CrowdfundWishLabel<br>
	 * 描述：心愿添加标签
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日上午10:27:23
	 * @throws IOException
	 */
	@Test
	public void CrowdfundWishLabel() throws IOException{
		int crowdfundID=1;
		String crowdfundLabel="屌丝，光棍，单身狗";
		JSONObject backJsonObject=appCrowdfundService.CrowdfundWishLabel(crowdfundID,crowdfundLabel);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println(backJsonObject.get("message").toString());
		}
	}
	
	
	
	
	/**方法名称: CrowdfundReleaseSpoor<br>
	 * 描述：足迹发布
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日下午2:00:06
	 * @throws IOException
	 */
	@Test
	public void CrowdfundReleaseSpoor() throws IOException{
		int membersID=1;
		String crowdfundTitle="单身狗的双十一";
		String crowdfundDesc="单身狗的双十一";
		String crowdfundPath="http://image.baidu.com/detail/newindex?col=%E5%AE%A0%E7%89%A9&tag=%E7%8B%97%E7%8B%97&pn=2&pid=9551996128&aid=&user_id=664577379&setid=-1&sort=0&newsPn=&star=&fr=&from=1";
		String detailsString="[{trackRecordTitle:'单身狗的第一天',trackRecordImage:'www.baidu.com',trackRecordLocation:'北京市国家广告园玩赚世界大本营',trackRecordCreateTime:'2015-11-11 11:11:11'},{trackRecordTitle:'单身狗的第二天',trackRecordImage:'www.baidu.com',trackRecordLocation:'北京市国家广告园玩赚世界大本营',trackRecordCreateTime:'2015-11-11 11:11:11'}]";
		JSONArray detail = JSONArray.fromObject(detailsString);
		JSONObject backJsonObject=appCrowdfundService.CrowdfundReleaseSpoor(membersID,crowdfundTitle,crowdfundDesc,crowdfundPath,detail);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println(backJsonObject.get("message").toString());
		}
	}
	
}
