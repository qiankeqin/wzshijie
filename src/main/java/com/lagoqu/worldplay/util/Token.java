package com.lagoqu.worldplay.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**描述：获取APP管理员Token值<br>
 * 作者：王小欢 <br>
 * 修改日期：2015年6月10日下午5:09:10 <br>
 * E-mail:  <br> 
 */
public class Token {

	/**方法名称: getToken<br>
	 * 描述：//读取token值
	 * 作者: 王小欢
	 * 修改日期：2015年6月11日下午4:46:24
	 * @param toKenPath
	 * @return
	 * @throws IOException
	 */
	public String getToken(String toKenPath) throws IOException{ 
		File f = new File(toKenPath);  
	    //读取token值
	    BufferedReader input = new BufferedReader(new FileReader(f));
	    String newtoken=input.readLine();
	    input.close();
		return newtoken;
	}
	
	
	
	/**方法名称: setToken<br>
	 * 描述： //写入token值
	 * 作者: 王小欢
	 * 修改日期：2015年6月11日下午4:45:06
	 * @param toKenPath
	 * @param token
	 * @throws IOException
	 */
	public void setToken(String toKenPath,String token) throws IOException{
		 //创建文件
		File f = new File(toKenPath);  
	    if (f.exists()) {  
	        System.out.print("文件存在");  
	    } else {  
	        System.out.print("文件不存在");  
	        f.createNewFile();// 不存在则创建  
	    }
		BufferedWriter output = new BufferedWriter(new FileWriter(f));  
   	    output.write(token);  
   	    output.close();
	}
	 
}
