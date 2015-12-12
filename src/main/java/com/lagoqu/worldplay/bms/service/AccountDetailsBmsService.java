package com.lagoqu.worldplay.bms.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.lagoqu.worldplay.entity.AccountDetails;
/**描述：账户明细<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年7月6日下午4:15:34 <br>
 * E-mail:  <br> 
 */
@Service
public class AccountDetailsBmsService extends BaseDao<AccountDetails>{

	/**方法名称: aDetailsList<br>
	 * 描述：查询个人账户明细
	 * 作者: 王小欢
	 * 修改日期：2015年7月7日上午9:48:04
	 * @param page
	 * @param size
	 * @param membersID
	 * @return
	 */
	public Pagination<JSONArray> aDetailsList(int page,int size,int membersID){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select ms.membersImage as image,ms.membersNickName as nickname,ads.detailsAccount as account,ads.createTime,ads.detailsType as type from AccountDetails ads inner join Members ms on ads.detailsMembersId =ms.membersID where ads.membersID="+membersID+" order by ads.createTime desc";
		String countSql="select count(ads.detailsID) from AccountDetails ads where ads.membersID="+membersID+"";
		System.out.println(sql);
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	
	
	/**方法名称: detailsList<br>
	 * 描述：对账总明细
	 * 作者: 王小欢
	 * 修改日期：2015年9月17日下午2:18:24
	 * @param page
	 * @param size
	 * @param orderPayedTime
	 * @return
	 */
	public Pagination<JSONArray> detailsList(int page,int size,String orderPayedTime){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select (select SUM(CASE detailsType WHEN 1 THEN detailsAccount ELSE -1*detailsAccount END) from AccountDetails where ad.createTime >=createTime) balance,CASE ad.detailsType WHEN 1 THEN ad.detailsAccount ELSE -1*ad.detailsAccount END as detailsAccount,ad.detailsType,ad.createTime from AccountDetails ad where 1=1";
		String countSql="select count(ad.detailsID) from AccountDetails ad where 1=1";
		if(orderPayedTime!=null && orderPayedTime!=""){
			String time[]=orderPayedTime.split(",");
			Timestamp timeStart = new Timestamp(System.currentTimeMillis());
			Timestamp timeEnd = new Timestamp(System.currentTimeMillis());
			timeStart = Timestamp.valueOf(time[0]);
			timeEnd = Timestamp.valueOf(time[1]);
			sql+=" and ad.createTime>='"+timeStart+"'";
			sql+=" and ad.createTime<='"+timeEnd+"'";
			countSql+=" and ad.createTime>='"+timeStart+"'";
			countSql+=" and ad.createTime<='"+timeEnd+"'";
		}
		sql+= " order by ad.createTime desc";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
	
	
	
	
	
	
	/**方法名称: detailsListExcelPort<br>
	 * 描述：对账总明细Excel导出
	 * 作者: 王小欢
	 * 修改日期：2015年9月17日下午2:18:24
	 * @param orderPayedTime
	 * @return
	 * @throws IOException 
	 */
	public Workbook detailsListExcelPort(String orderPayedTime) throws IOException{
		String sql="select (select SUM(CASE detailsType WHEN 1 THEN detailsAccount ELSE -1*detailsAccount END) from AccountDetails where ad.createTime >=createTime) balance,CASE ad.detailsType WHEN 1 THEN ad.detailsAccount ELSE -1*ad.detailsAccount END as detailsAccount,ad.detailsType,ad.createTime from AccountDetails ad where 1=1";
		if(orderPayedTime!=null && orderPayedTime!=""){
			String time[]=orderPayedTime.split(",");
			Timestamp timeStart = new Timestamp(System.currentTimeMillis());
			Timestamp timeEnd = new Timestamp(System.currentTimeMillis());
			timeStart = Timestamp.valueOf(time[0]);
			timeEnd = Timestamp.valueOf(time[1]);
			sql+=" and ad.createTime>='"+timeStart+"'";
			sql+=" and ad.createTime<='"+timeEnd+"'";
		}
		sql+= " order by ad.createTime desc ";
		JSONArray jsonArray=super.findJsonArray(sql,null);
		String[] excelHeader = {"日期", "收入/支出","余额 "};
		// 创建excel
	    HSSFWorkbook wb = new HSSFWorkbook();
	    // 创建sheet
	    HSSFSheet sheet = wb.createSheet("对账收入Excel");
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
            if(jb.getInt("detailsType")==1){
            	rowTitle.createCell(1).setCellValue("+"+(double)jb.getInt("detailsAccount")/100);
            }if(jb.getInt("detailsType")==2){
            	rowTitle.createCell(1).setCellValue("-"+(double)jb.getInt("detailsAccount")/100);
            }
            rowTitle.createCell(2).setCellValue((double)jb.getInt("balance")/100);  
           
        }
	   return wb;
	}
}
