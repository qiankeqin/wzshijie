package com.lagoqu.worldplay.api.page;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.constants.Constants;
import com.lagoqu.worldplay.util.Sign;

@Controller
@Scope("prototype")
@RequestMapping("/myPage")
public class MyPage extends APIController{
	/**
	 * 方法名称: toMy<br>
	 * 描述：跳转到我的页面
	 * 作者: 邢留杰
	 * 修改日期：2015年6月12日上午10:31:46
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String toMy() throws Exception{
		if(!super.isMemberLogin()){
			return "redirect:" + Constants.WEIXIN_USERINFO; 
		}
		 super.setRequestAttibute("crrrentMember", super.getMember());
		return "my/my.html";
	}
	/**
	 * 方法名称: toLaunch<br>
	 * 描述：跳转到发起
	 * 作者: 邢留杰
	 * 修改日期：2015年6月12日上午11:20:24
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "launch", method = RequestMethod.GET)
	public String toLaunch() throws Exception{
		if(!super.isMemberLogin()){
			return "redirect:" + Constants.WEIXIN_USERINFO; 
		}
		 super.setRequestAttibute("crrrentMember", super.getMember());
		return "my/my_faqi.html";
	}
	/**
	 * 方法名称: toAttention<br>
	 * 描述：跳转到关注
	 * 作者: 邢留杰
	 * 修改日期：2015年6月12日上午11:20:12
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "attention", method = RequestMethod.GET)
	public String toAttention() throws Exception{
		if(!super.isMemberLogin()){
			return "redirect:" + Constants.WEIXIN_USERINFO; 
		}
		 super.setRequestAttibute("crrrentMember", super.getMember());
		return "my/my_shoucang.html";
	}
	/**
	 * 方法名称: toPartake<br>
	 * 描述：跳转到参与
	 * 作者: 邢留杰
	 * 修改日期：2015年6月12日上午11:19:44
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "partake", method = RequestMethod.GET)
	public String toPartake() throws Exception{
		if(!super.isMemberLogin()){
			return "redirect:" + Constants.WEIXIN_USERINFO; 
		}
		super.setRequestAttibute("crrrentMember", super.getMember());
		return "my/my_canyu.html";
	}
	/**
	 * 方法名称: toSet<br>
	 * 描述：跳转到设置
	 * 作者: 邢留杰
	 * 修改日期：2015年6月12日上午11:19:35
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "set", method = RequestMethod.GET)
	public String toSet() throws Exception{
		if(!super.isMemberLogin()){
			return "redirect:" + Constants.WEIXIN_USERINFO; 
		}
		Map<String,String> ret = Sign.sign("http://www.wzshijie.com/myPage/set");
		super.setRequestAttibute("accessToken", ret.get("accessToken"));
		super.setRequestAttibute("url",ret.get("url"));
        super.setRequestAttibute("jsapi_ticket",ret.get("jsapi_ticket"));
        super.setRequestAttibute("nonceStr",ret.get("nonceStr"));
        super.setRequestAttibute("timestamp",ret.get("timestamp"));
        super.setRequestAttibute("signature",ret.get("signature"));
        super.setRequestAttibute("crrrentMember", super.getMember());
		return "my/my_set.html";
	}
	/**
	 * 方法名称: checkIdCard<br>
	 * 描述：身份认证页面
	 * 作者: 邢留杰
	 * 修改日期：2015年7月29日上午11:20:27
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkIdCard/{membersID}", method = RequestMethod.GET)
	public String checkIdCard(@PathVariable("membersID") int membersID) throws Exception{
		 super.setRequestAttibute("mbID", membersID);
		return "my/my_shenfenrenzheng.html";
	}
	
	
	/**
	 * 方法名称: withdraw<br>
	 * 描述：用户提现
	 * 作者: 王小欢
	 * 修改日期：2015年7月29日上午14:20:27
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/withdraw/{membersID}", method = RequestMethod.GET)
	public String withdraw(@PathVariable("membersID") int membersID) throws Exception{
		 super.setRequestAttibute("mbID", membersID);
		return "my/my_withdraw.html";
	}
}
