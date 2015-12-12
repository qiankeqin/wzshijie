package com.lagoqu.worldplay.entity;

import com.lagoqu.common.db.entity.ATable;
import com.lagoqu.common.db.entity.BaseEntity;

@ATable(name = "news", pkname = "newsId")
public class News extends BaseEntity{
	    // Fields
	    private int newsId;  //新闻id
	    private long id;
		private long  funTypeID;   //哪好玩分类ID
		private String newsWriter;  //新闻作者
		private String newsTitle;   //新闻标题
		private String newsImg;    //新闻展示图
		private String newsDetailImg;//详情页头图
		private String newsSource;  //新闻源
		private String newsBrief;   //新闻描述
		private String newsContent;  //新闻内容
		private int newsSort=1000000;        //排序
		
		private String areaID;         //洲、地区
		private String provicesID;    //国家、城市
		private String destinationID;  //城市、市
		
		private String destinationName;    //城市名字
		private String areaType;    //类型   国内 1   国外 0
		private String apdID;    //最后一级ID
		private int newsTimes;   //浏览次数
		public int getNewsTimes() {
			return newsTimes;
		}
		public void setNewsTimes(int newsTimes) {
			this.newsTimes = newsTimes;
		}
		public int getNewsId() {
			return newsId;
		}
		public void setNewsId(int newsId) {
			this.newsId = newsId;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		
		public long getFunTypeID() {
			return funTypeID;
		}
		public void setFunTypeID(long funTypeID) {
			this.funTypeID = funTypeID;
		}
		public String getNewsWriter() {
			return newsWriter;
		}
		public void setNewsWriter(String newsWriter) {
			this.newsWriter = newsWriter;
		}
		public String getNewsTitle() {
			return newsTitle;
		}
		public void setNewsTitle(String newsTitle) {
			this.newsTitle = newsTitle;
		}
		public String getNewsImg() {
			return newsImg;
		}
		public void setNewsImg(String newsImg) {
			this.newsImg = newsImg;
		}
		public String getNewsDetailImg() {
			return newsDetailImg;
		}
		public void setNewsDetailImg(String newsDetailImg) {
			this.newsDetailImg = newsDetailImg;
		}
		public String getNewsSource() {
			return newsSource;
		}
		public void setNewsSource(String newsSource) {
			this.newsSource = newsSource;
		}
		public String getNewsBrief() {
			return newsBrief;
		}
		public void setNewsBrief(String newsBrief) {
			this.newsBrief = newsBrief;
		}
		public String getNewsContent() {
			return newsContent;
		}
		public void setNewsContent(String newsContent) {
			this.newsContent = newsContent;
		}
		public int getNewsSort() {
			return newsSort;
		}
		public void setNewsSort(int newsSort) {
			this.newsSort = newsSort;
		}
		public String getAreaID() {
			return areaID;
		}
		public void setAreaID(String areaID) {
			this.areaID = areaID;
		}
		public String getProvicesID() {
			return provicesID;
		}
		public void setProvicesID(String provicesID) {
			this.provicesID = provicesID;
		}
		public String getDestinationID() {
			return destinationID;
		}
		public void setDestinationID(String destinationID) {
			this.destinationID = destinationID;
		}
		public String getDestinationName() {
			return destinationName;
		}
		public void setDestinationName(String destinationName) {
			this.destinationName = destinationName;
		}
		public String getAreaType() {
			return areaType;
		}
		public void setAreaType(String areaType) {
			this.areaType = areaType;
		}
		public String getApdID() {
			return apdID;
		}
		public void setApdID(String apdID) {
			this.apdID = apdID;
		}
		
		
}
