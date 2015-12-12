package com.lagoqu;

import java.io.IOException;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.lagoqu.common.spring.test.BaseSpringTest;
import com.lagoqu.worldplay.entity.Crowdfund;
import com.lagoqu.worldplay.entity.CrowdfundMark;
import com.lagoqu.worldplay.entity.CrowdfundReply;
import com.lagoqu.worldplay.entity.OrderInfo;
import com.lagoqu.worldplay.entity.ReportRecord;
import com.lagoqu.worldplay.service.AppCrowdfundAttentionService;
import com.lagoqu.worldplay.service.AppCrowdfundReplyService;
import com.lagoqu.worldplay.service.AppOrderInfoService;
import com.lagoqu.worldplay.service.AppReportRecordService;
import com.lagoqu.worldplay.service.CrowdfundMarkService;
/**描述：信息动作Api
 * 修改，删除，打赏，评论，关注，举报<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月27日上午11:33:05 <br>
 * E-mail:  <br> 
 */
public class CrowdfundMovementApiTest extends BaseSpringTest{

	@Resource
	AppCrowdfundAttentionService appCrowdfundAttentionService;
	
	@Resource
	AppCrowdfundReplyService appCrowdfundReplyService;
	
	@Resource
	AppReportRecordService appReportRecordService;
	
	@Resource
	AppOrderInfoService appOrderInfoService;
	
	@Resource
	CrowdfundMarkService crowdfundMarkService;
	
	/**方法名称: CrowdfundReleaseComment<br>
	 * 描述：心愿或足迹评论
	 * 作者: 王小欢
	 * 修改日期：2015年11月3日上午10:17:21
	 * @throws IOException
	 */
	@Test
	public void CrowdfundReleaseComment() throws IOException{
			CrowdfundReply crowdfundReply = new CrowdfundReply();
			for (int i = 0; i < 2000; i++) {
				crowdfundReply.setMembersID(1);
				crowdfundReply.setReplyMembersID(2);
				crowdfundReply.setCrowdfundID(i);
				crowdfundReply.setCrowdfundMarkID(i);
				crowdfundReply.setCrowdfundReply("请继续任性，土豪哥");
				JSONObject backJsonObject=appCrowdfundReplyService.CrowdfundReleaseComment(crowdfundReply);
				boolean state=backJsonObject.getBoolean("state");
				if(state==true){
					System.out.println(backJsonObject.get("data").toString());
				}else{
					System.out.println(backJsonObject.get("message").toString());
				}	
			}
			
	}
	
	
	
	
	/**方法名称: CrowdfundReleaseReward<br>
	 * 描述：心愿或足迹打赏创建订单
	 * 作者: 王小欢
	 * 修改日期：2015年11月4日下午1:45:53
	 * @throws IOException
	 */
	@Test
	public void CrowdfundReleaseReward() throws IOException{
		OrderInfo orderInfo=new OrderInfo();
		orderInfo.setMembersID(1);
		orderInfo.setCrowdfundID(1);
		orderInfo.setOrderName("xxx");
		orderInfo.setOrderPrice(333);
		orderInfo.setOrderNum(333);
		orderInfo.setOrderMemo("xxxyyyyy");
		orderInfo.setOrderSource(1);
		JSONObject backJsonObject=appOrderInfoService.CrowdfundReleaseReward(orderInfo);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println(backJsonObject.get("message").toString());
		}
	}
	
	
	/**方法名称: CrowdfundReleaseCommentDel<br>
	 * 描述：打赏评论删除
	 * 作者: 王小欢
	 * 修改日期：2015年11月3日上午11:06:48
	 * @throws IOException
	 */
	@Test
	public void CrowdfundReleaseCommentDel() throws IOException{
		int crowdfundReplyID=2;
		JSONObject backJsonObject=appCrowdfundReplyService.CrowdfundReleaseCommentDel(crowdfundReplyID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println(backJsonObject.get("message").toString());
		}
	}
	
	/**方法名称: CrowdfundReleaseAttention<br>
	 * 描述：心愿或足迹关注
	 * 作者: 王小欢
	 * 修改日期：2015年11月2日下午3:08:04
	 * @throws IOException
	 */
	@Test
	public void CrowdfundReleaseAttention() throws IOException{
		int crowdfundID=1;
		int membersID=5;
		JSONObject backJsonObject=appCrowdfundAttentionService.CrowdfundReleaseAttention(crowdfundID,membersID);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println(backJsonObject.get("message").toString());
		}
	}
	
	
	/**方法名称: CrowdfundReleaseReport<br>
	 * 描述：心愿或足迹举报
	 * 作者: 王小欢
	 * 修改日期：2015年11月3日下午7:57:23
	 * @throws IOException
	 */
	@Test
	public void CrowdfundReleaseReport() throws IOException{
		ReportRecord reportRecord = new ReportRecord();
		reportRecord.setReportRecordMumberID(1);
		reportRecord.setReportRecordName("xxxx");
		reportRecord.setReportRecordPhone("13488888888");
		reportRecord.setCrowdfundID(1);
		reportRecord.setMembersID(2);
		reportRecord.setMembersPhone("13466666666");
		reportRecord.setReportContent("xxxxxxxxyyyyyyyyyyy");
		reportRecord.setReportImage("http://www.baidu.com/image.jpg");
		JSONObject backJsonObject=appReportRecordService.CrowdfundReleaseReport(reportRecord);
		boolean state=backJsonObject.getBoolean("state");
		if(state==true){
			System.out.println(backJsonObject.get("data").toString());
		}else{
			System.out.println(backJsonObject.get("message").toString());
		}
	}
	
	@Test
	public void a(){
		CrowdfundMark crowdfundMark = new CrowdfundMark();
		for (int i = 170; i < 1000; i++) {
			crowdfundMark.setOrderID(0001);
			crowdfundMark.setCrowdfundID(i);
			crowdfundMark.setCrowdfundmarkAccount(9999999);
			crowdfundMark.setCrowdfundmarkMessage("我是土豪我有钱我任性");
			crowdfundMark.setMembersID(2);
			crowdfundMarkService.insert(crowdfundMark);
		}
		
	}
}
