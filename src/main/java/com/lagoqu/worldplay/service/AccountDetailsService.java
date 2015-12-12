package com.lagoqu.worldplay.service;

import net.sf.json.JSONArray;

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
public class AccountDetailsService extends BaseDao<AccountDetails>{

	
	
	/**方法名称: AccountDetailsInsert<br>
	 * 描述：增加账户明细
	 * 作者: 王小欢
	 * 修改日期：2015年7月6日下午4:20:13
	 * @param aDetails
	 * @return
	 */
	public int AccountDetailsInsert(AccountDetails aDetails){
		AccountDetails accountDetails=new AccountDetails();
		accountDetails.setMembersID(aDetails.getMembersID());
		accountDetails.setDetailsAccount(aDetails.getDetailsAccount());
		accountDetails.setDetailsType(aDetails.getDetailsType());
		accountDetails.setDetailsDesc(aDetails.getDetailsDesc());
		accountDetails.setDetailsMembersId(aDetails.getDetailsMembersId());
		accountDetails.setDetailsTime(aDetails.getDetailsTime());
		int detailsID=insertBackID(accountDetails);
		return detailsID;
	}
	
	
	
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
		String sql="select ms.membersImage as image,ms.membersNickName as nickname,ads.detailsAccount as account,ads.createTime,ads.detailsType as type from AccountDetails ads inner join Members ms on ads.detailsMembersId =ms.membersID where ads.membersID=? order by ads.createTime desc";
		String countSql="select count(ads.detailsID) from AccountDetails ads where ads.membersID=?";
		findListJsonByPages(pagination,sql,countSql,membersID);
		return pagination;
	}
}
