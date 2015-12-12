package com.lagoqu.worldplay.common.params;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class ParamBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int appver;
	private int phoneid;

	public int getAppver() {
		return appver;
	}

	public void setAppver(int appver) {
		this.appver = appver;
	}

	public int getPhoneid() {
		return phoneid;
	}

	public void setPhoneid(int phoneid) {
		this.phoneid = phoneid;
	}
}
