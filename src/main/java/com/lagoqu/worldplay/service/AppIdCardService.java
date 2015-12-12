package com.lagoqu.worldplay.service;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.IdCard;

@Service
public class AppIdCardService extends BaseDao<IdCard>{
	
	@Resource
	AppMembersService appMembersService;
	
	/**方法名称: userIdCardSubmit<br>
	 * 描述：用户身份证信息提交
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午6:51:03
	 * @param membersID
	 * @param idCardName
	 * @param correctSideImage
	 * @param oppositeSideImage
	 * @param idCardNum
	 * @return
	 */
	public JSONObject userIdCardSubmit(int membersID,String idCardName,String correctSideImage,String oppositeSideImage,String idCardNum){
		JSONObject backJsonObject=new JSONObject();
		String sql = "select state,idCardId  from IdCard where membersID =?";
		JSONObject js = super.findJson(sql,null,membersID);
		if(js==null){
			//插入用户身份证信息
			int idCardId=this.saveIdCard(membersID, idCardName, correctSideImage, oppositeSideImage, idCardNum);
			if(idCardId==-1){
				backJsonObject.put("message","身份证信息提交失败");
				backJsonObject.put("state", false);
				return backJsonObject;
			}
		}else {
			if(js.getInt("state")==1){
				backJsonObject.put("message","审核成功，不能再次提交");
				backJsonObject.put("state", false);
				return backJsonObject; 
			}
			if(js.getInt("state")==0){
				backJsonObject.put("message","审核中,请耐心等待");
				backJsonObject.put("state", false);
				return backJsonObject; 
			}
			if(js.getInt("state")==2){
				boolean bl=this.updateIdCard(js.getInt("idCardId"),membersID,idCardName,correctSideImage,oppositeSideImage,idCardNum);
				if(bl==false){
					backJsonObject.put("message","身份证信息提交失败");
					backJsonObject.put("state", false);
					return backJsonObject; 
				}
			}
		}
		boolean bl=appMembersService.updateIdCard(1,membersID);
		if(bl==false){
			backJsonObject.put("message","个人信息修改失败");
			backJsonObject.put("state", false);
			return backJsonObject;
		}else{
			backJsonObject.put("data","身份证提交成功");
			backJsonObject.put("state", true);
			return backJsonObject; 
		}
	}
	
	
	/**方法名称: saveIdCard<br>
	 * 描述：保存用户身份证信息
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午6:13:58
	 * @param membersID
	 * @param idCardName
	 * @param correctSideImage
	 * @param oppositeSideImage
	 * @param idCardNum
	 * @return
	 */
	public int saveIdCard(int membersID,String idCardName,String correctSideImage,String oppositeSideImage,String idCardNum){
		IdCard idCard = new IdCard();
		idCard.setMembersID(membersID);
		idCard.setIdCardName(idCardName);
		idCard.setCorrectSideImage(correctSideImage);
		idCard.setOppositeSideImage(oppositeSideImage);
		idCard.setIdCardNum(idCardNum);
		idCard.setState(0);
		int idCardId=insertBackID(idCard);
		return idCardId;
	}
	
	
	/**方法名称: updateIdCard<br>
	 * 描述：修改用户身份证信息
	 * 作者: 王小欢
	 * 修改日期：2015年10月28日下午6:49:55
	 * @param idCardId
	 * @param membersID
	 * @param idCardName
	 * @param correctSideImage
	 * @param oppositeSideImage
	 * @param idCardNum
	 * @return
	 */
	public boolean updateIdCard(int idCardId,int membersID,String idCardName,String correctSideImage,String oppositeSideImage,String idCardNum){
		String sql = "update IdCard set idCardName=?,correctSideImage=?,oppositeSideImage=?,idCardNum=?,createTime=CURRENT_TIMESTAMP,state=0  where membersID =? and idCardId =?";
		boolean bl = super.execSql(sql,idCardName,correctSideImage,oppositeSideImage,idCardNum,membersID,idCardId);
		return bl;
	}
}
 