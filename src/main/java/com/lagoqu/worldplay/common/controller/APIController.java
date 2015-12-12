package com.lagoqu.worldplay.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lagoqu.common.framework.controller.BaseController;
import com.lagoqu.common.framework.util.json.JSONUtil;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.entity.Members;

public class APIController extends BaseController {
	private static final Logger log = LogManager.getLogger(APIController.class);

	public void returnFailJson(String erroString) throws IOException {
		returnJosn(response, false, "", erroString);
	}

	public void returnSuccessJson(String result) throws IOException {
		returnJosn(response, true, result, "");
	}

	protected JSONObject getRequestJsonParams() {
		if (request == null) {
			return null;
		}
		String pa = request.getParameter("params");
		if (pa == null) {
			try {
				returnFailJson("参数错误!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		JSONObject params = JSONObject.fromObject(pa);
		System.out.println(pa);
		params.optJSONObject(pa);
		return params;
	}

	protected Map<String, String[]> getRequestParamsMap() {
		if (request == null) {
			return null;
		}
		Map<String, String[]> map = request.getParameterMap();
		return map;
	}

	public String returnJSONEasyUISuccess(Pagination<?> p) throws IOException {
		JSONObject result = null;
		String jsonData = null;
		List<?> entityList = p.getList();
		long total = p.getTotalCount();
		Map pageMap = new HashMap();
		pageMap.put("total", total);

		List dataList = new ArrayList();
		for (int i = 0; i < entityList.size(); i++) {
			dataList.add(entityList.get(i));
		}
		pageMap.put("rows", dataList);
		result = JSONUtil.convertJSONObject(pageMap);
		jsonData = result.toString();

		this.writeJSON(response, jsonData, true);
		return null;
	}

	/**方法名称: saveMember<br>
	 * 描述：保存会员信息
	 * 作者: 王小欢
	 * 修改日期：2015年6月25日上午11:08:33
	 * @param members
	 */
	private void saveMember(Members members) {
		super.getSession().setAttribute(SessionCache.current_member, members);
	}

	/**方法名称: isMemberLogin<br>
	 * 描述：判断会员是否登录
	 * 作者: 王小欢
	 * 修改日期：2015年6月25日下午5:07:36
	 * @return
	 */
	protected boolean isMemberLogin() {
		boolean isLogin = false;
		if (null != super.getSession().getAttribute(SessionCache.current_member)) {
			isLogin = true;
		}
		return isLogin;
	}

	protected void setMember(JSONObject ms) throws Exception {
		Members members = (Members) JSONObject.toBean(ms, Members.class);
		/*
		 * if (StringUtils.isEmpty(members.getMembersImage())) {
		 * members.setMembersImage("/images/pic_02_11.jpg"); }
		 */

		this.saveMember(members);
	}

	protected void setMember(Members members) throws Exception {

		this.saveMember(members);
	}

	protected Members getMember() {
		return (Members) super.getSession().getAttribute(SessionCache.current_member);
	}
}
