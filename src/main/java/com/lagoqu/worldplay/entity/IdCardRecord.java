package com.lagoqu.worldplay.entity;

import java.sql.Timestamp;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**描述：用户身份证审核记录<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月23日上午11:07:59 <br>
 * E-mail:  <br> 
 */
@ATable(name = "IdCardRecord", pkname = "idCardRecordId")
public class IdCardRecord  extends BaseEntity{
	private int idCardRecordId;		    //身份证编号
	private int membersID;				//用户编号
	private String idCardName;			//身份证真实名字
	private String correctSideImage;	//身份证正面
	private String oppositeSideImage;	//身份证反面
	private String idCardNum;			//身份证号
	private String idCardRecordState;	//审核状态 描述
	private Timestamp updateTime;       //审核时间
	public int getIdCardRecordId() {
		return idCardRecordId;
	}
	public void setIdCardRecordId(int idCardRecordId) {
		this.idCardRecordId = idCardRecordId;
	}
	public int getMembersID() {
		return membersID;
	}
	public void setMembersID(int membersID) {
		this.membersID = membersID;
	}
	public String getIdCardName() {
		return idCardName;
	}
	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
	}
	public String getCorrectSideImage() {
		return correctSideImage;
	}
	public void setCorrectSideImage(String correctSideImage) {
		this.correctSideImage = correctSideImage;
	}
	public String getOppositeSideImage() {
		return oppositeSideImage;
	}
	public void setOppositeSideImage(String oppositeSideImage) {
		this.oppositeSideImage = oppositeSideImage;
	}
	public String getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public String getIdCardRecordState() {
		return idCardRecordState;
	}
	public void setIdCardRecordState(String idCardRecordState) {
		this.idCardRecordState = idCardRecordState;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
