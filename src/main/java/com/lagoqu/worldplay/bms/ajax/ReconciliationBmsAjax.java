package com.lagoqu.worldplay.bms.ajax;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.bms.service.AccountDetailsBmsService;
import com.lagoqu.worldplay.bms.service.AccountsBmsService;
import com.lagoqu.worldplay.bms.service.OrderInfoBmsService;
import com.lagoqu.worldplay.bms.service.WithdrawDetailsBmsService;
import com.lagoqu.worldplay.common.controller.BMSController;
/**描述：对账<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年9月16日下午4:48:46 <br>
 * E-mail:  <br> 
 */
@Controller
@Scope("prototype")
@RequestMapping("/ajax/bms/Reconciliation")
public class ReconciliationBmsAjax extends BMSController{
	@Resource
	OrderInfoBmsService orderInfoBmsService;
	
	@Resource
	WithdrawDetailsBmsService withdrawDetailsBmsService;
	
	@Resource
	AccountDetailsBmsService accountDetailsBmsService;
	
	@Resource
	AccountsBmsService accountsBmsService;
	
	/**方法名称: proceedsList<br>
	 * 描述：对账收入
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日下午4:58:41
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="proceedsList",method = RequestMethod.POST)
	public void proceedsList() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		String membersPhone=map.get("membersPhone")[0];
		String membersNickName=map.get("membersNickName")[0];
		String orderPayedTime=map.get("orderPayedTime")[0];
		int orderPayType=Integer.parseInt(map.get("orderPayType")[0]);
		Pagination<JSONArray> pagination=orderInfoBmsService.proceedsList(page,size,membersPhone,membersNickName,orderPayedTime,orderPayType);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	/**方法名称: proceedsSum<br>
	 * 描述：对账收入总计
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日下午4:58:41
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="proceedsSum",method = RequestMethod.POST)
	public void proceedsSum() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		String membersPhone=map.get("membersPhone")[0];
		String membersNickName=map.get("membersNickName")[0];
		String orderPayedTime=map.get("orderPayedTime")[0];
		int orderPayType=Integer.parseInt(map.get("orderPayType")[0]);
		JSONArray sumPrice=orderInfoBmsService.proceedsSum(membersPhone,membersNickName,orderPayedTime,orderPayType);
		returnSuccessJson(sumPrice.get(0).toString());
	}
	
	
	/**方法名称: proceedsListExcelPort<br>
	 * 描述：对账收入Excel导出
	 * 作者: 王小欢
	 * 修改日期：2015年9月18日上午10:44:01
	 * @throws Exception
	 */
	@RequestMapping(value="proceedsListExcelPort",method = RequestMethod.GET)
	public String proceedsListExcelPort(@RequestParam(value = "membersPhone", required = true) String membersPhone,
			@RequestParam(value = "membersNickName", required = true) String membersNickName,
			@RequestParam(value = "orderPayedTime", required = true) String orderPayedTime,
			@RequestParam(value = "orderPayType", required = true) int orderPayType) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		orderInfoBmsService.proceedsListExcelPort(membersPhone,membersNickName,orderPayedTime,orderPayType).write(os);
		byte[] content = os.toByteArray();
	    InputStream is = new ByteArrayInputStream(content);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");//设置日期格式
	    String ExcelPortPath="玩赚世界导出对账收入Excel"+df.format(new Date())+".xls";
	    // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((ExcelPortPath).getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
		return null;
	}
	
	
	
	
	
	
	
	/**方法名称: expenditureList<br>
	 * 描述：对账支出
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日下午4:58:52
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="expenditureList",method = RequestMethod.POST)
	public void expenditureList() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		String membersPhone=map.get("membersPhone")[0];
		String membersNickName=map.get("membersNickName")[0];
		String orderPayedTime=map.get("orderPayedTime")[0];
		Pagination<JSONArray> pagination=withdrawDetailsBmsService.expenditureList(page,size,membersPhone,membersNickName,orderPayedTime);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	
	
	/**方法名称: expenditureSum<br>
	 * 描述：对账支出
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日下午4:58:52
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="expenditureSum",method = RequestMethod.POST)
	public void expenditureSum() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		String membersPhone=map.get("membersPhone")[0];
		String membersNickName=map.get("membersNickName")[0];
		String orderPayedTime=map.get("orderPayedTime")[0];
		JSONArray sumAccount=withdrawDetailsBmsService.expenditureSum(membersPhone,membersNickName,orderPayedTime);
		super.setRequestAttibute("sumAccount", sumAccount.get(0));
		returnSuccessJson(sumAccount.get(0).toString());
	}
	
	
	
	
	/**方法名称: expenditureListExcelPort<br>
	 * 描述：对账支出Excel导出
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日下午4:58:52
	 * @throws Exception
	 */
	@RequestMapping(value="expenditureListExcelPort",method = RequestMethod.GET)
	public String expenditureListExcelPort(@RequestParam(value = "membersPhone", required = true) String membersPhone,
			@RequestParam(value = "membersNickName", required = true) String membersNickName,
			@RequestParam(value = "orderPayedTime", required = true) String orderPayedTime) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		withdrawDetailsBmsService.expenditureListExcelPort(membersPhone,membersNickName,orderPayedTime).write(os);;
		byte[] content = os.toByteArray();
	    InputStream is = new ByteArrayInputStream(content);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");//设置日期格式
	    String ExcelPortPath="玩赚世界导出对账支出Excel"+df.format(new Date())+".xls";
	    // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((ExcelPortPath).getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
		return null;
	}
	
	
	
	
	
	
	
	
	/**方法名称: detailsList<br>
	 * 描述：对账总明细
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日下午4:59:01
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="detailsList",method = RequestMethod.POST)
	public void detailsList() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		String orderPayedTime=map.get("orderPayedTime")[0];
		Pagination<JSONArray> pagination=accountDetailsBmsService.detailsList(page,size,orderPayedTime);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	
	/**方法名称: detailsListExcelPort<br>
	 * 描述：对账总明细Excel导出
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日下午4:59:01
	 * @throws Exception
	 */
	@RequestMapping(value="detailsListExcelPort",method = RequestMethod.GET)
	public String detailsListExcelPort(@RequestParam(value = "orderPayedTime", required = true) String orderPayedTime) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		accountDetailsBmsService.detailsListExcelPort(orderPayedTime).write(os);;
		byte[] content = os.toByteArray();
	    InputStream is = new ByteArrayInputStream(content);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");//设置日期格式
	    String ExcelPortPath="玩赚世界导出对账总明细Excel"+df.format(new Date())+".xls";
	    // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((ExcelPortPath).getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
		return null;
	}
	
	
	
	
	/**方法名称: AccountsList<br>
	 * 描述：用户账户
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日下午4:58:41
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="AccountsList",method = RequestMethod.POST)
	public void AccountsList() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		String membersPhone=map.get("membersPhone")[0];
		String membersNickName=map.get("membersNickName")[0];
		Pagination<JSONArray> pagination=accountsBmsService.AccountsList(page,size,membersPhone,membersNickName);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	/**方法名称: AccountsSum<br>
	 * 描述：用户账户总计
	 * 作者: 王小欢
	 * 修改日期：2015年9月16日下午4:58:41
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="AccountsSum",method = RequestMethod.POST)
	public void AccountsSum() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		String membersPhone=map.get("membersPhone")[0];
		String membersNickName=map.get("membersNickName")[0];
		JSONArray sumAccounts=accountsBmsService.AccountsSum(membersPhone,membersNickName);
		returnSuccessJson(sumAccounts.get(0).toString());
	}
	
	
	
	
	
	/**方法名称: MembersMarkList<br>
	 * 描述：用户打赏记录
	 * 作者: 王小欢
	 * 修改日期：2015年12月10日下午6:09:07
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="MembersMarkList",method = RequestMethod.POST)
	public void MembersMarkList() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		String membersPhone=map.get("membersPhone")[0];
		String membersNickName=map.get("membersNickName")[0];
		Pagination<JSONArray> pagination=orderInfoBmsService.MembersMarkList(page,size,membersPhone,membersNickName);
		returnJSONEasyUISuccess(pagination);
	}
	
	
	
	/**方法名称: MembersMarkDetails<br>
	 * 描述：用户打赏详情
	 * 作者: 王小欢
	 * 修改日期：2015年12月11日上午10:35:08
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="MembersMarkDetails",method = RequestMethod.POST)
	public void MembersMarkDetails() throws Exception {
		Map<String,String[]> map =super.getRequestParamsMap();
		int page=Integer.parseInt(map.get("page")[0]);
		int size=Integer.parseInt(map.get("rows")[0]);
		int membersID=Integer.parseInt(map.get("membersID")[0]);
		Pagination<JSONArray> pagination=orderInfoBmsService.MembersMarkDetails(page,size,membersID);
		returnJSONEasyUISuccess(pagination);
	}
}
