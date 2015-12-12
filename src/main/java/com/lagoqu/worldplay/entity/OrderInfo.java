package com.lagoqu.worldplay.entity;

import java.sql.Timestamp;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

@ATable(name = "OrderInfo", pkname = "orderID")
public class OrderInfo extends BaseEntity{
	private int orderID;     	//订单id
	private int crowdfundID; 	//助力id
	private int membersID; 		//会员id
	private String orderName;	//订单名称
	private int orderPrice; 	//订单总额
	private int orderPayType;	//支付类型  0支付宝 1.微信
	private int orderSource;    //订单来源  0 心愿 1足迹
	private int orderNum;		//订单数量
	private int orderIsOk;		//订单是否完成
	private String orderMemo;	//订单留言
	private Timestamp orderPayedTime;//支付时间
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getCrowdfundID() {
		return crowdfundID;
	}
	public void setCrowdfundID(int crowdfundID) {
		this.crowdfundID = crowdfundID;
	}
	public int getMembersID() {
		return membersID;
	}
	public void setMembersID(int membersID) {
		this.membersID = membersID;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public int getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}
	public int getOrderPayType() {
		return orderPayType;
	}
	public void setOrderPayType(int orderPayType) {
		this.orderPayType = orderPayType;
	}
	
	public int getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(int orderSource) {
		this.orderSource = orderSource;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public int getOrderIsOk() {
		return orderIsOk;
	}
	public void setOrderIsOk(int orderIsOk) {
		this.orderIsOk = orderIsOk;
	}
	public String getOrderMemo() {
		return orderMemo;
	}
	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}
	public Timestamp getOrderPayedTime() {
		return orderPayedTime;
	}
	public void setOrderPayedTime(Timestamp orderPayedTime) {
		this.orderPayedTime = orderPayedTime;
	}
	
}
