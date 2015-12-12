package com.lagoqu.worldplay.bms.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.WithdrawDetails;

/**描述：提现记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年7月19日下午4:04:57 <br>
 * E-mail:  <br> 
 */
@Service
public class WithdrawDetailsBmsService extends BaseDao<WithdrawDetails>{

	
	/**方法名称: wDetailsList<br>
	 * 描述：未处理提现记录列表
	 * 作者: 王小欢
	 * 修改日期：2015年7月19日下午5:03:46
	 * @param page
	 * @param size
	 * @param membersPhone
	 * @return
	 */
	public Pagination<JSONArray> wDetailsList(int page,int size,String membersPhone){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select wd.wDetailsID,wd.createTime,wd.wDetailsAccount,wd.wDetailsStatus,ms.membersID,ms.membersPhone,ms.membersNickName,ms.identifyState from WithdrawDetails wd inner join Members ms on wd.membersID=ms.membersID where wd.wDetailsStatus=1";
		String countSql="select count(wd.wDetailsID) from WithdrawDetails wd inner join Members ms on wd.membersID=ms.membersID where wd.wDetailsStatus=1";
		if(membersPhone!=null && membersPhone!=""){
			sql+=" and ms.membersPhone='"+membersPhone+"'";
			countSql+=" and ms.membersPhone='"+membersPhone+"'";
		}
		sql+= " order by wd.createTime desc ";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	/**方法名称: wdetailsSuc<br>
	 * 描述：提现记录审核通过
	 * 作者: 王小欢
	 * 修改日期：2015年7月7日下午1:25:47
	 * @param wDetailsID
	 */
	public boolean wdetailsSuc(int wDetailsID,String operationName){
		Timestamp tp = new Timestamp(System.currentTimeMillis());
		String sql = "update WithdrawDetails set wDetailsStatus=2,updateTime='"+tp+"',operationName='"+operationName+"' where wDetailsID="+wDetailsID;
		boolean state=super.execSql(sql);
		return state;
	}
	
	
	/**方法名称: findByIdWDetails<br>
	 * 描述：个人提现信息
	 * 作者: 王小欢
	 * 修改日期：2015年7月19日下午7:03:58
	 * @param membersID
	 * @return
	 * @throws SQLException
	 */
	public JSONObject findByIdWDetails(int wDetailsID) throws SQLException{
		String sql="select wDetailsID,membersID,wDetailsAccount,wDetailsUserName,wDetailsName from  WithdrawDetails where wDetailsID="+wDetailsID;
		JSONObject js = super.findJson(sql, null);
		return js;
	}
	
	
	
	/**方法名称: wDetailsListSuccess<br>
	 * 描述：已审核通过提现记录列表
	 * 作者: 王小欢
	 * 修改日期：2015年7月19日下午5:04:44
	 * @param page
	 * @param size
	 * @param membersPhone
	 * @return
	 */
	public Pagination<JSONArray> wDetailsListSuccess(int page,int size,String membersPhone){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select wd.wDetailsID,wd.createTime,wd.wDetailsAccount,wd.wDetailsName,wd.wDetailsUserName,wd.wDetailsStatus,wd.updateTime,wd.operationName,ms.membersID,ms.membersPhone from WithdrawDetails wd inner join Members ms on wd.membersID=ms.membersID where wd.wDetailsStatus=2";
		String countSql="select count(wd.wDetailsID) from WithdrawDetails wd inner join Members ms on wd.membersID=ms.membersID where wd.wDetailsStatus=2";
		if(membersPhone!=null && membersPhone!=""){
			sql+=" and ms.membersPhone='"+membersPhone+"'";
			countSql+=" and ms.membersPhone='"+membersPhone+"'";
		}
		sql+= " order by wd.updateTime asc ";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	
	
	/**方法名称: expenditureList<br>
	 * 描述：对账支出
	 * 作者: 王小欢
	 * 修改日期：2015年9月17日下午1:45:46
	 * @param page
	 * @param size
	 * @param membersPhone
	 * @param membersNickName
	 * @param orderPayedTime
	 * @return
	 */
	public Pagination<JSONArray> expenditureList(int page,int size,String membersPhone,String membersNickName,String orderPayedTime){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select wd.wDetailsAccount,wd.wDetailsUserName,wd.wDetailsName,wd.createTime,wd.wDetailsType,ms.membersPhone,ms.membersNickName from WithdrawDetails wd inner join Members ms on wd.membersID =ms.membersID where 1=1";
		String countSql="select count(wd.wDetailsID) from WithdrawDetails wd inner join Members ms on wd.membersID =ms.membersID where 1=1";
		if(membersPhone!=null && membersPhone!=""){
			sql+=" and ms.membersPhone='"+membersPhone+"'";
			countSql+=" and ms.membersPhone='"+membersPhone+"'";
		}
		if(membersNickName!=null && membersNickName!=""){
			sql+=" and ms.membersNickName='"+membersNickName+"'";
			countSql+=" and ms.membersNickName='"+membersNickName+"'";
		}
		if(orderPayedTime!=null && orderPayedTime!=""){
			String time[]=orderPayedTime.split(",");
			Timestamp timeStart = new Timestamp(System.currentTimeMillis());
			Timestamp timeEnd = new Timestamp(System.currentTimeMillis());
			timeStart = Timestamp.valueOf(time[0]);
			timeEnd = Timestamp.valueOf(time[1]);
			sql+=" and wd.createTime>='"+timeStart+"'";
			sql+=" and wd.createTime<='"+timeEnd+"'";
			countSql+=" and wd.createTime>='"+timeStart+"'";
			countSql+=" and wd.createTime<='"+timeEnd+"'";
		}
		sql+= " order by wd.createTime desc ";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	
	public JSONArray expenditureSum(String membersPhone,String membersNickName,String orderPayedTime){
		String sumSqlSql="select sum(wd.wDetailsAccount) as sumAccount from WithdrawDetails wd inner join Members ms on wd.membersID =ms.membersID where 1=1";
		if(membersPhone!=null && membersPhone!=""){
			sumSqlSql+=" and ms.membersPhone='"+membersPhone+"'";
		}
		if(membersNickName!=null && membersNickName!=""){
			sumSqlSql+=" and ms.membersNickName='"+membersNickName+"'";
		}
		if(orderPayedTime!=null && orderPayedTime!=""){
			String time[]=orderPayedTime.split(",");
			Timestamp timeStart = new Timestamp(System.currentTimeMillis());
			Timestamp timeEnd = new Timestamp(System.currentTimeMillis());
			timeStart = Timestamp.valueOf(time[0]);
			timeEnd = Timestamp.valueOf(time[1]);
			sumSqlSql+=" and wd.createTime>='"+timeStart+"'";
			sumSqlSql+=" and wd.createTime<='"+timeEnd+"'";
		}
		JSONArray jsonArray=super.findJsonArray(sumSqlSql,null);
		return jsonArray;
	}
	
	
	
	
	
	
	/**方法名称: expenditureListExcelPort<br>
	 * 描述：对账支出Excel导出
	 * 作者: 王小欢
	 * 修改日期：2015年9月17日下午1:45:46
	 * @param membersPhone
	 * @param membersNickName
	 * @param orderPayedTime
	 * @return
	 * @throws IOException 
	 */
	public Workbook expenditureListExcelPort(String membersPhone,String membersNickName,String orderPayedTime) throws IOException{
		String sql="select wd.wDetailsAccount,wd.wDetailsUserName,wd.wDetailsName,wd.createTime,wd.wDetailsType,ms.membersPhone,ms.membersNickName from WithdrawDetails wd inner join Members ms on wd.membersID =ms.membersID where 1=1";
		if(membersPhone!=null && membersPhone!=""){
			sql+=" and ms.membersPhone='"+membersPhone+"'";
		}
		if(membersNickName!=null && membersNickName!=""){
			sql+=" and ms.membersNickName='"+membersNickName+"'";
		}
		if(orderPayedTime!=null && orderPayedTime!=""){
			String time[]=orderPayedTime.split(",");
			Timestamp timeStart = new Timestamp(System.currentTimeMillis());
			Timestamp timeEnd = new Timestamp(System.currentTimeMillis());
			timeStart = Timestamp.valueOf(time[0]);
			timeEnd = Timestamp.valueOf(time[1]);
			sql+=" and wd.createTime>='"+timeStart+"'";
			sql+=" and wd.createTime<='"+timeEnd+"'";
		}
		sql+= " order by wd.createTime desc ";
		JSONArray jsonArray=super.findJsonArray(sql,null);
		String[] excelHeader = { "日期", "用户手机号", "用户昵称","用户支付宝账号","用户支付宝名字","金额"};
		// 创建excel
	    HSSFWorkbook wb = new HSSFWorkbook();
	    // 创建sheet
	    HSSFSheet sheet = wb.createSheet("对账支出Excel");
	    sheet.setColumnWidth(0,200);
	    // 创建一行
	    HSSFRow rowTitle = sheet.createRow(0);
	    HSSFCellStyle style = wb.createCellStyle();  
	    // 设置居中样式  
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中  
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中

	    // 在行上创建1列
	    for (int i = 0; i < excelHeader.length; i++) {  
	    	 HSSFCell cellTitle = rowTitle.createCell(i);
	    	 cellTitle.setCellValue(excelHeader[i]); 
	    	 cellTitle.setCellStyle(style);    
             sheet.setColumnWidth(i, 5000);
        } 	   
	    for (int i = 0; i < jsonArray.size(); i++) {  
	    	rowTitle = sheet.createRow(i + 1);  
	    	JSONObject jb=jsonArray.getJSONObject(i);
            rowTitle.createCell(0).setCellValue(jb.getString("createTime"));  
            rowTitle.createCell(1).setCellValue(jb.getString("membersPhone"));
            rowTitle.createCell(2).setCellValue(jb.getString("membersNickName")); 
            rowTitle.createCell(3).setCellValue(jb.getString("wDetailsUserName"));  
            rowTitle.createCell(4).setCellValue(jb.getString("wDetailsName"));  
            rowTitle.createCell(5).setCellValue((double)jb.getInt("wDetailsAccount")/100); 
        }
	    return wb;
	}
}
