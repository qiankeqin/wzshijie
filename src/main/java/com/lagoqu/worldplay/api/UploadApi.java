package com.lagoqu.worldplay.api;

import java.io.IOException;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lagoqu.worldplay.common.controller.APIController;
import com.lagoqu.worldplay.util.Upload;

@Controller
@Scope("prototype")
@RequestMapping("/uploadfile")
public class UploadApi extends APIController {
	@RequestMapping(value = "/{type}", method = RequestMethod.POST)
	public void uploadfile(@PathVariable("type") String mType){
		JSONArray result=Upload.uploadrelative(request, mType);
		try {
			returnSuccessJson(result.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/{type}/{rotat}", method = RequestMethod.POST)
	public void uploadfileRotat(@PathVariable("type") String mType,@PathVariable("rotat") String rotat){
		JSONArray result=Upload.uploadrelativeRotat(request, mType,rotat);
		try {
			returnSuccessJson(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 方法名称: uploadfileWeiXin<br>
	 * 描述：base64上传
	 * 作者: 邢留杰
	 * 修改日期：2015年8月11日下午3:55:59
	 * @throws IOException
	 */
	@RequestMapping(value = "/weixin", method = RequestMethod.POST)
	public void uploadfileWeiXin() throws IOException{
		String imgStr =request.getParameter("imgStr");
		if(imgStr == null){
			returnFailJson("请选择上传图片");
		}
		JSONArray result = Upload.uploadBase64(request, imgStr);
		try {
			returnSuccessJson(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
