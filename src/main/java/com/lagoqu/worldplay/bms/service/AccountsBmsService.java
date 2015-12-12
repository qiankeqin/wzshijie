package com.lagoqu.worldplay.bms.service;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Accounts;
@Service
public class AccountsBmsService extends BaseDao<Accounts>{

	
	
	/**方法名称: findByIdAccounts<br>
	 * 描述：个人账户信息
	 * 作者: 王小欢
	 * 修改日期：2015年7月19日下午7:03:58
	 * @param membersID
	 * @return
	 * @throws SQLException
	 */
	public JSONObject findByIdAccounts(int membersID) throws SQLException{
		String sql="select accountBalance as  balance,accountAvailable as available,accountGet as get,accountWithdraw as withdraw from  Accounts where membersID="+membersID;
		JSONObject js = super.findJson(sql, null);
		return js;
	}
	
	
	
	/**方法名称: AccountsList<br>
	 * 描述：用户账户
	 * 作者: 王小欢
	 * 修改日期：2015年11月20日下午1:13:56
	 * @param page
	 * @param size
	 * @param membersPhone
	 * @param membersNickName
	 * @return
	 */
	public Pagination<JSONArray> AccountsList(int page,int size,String membersPhone,String membersNickName){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		String sql="select ac.accountBalance,ac.accountAvailable,ac.accountWithdraw,ac.accountGet,ms.membersID,ms.membersPhone,ms.membersNickName,ms.identifyState from Accounts ac inner join Members ms on ac.membersID =ms.membersID where 1=1 ";
		String countSql="select count(ac.accountID) from Accounts ac inner join Members ms on ac.membersID =ms.membersID";
		if(membersPhone!=null && membersPhone!=""){
			sql+=" and ms.membersPhone='"+membersPhone+"'";
			countSql+=" and ms.membersPhone='"+membersPhone+"'";
		}
		if(membersNickName!=null && membersNickName!=""){
			sql+=" and ms.membersNickName='"+membersNickName+"'";
			countSql+=" and ms.membersNickName='"+membersNickName+"'";
		}
	//	sql+= " order by odf.orderPayedTime desc ";
		findListJsonByPages(pagination,sql,countSql);
		
		return pagination;
	}
	
	
	
	
	/**方法名称: AccountsSum<br>
	 * 描述：用户账户总计
	 * 作者: 王小欢
	 * 修改日期：2015年11月20日下午1:14:12
	 * @param membersPhone
	 * @param membersNickName
	 * @return
	 */
	public JSONArray AccountsSum(String membersPhone,String membersNickName){
		String sumSqlSql="select sum(ac.accountBalance) as sumAccountBalance,sum(ac.accountAvailable) as sumAccountAvailable,sum(ac.accountWithdraw) as sumAccountWithdraw,sum(ac.accountGet) as sumAccountGet from Accounts ac inner join Members ms on ac.membersID =ms.membersID where 1=1 ";
		if(membersPhone!=null && membersPhone!=""){
			sumSqlSql+=" and ms.membersPhone='"+membersPhone+"'";
		}
		if(membersNickName!=null && membersNickName!=""){
			sumSqlSql+=" and ms.membersNickName='"+membersNickName+"'";
		}
		JSONArray jsonArray=super.findJsonArray(sumSqlSql,null);
		return jsonArray;
	}
}
