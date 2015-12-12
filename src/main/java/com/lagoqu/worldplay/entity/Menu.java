package com.lagoqu.worldplay.entity;
import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

@ATable(name = "Menu", pkname = "menuID")
public class Menu extends BaseEntity {
	
	/*
	public static final int id_level_top = 0;//顶级id
	public static final int level_max = 5;//最多层级
	*/	
	private int parentId;			//级别表识 0顶级 1二级别 2三级别
	private int menuID;				//菜单ID 主键
	private String menuName;		//菜单名字
	private String description;		//菜单描述
	private String url;				//菜单路径
	private int isFinal;			//是否为终极菜单(0:终极菜单 1：非终极菜单)
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getMenuID() {
		return menuID;
	}
	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getIsFinal() {
		return isFinal;
	}
	public void setIsFinal(int isFinal) {
		this.isFinal = isFinal;
	}
}
	