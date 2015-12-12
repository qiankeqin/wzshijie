package com.lagoqu;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.lagoqu.common.spring.test.BaseSpringTest;
import com.lagoqu.common.util.Pagination;
import com.lagoqu.worldplay.service.AppMembersService;

/**描述：用户登录Api测试<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年10月27日下午1:41:36 <br>
 * E-mail:  <br> 
 */
public class MembersLoginApiTest extends BaseSpringTest{

	@Resource
	AppMembersService appMembersService;
	/**方法名称: test1<br>
	 * 描述：
	 * 作者: 王小欢
	 * 修改日期：2015年10月27日下午1:42:23
	 * @throws IOException 
	 */
	@Test
	public void getVerificationCode() throws IOException{
		System.out.println("start......");
		String membersPhone ="13466691514";
		int codeType=2;
		JSONObject backJsonObject=appMembersService.getVerificationCode(membersPhone,codeType);
		boolean state=backJsonObject.getBoolean("state");
		System.out.println(state);
		if(state==false){
			System.out.println(backJsonObject.get("message"));
		}else{
			System.out.println(backJsonObject.get("data"));
		}
		System.out.println("end......");

	}
}
