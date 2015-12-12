package com.lagoqu.worldplay.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lagoqu.common.db.dao.BaseDao;
import com.lagoqu.worldplay.entity.ArtbaseType;
@Service
public class AppArtbaseTypeService extends BaseDao<ArtbaseType>{

	
	
	/**方法名称: ArtbaseTypeList<br>
	 * 描述：文字库分类列表
	 * 作者: 王小欢
	 * 修改日期：2015年10月31日上午11:35:37
	 * @return
	 */
	public JSONObject ArtbaseTypeList(){
		JSONObject backJsonObject=new JSONObject();
		String sqlselect="select * from ArtbaseType";
		JSONArray jsonArray = findAllJson();
		if(jsonArray!=null){
			backJsonObject.put("data",jsonArray);
			backJsonObject.put("state", true);
			return backJsonObject;
		}else {
			backJsonObject.put("message","获取段子分类信息失败，请重试");
			backJsonObject.put("state", false);
			return backJsonObject; 
		}
	}
}
