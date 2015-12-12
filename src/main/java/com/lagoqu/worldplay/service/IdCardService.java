package com.lagoqu.worldplay.service;

import java.sql.SQLException;
import java.text.ParseException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.IdCard;
import com.lagoqu.worldplay.entity.IdCardRecord;

@Service
public class IdCardService extends BaseDao<IdCard>{
	/**
	 * 方法名称: saveIdCard<br>
	 * 描述：新增一条数据
	 * 作者: 邢留杰
	 * 修改日期：2015年7月16日下午6:02:58
	 * @param jObject
	 * @return
	 * @throws ParseException
	 */
	public int saveIdCard(JSONObject jObject) throws ParseException{
		IdCard ic=(IdCard) JSONObject.toBean(jObject, IdCard.class);
		ic.setState(0);
		int icId=insertBackID(ic);
		return icId;
	}
	/**
	 * 方法名称: updateIdCard<br>
	 * 描述：修改身份信息
	 * 作者: 邢留杰
	 * 修改日期：2015年7月19日上午11:54:41
	 * @param jObject
	 * @return
	 * @throws ParseException
	 */
	public boolean updateIdCard(JSONObject jObject,int idCard) throws ParseException{
		IdCard ic=(IdCard) JSONObject.toBean(jObject, IdCard.class);
		String sql = "update IdCard set idCardName=?,correctSideImage=?,oppositeSideImage=?,idCardNum=?,createTime=CURRENT_TIMESTAMP,state=0  where membersID =? and idCardId =?";
		boolean bl = super.execSql(sql,ic.getIdCardName(),ic.getCorrectSideImage(),ic.getOppositeSideImage(),ic.getIdCardNum(),ic.getMembersID(),idCard);
		return bl;
	}
	/**
	 * 方法名称: selectByMembersId<br>
	 * 描述：
	 * 作者: 邢留杰
	 * 修改日期：2015年7月17日下午1:50:19
	 * @param jObject
	 * @return
	 * @throws ParseException
	 */
	public JSONObject selectByMembersId(int membersID) throws ParseException{
		String sql = "select state,idCardId  from IdCard where membersID =?";
		JSONObject js = super.findJson(sql,null,membersID);
		return js;
	}
	/**
	 * 方法名称: findPageData<br>
	 * 描述：待审核列表
	 * 作者: 邢留杰
	 * 修改日期：2015年7月19日上午11:54:22
	 * @param page
	 * @param size
	 * @return
	 */
	public Pagination<JSONArray> findPageData(int page,int size,String membersPhone){
		Pagination<JSONArray> pagination=new Pagination<JSONArray>();
		pagination.setPageNo(page);
		pagination.setPageSize(size);
		StringBuffer sql = new StringBuffer();
		sql.append("select ms.membersPhone,ms.membersNickName,ic.idCardId,ic.membersID,ic.idCardName,ic.correctSideImage,ic.oppositeSideImage,ic.idCardNum,ic.createTime from IdCard ic inner join Members ms on ic.membersID =ms.membersID where ic.state=0");
		StringBuffer countSql = new StringBuffer();
		countSql.append("select count(ic.idCardId) from IdCard ic inner join Members ms on ic.membersID =ms.membersID where ic.state=0");
		if(membersPhone!=null&&!"".equals(membersPhone)){
			sql.append(" and ms.membersPhone='"+membersPhone+"'");
			countSql.append(" and ms.membersPhone='"+membersPhone+"'");
		}
		sql.append(" order by ic.createTime asc");
		return super.findListJsonByPages(pagination, sql.toString(), countSql.toString());
	}
	/**
	 * 方法名称: review<br>
	 * 描述：审核成功修改状态
	 * 作者: 邢留杰
	 * 修改日期：2015年7月19日下午4:41:08
	 * @param idCardId
	 * @param idCardRecordState
	 * @throws ParseException
	 * @throws SQLException
	 */
	public void reviewSuccess(int idCardId,String idCardRecordState) throws ParseException, SQLException{
		IdCard idCard = super.get(idCardId);
		String sql = "insert into IdCardRecord (membersID,idCardName,correctSideImage,oppositeSideImage,idCardNum,idCardRecordState,updateTime) values ('"+idCard.getMembersID()+"','"+idCard.getIdCardName()+"','"+idCard.getCorrectSideImage()+"','"+idCard.getOppositeSideImage()+"','"+idCard.getIdCardNum()+"','"+idCardRecordState+"','"+idCard.getCreateTime()+"')"; 
		super.execSql(sql);
		String sql2 = "update IdCard set state=1  where idCardId =?";
		super.execSql(sql2,idCardId);
		String sql3 = "update Members set identifyState=2  where membersID =?";
		super.execSql(sql3,idCard.getMembersID());
	}
	/**
	 * 方法名称: review<br>
	 * 描述：审核失败修改状态
	 * 作者: 邢留杰
	 * 修改日期：2015年7月19日下午4:41:08
	 * @param idCardId
	 * @param idCardRecordState
	 * @throws ParseException
	 * @throws SQLException
	 */
	public void reviewFail(int idCardId,String idCardRecordState) throws ParseException, SQLException{
		IdCard idCard = super.get(idCardId);
		String sql = "insert into IdCardRecord (membersID,idCardName,correctSideImage,oppositeSideImage,idCardNum,idCardRecordState,updateTime) values ('"+idCard.getMembersID()+"','"+idCard.getIdCardName()+"','"+idCard.getCorrectSideImage()+"','"+idCard.getOppositeSideImage()+"','"+idCard.getIdCardNum()+"','"+idCardRecordState+"','"+idCard.getCreateTime()+"')"; 
		super.execSql(sql);
		String sql2 = "update IdCard set state=2  where idCardId =?";
		super.execSql(sql2,idCardId);
		String sql3 = "update Members set identifyState=3  where membersID =?";
		super.execSql(sql3,idCard.getMembersID());
	}
	
	
	public JSONObject findByIdCords(int membersID) throws SQLException{
		String sql="select * from IdCard where state=1 and membersID="+membersID;
		JSONObject js = super.findJson(sql, null);
		return js;
	}
}
 