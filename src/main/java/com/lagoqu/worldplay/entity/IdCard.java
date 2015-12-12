package com.lagoqu.worldplay.entity;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**描述：用户身份证审核信息<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月23日上午11:03:15 <br>
 * E-mail:  <br> 
 */
@ATable(name = "IdCard", pkname = "idCardId")
public class IdCard extends BaseEntity{
	private int idCardId;				//身份证编号
	private int membersID;				//用户编号
	private String idCardName;			//身份证真实名字
	private String correctSideImage;	//身份证正面
	private String oppositeSideImage;	//身份证反面
	private String idCardNum;			//身份证号
	private int state=0;					//审核状态 0 审核中 1 审核成功 2审核失败
	public int getIdCardId() {
		return idCardId;
	}
	public void setIdCardId(int idCardId) {
		this.idCardId = idCardId;
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
