package com.lagoqu.worldplay.util;

/**
 * 描述：云片短信返回结果<br>
 * 作者：王猛 <br>
 * 修改日期：2015年4月24日下午3:05:36 <br>
 * E-mail: <br>
 */
public class YunpianResult {
	private long code;// 返回码
	private String msg;// 错误信息

	/**
	 * 方法名称: isSendSuccess<br>
	 * 描述：短信发送是否成功<br>
	 * 作者: 王猛<br>
	 * 修改日期：2015年4月24日下午3:05:23<br>
	 * 
	 * @return<br>
	 */
	public boolean isSendSuccess() {
		if (0 == this.code) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 方法名称: isIllegalUserOperate<br>
	 * 描述：是否会员操作不当导致的短信发送失败<br>
	 * 作者: 王猛<br>
	 * 修改日期：2015年4月24日下午3:12:10<br>
	 * @return<br>
	 */
	public boolean isIllegalUserOperate() {
		if (9 == this.code) {//同一手机号5分钟内重复提交相同的内容超过3次
			return true;
		} else if (17 == this.code) {//24小时内同一手机号发送次数超过限制
			return true;
		} else {
			return false;
		}
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
