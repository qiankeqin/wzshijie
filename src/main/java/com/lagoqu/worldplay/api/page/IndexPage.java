package com.lagoqu.worldplay.api.page;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lagoqu.common.util.RandomUtil;
import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.entity.Members;
import com.lagoqu.worldplay.util.WeixinToken;

@Controller
@Scope("prototype")
@RequestMapping("")
public class IndexPage extends APIController{
	/**
	 * 方法名称: toIndex<br>
	 * 描述：首页跳转
	 * 作者: 邢留杰
	 * 修改日期：2015年6月12日上午10:15:14
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String toIndex() throws Exception{
		return "index.html";
	}
	
	@RequestMapping(value = "v2Index", method = RequestMethod.GET)
	public String v2Index() throws Exception{
		return "index_v2.html";
	}
	
	
	/**方法名称: toIndexWeb<br>
	 * 描述：Web首页
	 * 作者: 王小欢
	 * 修改日期：2015年7月24日上午10:20:33
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String toDownLoad() throws Exception{
		return "toDownload.html";
	}
	
	/**方法名称: toIndexWeb<br>
	 * 描述：Web首页
	 * 作者: 王小欢
	 * 修改日期：2015年7月24日上午10:20:33
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "webDownLoad", method = RequestMethod.GET)
	public String toIndexWeb() throws Exception{
		return "webxz/indexWeb.html";
	}
	
	/**方法名称: toIndexWx<br>
	 * 描述：Wx下载页
	 * 作者: 王小欢
	 * 修改日期：2015年7月24日上午10:20:33
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "wxxz", method = RequestMethod.GET)
	public String toIndexWx() throws Exception{
		return "webxz/download-apk.html";
	}
	
	
	
	
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String toLogin() throws Exception{
		request.setCharacterEncoding("UTF-8");
		String membersWxID = request.getParameter("membersWxID");
		String cfId = request.getParameter("state");
		String membersImage = request.getParameter("membersImage");
		String membersNickName = request.getParameter("membersNickName");
		if("".equals(membersNickName.trim())){
			membersNickName = RandomUtil.getCharAndNum(6);
		}else{
			membersNickName=new String(membersNickName.getBytes("ISO-8859-1"),"UTF-8" );
		}
		int membersSex = Integer.parseInt(request.getParameter("membersSex"));
		super.setRequestAttibute("WxID", membersWxID);
		super.setRequestAttibute("cfId", cfId);
		super.setRequestAttibute("image", membersImage);
		super.setRequestAttibute("name", membersNickName);
		super.setRequestAttibute("sex", membersSex);
		return "login.html";
	}
	@RequestMapping(value = "toSkip", method = RequestMethod.GET)
	public String toSkip() throws Exception{
		return "toSkip.html";
	}
	@RequestMapping(value = "weixinlogin", method = RequestMethod.GET)
	public String weixinlogin() throws Exception{
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		Map<String,String> ret = WeixinToken.getOpenid(code);
		Members bms = new Members();
		if(ret!=null){
			Map<String,String> rew = WeixinToken.getUserInfo(ret.get("openid"), ret.get("accesstoken"));
			if(rew!=null){
				bms.setMembersWxID(rew.get("openid"));
				bms.setMembersImage(rew.get("headimgurl"));
				bms.setMembersNickName(rew.get("nickname"));
				if("1".equals(rew.get("sex"))){
					bms.setMembersSex(1);
				}
				if("2".equals(rew.get("sex"))){
					bms.setMembersSex(0);
				}
			}
			
		}
		super.setRequestAttibute("members", bms);
		super.setRequestAttibute("state", state);
		return "weixin.html";
	}

	
}
