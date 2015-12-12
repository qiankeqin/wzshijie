package com.lagoqu.worldplay.util;

public class TokenThread implements Runnable{

    public static String accessToken = null; 
	public void run() {
		// TODO Auto-generated method stub
		 while (true) {
					 accessToken = WeixinToken.getToken();// 获取token   
					 try {
			             if (null != accessToken) {    
			                 // 休眠7000秒    
								Thread.sleep((5000) * 1000);  
			             } else {    
			                 // 如果access_token为null，60秒后再获取    
								Thread.sleep(30 * 1000);
			             } 
		             } catch (InterruptedException e) {
							// TODO Auto-generated catch block
			            	 try {    
			                     Thread.sleep(30 * 1000);    
			                 } catch (InterruptedException e1) {    
			                       
			                 }  
						} 
				}
	}

}
