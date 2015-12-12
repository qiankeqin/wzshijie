package com.lagoqu.worldplay.bms.service;

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
import com.lagoqu.worldplay.entity.OrderInfo;
import com.mysql.fabric.xmlrpc.base.Data;
import com.mysql.fabric.xmlrpc.base.Value;
/**描述：订单<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年7月6日下午4:15:34 <br>
 * E-mail:  <br> 
 */
@Service
public class OrderInfoBmsService extends BaseDao<OrderInfo>{

	
	/**方法名称: proceedsList<br>
	 * 描述：对账收入
	 * 作者: 王小欢
	 * 修改日期：2015年9月17日下午1:47:43
	 * @param page
	 * @param size
	 * @param membersPhone
	 * @param membersNickName
	 * @param orderPayedTime
	 * @param orderPayType
	 * @return
	 */
	public Pagination<JSONArray> proceedsList(int page,int size,String membersPhone,String membersNickName,String orderPayedTime,int orderPayType){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select odf.orderID,odf.orderPayedTime,odf.orderPrice,odf.orderPayType,ms.membersPhone,ms.membersNickName from OrderInfo odf inner join Members ms on odf.membersID =ms.membersID where odf.orderIsOk=1";
		String countSql="select count(odf.orderID) from OrderInfo odf inner join Members ms on odf.membersID =ms.membersID where odf.orderIsOk=1";
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
			sql+=" and odf.orderPayedTime>='"+timeStart+"'";
			sql+=" and odf.orderPayedTime<='"+timeEnd+"'";
			countSql+=" and odf.orderPayedTime>='"+timeStart+"'";
			countSql+=" and odf.orderPayedTime<='"+timeEnd+"'";
		}
		if(orderPayType!=2){
		    sql+=" and odf.orderPayType="+orderPayType;
		    countSql+=" and odf.orderPayType="+orderPayType;
		}
		sql+= " order by odf.orderPayedTime desc ";
		findListJsonByPages(pagination,sql,countSql);
		
		return pagination;
	}
	
	
	public JSONArray proceedsSum(String membersPhone,String membersNickName,String orderPayedTime,int orderPayType){
		String sumSqlSql="select sum(odf.orderPrice) as sumOrderPrice from OrderInfo odf inner join Members ms on odf.membersID =ms.membersID where odf.orderIsOk=1";
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
			sumSqlSql+=" and odf.orderPayedTime>='"+timeStart+"'";
			sumSqlSql+=" and odf.orderPayedTime<='"+timeEnd+"'";
		}
		if(orderPayType!=2){
		    sumSqlSql+=" and odf.orderPayType="+orderPayType;
		}
		JSONArray jsonArray=super.findJsonArray(sumSqlSql,null);
		return jsonArray;
	}
	
	
	
	/**方法名称: proceedsListExcelPort<br>
	 * 描述：对账收入Excel导出
	 * 作者: 王小欢
	 * 修改日期：2015年9月18日上午10:46:48
	 * @param page
	 * @param size
	 * @param membersPhone
	 * @param membersNickName
	 * @param orderPayedTime
	 * @param orderPayType
	 * @return
	 * @throws IOException 
	 */
	public Workbook proceedsListExcelPort(String membersPhone,String membersNickName,String orderPayedTime,int orderPayType) throws IOException{
		String sql="select odf.orderID,odf.orderPayedTime,odf.orderPrice,odf.orderPayType,ms.membersPhone,ms.membersNickName from OrderInfo odf inner join Members ms on odf.membersID =ms.membersID where odf.orderIsOk=1";
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
			sql+=" and odf.orderPayedTime>='"+timeStart+"'";
			sql+=" and odf.orderPayedTime<='"+timeEnd+"'";
		}
		if(orderPayType!=2){
		    sql+=" and odf.orderPayType="+orderPayType;
		}
		sql+= " order by odf.orderPayedTime desc ";
		JSONArray jsonArray=super.findJsonArray(sql,null);
		String[] excelHeader = { "订单号", "日期", "用户手机号","用户昵称 ","金额 ","支付方式 "};
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
            rowTitle.createCell(0).setCellValue(jb.getInt("orderID"));  
            rowTitle.createCell(1).setCellValue(jb.getString("orderPayedTime"));  
            rowTitle.createCell(2).setCellValue(jb.getString("membersPhone"));
            rowTitle.createCell(3).setCellValue(jb.getString("membersNickName"));  
            rowTitle.createCell(4).setCellValue((double)jb.getInt("orderPrice")/100); 
            if(jb.getInt("orderPayType")==0){
            	rowTitle.createCell(5).setCellValue("支付宝");
            }if(jb.getInt("orderPayType")==1){
            	rowTitle.createCell(5).setCellValue("微信");
            }
        }
	   
		return wb;
	}
	
	
	
	/**方法名称: MembersMarkList<br>
	 * 描述：用户打赏记录
	 * 作者: 王小欢
	 * 修改日期：2015年12月10日下午6:09:25
	 * @param page
	 * @param size
	 * @param membersPhone
	 * @param membersNickName
	 * @return
	 */
	public Pagination<JSONArray> MembersMarkList(int page,int size,String membersPhone,String membersNickName){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select odf.orderID,sum(odf.orderPrice) as sumPrice,ms.membersPhone,ms.membersNickName,ms.membersSex,ms.identifyState,ms.membersState,ms.membersID from OrderInfo odf inner join Members ms on odf.membersID =ms.membersID where odf.orderIsOk=1";
		String countSql="select count(distinct odf.membersID) from OrderInfo odf inner join Members ms on odf.membersID =ms.membersID where odf.orderIsOk=1";
		if(membersPhone!=null && membersPhone!=""){
			sql+=" and ms.membersPhone='"+membersPhone+"'";
			countSql+=" and ms.membersPhone='"+membersPhone+"'";
		}
		if(membersNickName!=null && membersNickName!=""){
			sql+=" and ms.membersNickName='"+membersNickName+"'";
			countSql+=" and ms.membersNickName='"+membersNickName+"'";
		}
		sql+= " group by odf.membersID  order by sum(odf.orderPrice) desc";
		findListJsonByPages(pagination,sql,countSql);
		
		return pagination;
	}
	
	
	
	
	
	/**方法名称: MembersMarkDetails<br>
	 * 描述：用户打赏详情
	 * 作者: 王小欢
	 * 修改日期：2015年12月11日上午10:34:58
	 * @param page
	 * @param size
	 * @param membersID
	 * @return
	 */
	public Pagination<JSONArray> MembersMarkDetails(int page,int size,int membersID){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select odf.orderID,odf.crowdfundID,odf.membersID,odf.orderName,odf.orderPrice,odf.orderPayType,odf.orderSource from OrderInfo odf where odf.orderIsOk=1 and odf.membersID="+membersID;
		String countSql="select count(odf.orderID) from OrderInfo odf where odf.orderIsOk=1 and odf.membersID="+membersID;
		sql+= " order by odf.orderID desc";
		findListJsonByPages(pagination,sql,countSql);
		return pagination;
	}
}
