package com.lagoqu.worldplay.bms.page;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lagoqu.worldplay.bms.service.OrderInfoBmsService;
import com.lagoqu.worldplay.bms.service.WithdrawDetailsBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
/**描述：对账<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年9月16日下午4:49:02 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/bms/Reconciliation")
public class ReconciliationBmsPage extends BMSController{
	@Resource
	OrderInfoBmsService orderInfoBmsService;
	
	@Resource
	WithdrawDetailsBmsService withdrawDetailsBmsService;
	/**方法名称: proceedsList<br>
	 * 描述：对账收入
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日下午4:50:04
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("proceedsList")
	public String proceedsList() throws Exception{
		return "bms/Reconciliation/proceedsList.html";
	}
	
	
	/**方法名称: expenditureList<br>
	 * 描述：对账支出
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日下午4:50:15
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("expenditureList")
	public String expenditureList() throws Exception{
		return "bms/Reconciliation/expenditureList.html";
	}
	
	
	
	/**方法名称: detailsList<br>
	 * 描述：对账总明细
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日下午4:50:24
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detailsList")
	public String detailsList() throws Exception{
		
		return "bms/Reconciliation/detailsList.html";
	}
	
	
	
	/**方法名称: AccountsList<br>
	 * 描述：用户账户
	 * 作者: 王小欢
	 * 修改日期：2015年11月20日下午1:09:04
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("AccountsList")
	public String AccountsList() throws Exception{
		
		return "bms/Reconciliation/AccountsList.html";
	}
	
	
	/**方法名称: MembersMarkList<br>
	 * 描述：用户打赏记录
	 * 作者: 王小欢
	 * 修改日期：2015年12月10日下午5:47:22
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("MembersMarkList")
	public String MembersMarkList() throws Exception{
		return "bms/Reconciliation/MembersMarkList.html";
	}
	
	
	/**方法名称: MembersMarkDetails<br>
	 * 描述：用户打赏详情
	 * 作者: 王小欢
	 * 修改日期：2015年12月11日上午10:10:40
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("{membersID}/MembersMarkDetails")
	public String MembersMarkDetails(@PathVariable("membersID") int membersID) throws Exception{
	//	super.setRequestAttibute("membersID", membersID);
		return "bms/Reconciliation/MembersMarkDetails.html";
	}
	
}
