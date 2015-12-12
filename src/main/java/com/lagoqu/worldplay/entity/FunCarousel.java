package com.lagoqu.worldplay.entity;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

/**
 * 描述：<br>
 * 作者：邢留杰 <br>
 * 修改日期：2015年12月8日下午2:08:37 <br>
 * E-mail:  <br>
 */
@ATable(name = "FunCarousel", pkname = "funCarouselId")
public class FunCarousel extends BaseEntity{
	private int funCarouselId;
	private long funID;
	private String recommendImg;//推荐图片
	
	private String description;//推荐描述

	
	public long getFunID() {
		return funID;
	}

	public void setFunID(long funID) {
		this.funID = funID;
	}

	public int getFunCarouselId() {
		return funCarouselId;
	}
	
	public void setFunCarouselId(int funCarouselId) {
		this.funCarouselId = funCarouselId;
	}


	public String getRecommendImg() {
		return recommendImg;
	}

	public void setRecommendImg(String recommendImg) {
		this.recommendImg = recommendImg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

}
