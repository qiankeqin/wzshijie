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

public class BMSController extends BaseController {
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
	public void saveUser(String userName) {
		super.getSession().setAttribute(SessionCache.current_user, userName);
	}

	public String getLoginUser() {
		return (String) super.getSession().getAttribute(SessionCache.current_user);
	}
	
	public void loginout() {
		super.getSession().removeAttribute(SessionCache.current_user);
	}

	/**方法名称: isMemberLogin<br>
	 * 描述：判断会员是否登录
	 * 作者: 王小欢
	 * 修改日期：2015年6月25日下午5:07:36
	 * @return
	 */
	protected boolean isUserLogin() {
		boolean isLogin = false;
		if (null != super.getSession().getAttribute(SessionCache.current_user)) {
			isLogin = true;
		}
		return isLogin;
	}
}
